package com.example.demo.kafka.impl;

import com.example.demo.kafka.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;


@Service
@Slf4j
public class KafkaImpl implements KafkaService {

    @Autowired
    private KafkaTemplate<String,String> template;


    @Autowired
    private KafkaConsumer<String,String> consumer;

    @Value("${kafka.platform.user.data.topic}")
    private String topicUser;

    @Override
    public void sendMessage(String topic,String message) {
        ListenableFuture<SendResult<String, String>> future = template.send(topic, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("message: {} send fail, topic: {}, due to: {}", message, topic, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                log.info(stringStringSendResult.getProducerRecord().value());
            }
        });
    }


    @Override
    public String readMessage() {
        // 订阅topic-user topic
        consumer.subscribe(Collections.singletonList(topicUser));
       // kafkaConsumer.subscribe(Collections.singletonList(topicUser));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        records.forEach(record -> {
            System.out.printf("成功消费消息：topic = %s ,partition = %d,offset = %d, key = %s, value = %s%n", record.topic(), record.partition(), record.offset(), record.key(), record.value());
        });

        return "ok";
    }
}
