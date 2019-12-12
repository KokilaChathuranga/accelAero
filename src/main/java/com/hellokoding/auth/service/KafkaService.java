package com.hellokoding.auth.service;

import com.hellokoding.auth.kafka.model.Message;

public interface KafkaService {
    Message getReplyFromConsumer(Message message);
}