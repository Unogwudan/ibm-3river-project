package com.unogwudan.ibm3riverproject.service;

import com.unogwudan.ibm3riverproject.helper.CustomerBalanceJoiner;
import com.unogwudan.ibm3riverproject.serializers.MySerdesFactory;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Properties;

@Configuration
public class CustomerBalanceKafkaStreamsProcessor {

    private int counter;

    public Topology topology() {

        StreamsBuilder builder = new StreamsBuilder();

        final KStream cStream =
                builder.stream("Customer",
                                Consumed.with(Serdes.String(), MySerdesFactory.customerSerde()))
                        .map((key, value) -> {
                            System.out.println("Customer Key: " + key);
                            System.out.println("Customer Value: " + value);
                            return new KeyValue(key, value);
                        });

        final KStream bStream =
                builder.stream("Balance",
                                Consumed.with(Serdes.String(), MySerdesFactory.balanceSerde()))
                        .map((key, value) -> {
                            System.out.println("Balance Key: " + key);
                            System.out.println("Balance Value: " + value);
                            return new KeyValue(key, value);
                        });


        KStream customerBalanceStream =
                cStream.join(bStream,
                        new CustomerBalanceJoiner(),
                        JoinWindows.of(Duration.ofSeconds(10)),
                        StreamJoined.with(
                                Serdes.String(),
                                MySerdesFactory.customerSerde(),
                                MySerdesFactory.balanceSerde()));
        customerBalanceStream.print(Printed.toSysOut());

        customerBalanceStream.peek((key, value) -> System.out.printf("Cus Stream key is [%s] value is [%s] %n", counter++, key, value))
                .to("CustomerBalance", Produced.with(Serdes.String(), MySerdesFactory.customerBalanceSerde()));

        return builder.build();
    }

    public Properties properties() {
        Properties streamProps = new Properties();
        streamProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "customer-balance-kafka-streams-processor");
        streamProps.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092, localhost:9092");
        streamProps.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        streamProps.put("schema.registry.url", "http://localhost:8081");

        return streamProps;
    }

    @Bean
    public void processStream() {
        KafkaStreams streams = new KafkaStreams(topology(), properties());
        streams.cleanUp();
        System.out.println("Start Stream Processing");
        streams.start();
    }

}
