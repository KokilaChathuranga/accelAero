package com.accelaero.customer.service;

import com.accelaero.customer.kafka.model.Message;

public interface KafkaService {
    Message getReplyFromConsumer(Message message);
}