    package mentorship.roadmap.microservices.service_c.config;

    import org.apache.kafka.common.serialization.StringDeserializer;
    import org.apache.kafka.common.serialization.StringSerializer;
    import mentorship.roadmap.microservices.service_c.entity.MessagePayload;
    import org.apache.kafka.clients.consumer.ConsumerConfig;
    import org.apache.kafka.clients.producer.ProducerConfig;
    import org.apache.kafka.common.serialization.Deserializer;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
    import org.springframework.kafka.core.*;
    import org.springframework.kafka.support.serializer.JsonDeserializer;

    import java.util.HashMap;
    import java.util.Map;

    @Configuration
    class KafkaConfig {
        @Bean
        public ProducerFactory<String, MessagePayload> producerFactory() {
            Map<String,Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonSerializer.class);
            return new DefaultKafkaProducerFactory<>(props);
        }
        @Bean
        KafkaTemplate<String, MessagePayload> kafkaTemplate(){ return new KafkaTemplate<>(producerFactory()); }

        @Bean
        public ConsumerFactory<String, MessagePayload> consumerFactory() {
            Map<String,Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonDeserializer.class);
            props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
            return new DefaultKafkaConsumerFactory<>(props, (Deserializer) new StringDeserializer(),
                    new JsonDeserializer<>(MessagePayload.class));
        }
        @Bean
        ConcurrentKafkaListenerContainerFactory<String, MessagePayload> kafkaListenerContainerFactory(){
            var f = new ConcurrentKafkaListenerContainerFactory<String, MessagePayload>();
            f.setConsumerFactory(consumerFactory());
            return f;
        }
    }

