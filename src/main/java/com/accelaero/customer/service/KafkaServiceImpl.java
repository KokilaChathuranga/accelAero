package com.accelaero.customer.service;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.accelaero.customer.kafka.model.Message;

@Service
public class KafkaServiceImpl implements KafkaService {
    @Autowired
    ReplyingKafkaTemplate<String, Message, Message> kafkaTemplate;
    @Value("${kafka.topic.request-topic}")
    String requestTopic;
    @Value("${kafka.topic.requestreply-topic}")
    String requestReplyTopic;

    @Override
    public Message getReplyFromConsumer(Message message) {
        // create producer record
        ProducerRecord<String, Message> record = new ProducerRecord<String, Message>(requestTopic, message);
        // set reply topic in header
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));
        // post in kafka topic
        RequestReplyFuture<String, Message, Message> sendAndReceive = kafkaTemplate.sendAndReceive(record);
        // confirm if producer produced successfully
        SendResult<String, Message> sendResult = null;
        try {
            sendResult = sendAndReceive.getSendFuture().get();
            //print all headers
            sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));
            // get consumer record
            ConsumerRecord<String, Message> consumerRecord = sendAndReceive.get();
            // return consumer value
            return consumerRecord.value();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}