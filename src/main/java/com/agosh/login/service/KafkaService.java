package com.agosh.login.service;

import java.time.Instant;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.agosh.login.config.KafkaProperties;

@Service
public class KafkaService {

    private final Logger log = LoggerFactory.getLogger(KafkaService.class);

    private KafkaProducer<String, String> producer;

    public KafkaService(KafkaProperties kafkaProperties) {
        this.producer = new KafkaProducer<>(kafkaProperties.getProducerProps());
    }

    public PublishResult publish(String topic, String message, String key) throws ExecutionException, InterruptedException {
        log.debug("REST request to send to Kafka topic {} with key {} the message : {}", topic, key, message);
        RecordMetadata metadata = producer.send(new ProducerRecord<>(topic, key, message)).get();
        return new PublishResult(metadata.topic(), metadata.partition(), metadata.offset(), Instant.ofEpochMilli(metadata.timestamp()));
    }

    
    private static class PublishResult {
        public final String topic;
        public final int partition;
        public final long offset;
        public final Instant timestamp;

        private PublishResult(String topic, int partition, long offset, Instant timestamp) {
            this.topic = topic;
            this.partition = partition;
            this.offset = offset;
            this.timestamp = timestamp;
        }
    }
}
