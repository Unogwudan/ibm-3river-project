package com.unogwudan.ibm3riverproject.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.errors.SerializationException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import java.lang.reflect.Type;

public class JsonDeserializer<T> implements Deserializer<T> {

    private final Gson gson = new GsonBuilder().create();

    private Class<T> destinationClass;
    private Type reflectionTypeToken;

    public JsonDeserializer(Class<T> destinationClass) {
        this.destinationClass = destinationClass;
    }

    public JsonDeserializer(Type reflectionTypeToken) {
        this.reflectionTypeToken = reflectionTypeToken;
    }

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {
        // nothing to do
    }

    @Override
    public T deserialize(String topic, byte[] bytes) {
        if (bytes == null)
            return null;

        try {
            Type type = destinationClass != null ? destinationClass : reflectionTypeToken;
            return gson.fromJson(new String(bytes, StandardCharsets.UTF_8), type);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing message", e);
        }
    }

    @Override
    public void close() {
        // nothing to do
    }
}