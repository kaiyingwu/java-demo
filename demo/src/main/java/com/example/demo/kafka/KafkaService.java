package com.example.demo.kafka;



/**
 * kafka测试Demo
 */
public interface KafkaService {

    /**
     * kafka消息生产发送者
     */
    void sendMessage(String topic,String message);


    /**
     * 测试kafka消息消费实现
     */
    String readMessage();

}
