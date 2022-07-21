package com.unogwudan.ibm3riverproject.serializers;

import com.unogwudan.ibm3riverproject.model.Balance;
import com.unogwudan.ibm3riverproject.model.Customer;
import com.unogwudan.ibm3riverproject.model.CustomerBalance;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class MySerdesFactory {

        private MySerdesFactory() {}

        public static Serde<Customer> customerSerde() {
            JsonSerializer<Customer> serializer = new JsonSerializer<>();
            JsonDeserializer<Customer> deserializer = new JsonDeserializer<>(Customer.class);
            return Serdes.serdeFrom(serializer, deserializer);
        }

        public static Serde<Balance> balanceSerde() {
            JsonSerializer<Balance> serializer = new JsonSerializer<>();
            JsonDeserializer<Balance> deserializer = new JsonDeserializer<>(Balance.class);
            return Serdes.serdeFrom(serializer, deserializer);
        }

    public static Serde<CustomerBalance> customerBalanceSerde() {
        JsonSerializer<CustomerBalance> serializer = new JsonSerializer<>();
        JsonDeserializer<CustomerBalance> deserializer = new JsonDeserializer<>(CustomerBalance.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }
}
