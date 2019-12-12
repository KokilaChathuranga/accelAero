package com.hellokoding.auth.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.hellokoding.auth.kafka.model.Message;


@Component
public class ReplyingKafkaConsumer {

    @KafkaListener(topics = "${kafka.topic.request-topic}")
    @SendTo
    public Message listen(Message request) throws InterruptedException {

        request.setAdditionalProperty("result", request.getField1().concat(" ").concat(request.getField2().concat(" ").concat("return result for search")));
        return request;
    }

}
