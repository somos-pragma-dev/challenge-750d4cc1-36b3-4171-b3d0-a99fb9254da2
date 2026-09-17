package com.example.reconciliation.infrastructure.config;

import com.example.reconciliation.domain.model.Movement;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.util.backoff.FixedBackOff;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.ReactorKafkaProducer;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class KafkaConfig {

    private static final Logger log = LoggerFactory.getLogger(KafkaConfig.class);

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id:reconciliation-group}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset:earliest}")
    private String autoOffsetReset;

    @Value("${spring.kafka.consumer.max-poll-records:100}")
    private int maxPollRecords;

    @Value("${spring.kafka.consumer.fetch-min-size:1}")
    private int fetchMinSize;

    @Value("${spring.kafka.consumer.fetch-max-wait:500}")
    private int fetchMaxWait;

    @Value("${spring.kafka.listener.ack-mode:manual}")
    private String ackMode;

    @Value("${spring.kafka.listener.concurrency:3}")
    private int concurrency;

    @Value("${spring.kafka.producer.retries:3}")
    private int maxAttempts;

    @Value("${spring.kafka.producer.properties.retry.backoff.ms:1000}")
    private long backOffMs;

    public static final String MOVEMENT_TOPIC = "bank.movements";
    public static final String CONSUMER_GROUP_ID = "reconciliation-consumer-group";

    @Bean
    public ObjectMapper kafkaObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Map<String, Object> consumerProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, fetchMinSize);
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, fetchMaxWait);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    @Bean
    public Map<String, Object> producerProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, maxAttempts);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        return props;
    }

    @Bean
    public ConsumerFactory<String, Movement> consumerFactory() {
        Map<String, Object> props = consumerProperties();
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.reconciliation.domain.model");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new StringDeserializer());
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerProperties());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Movement> kafkaListenerContainerFactory(
            ConsumerFactory<String, Movement> consumerFactory,
            KafkaErrorHandler errorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, Movement> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setAckMode(org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL);
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    public CommonErrorHandler kafkaErrorHandler(final KafkaTemplate<String, Object> kafkaTemplate) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (record, ex) -> new org.apache.kafka.common.TopicPartition(record.topic() + ".dlt", record.partition()));
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, new FixedBackOff(backOffMs, maxAttempts));
        return errorHandler;
    }

    @Bean
    public KafkaTransactionManager<String, Object> kafkaTransactionManager(final ProducerFactory<String, Object> producerFactory) {
        return new KafkaTransactionManager<>(producerFactory);
    }

    @Bean
    public ReactorKafkaProducer<String, Object> reactorKafkaProducer(final ProducerFactory<String, Object> producerFactory) {
        return new ReactorKafkaProducer<>(SenderOptions.create(producerFactory.getConfigurationProperties()));
    }

    @Bean
    public ReactorKafkaConsumer<String, Movement> reactorKafkaConsumer(final ConsumerFactory<String, Movement> consumerFactory) {
        return new ReactorKafkaConsumer(ReceiverOptions.create(consumerFactory.getConfigurationProperties()));
    }

    @Bean
    public java.util.concurrent.ExecutorService kafkaExecutor() {
        return java.util.concurrent.Executors.newFixedThreadPool(concurrency);
    }

    @Bean
    public TopicBuilder movementTopic() {
        return TopicBuilder.name(MOVEMENT_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public TopicBuilder movementDltTopic() {
        return TopicBuilder.name(MOVEMENT_TOPIC + ".dlt")
                .partitions(3)
                .replicas(1)
                .build();
    }

    public String getMovementTopic() {
        return MOVEMENT_TOPIC;
    }

    public String getConsumerGroupId() {
        return CONSUMER_GROUP_ID;
    }
}