package com.hellokoding.auth.kafka.consumer;

import java.util.HashSet;
import java.util.Set;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.hellokoding.auth.kafka.model.Food;
import com.hellokoding.auth.kafka.model.Message;
import com.hellokoding.auth.kafka.model.Restaurant;


@Component
public class ReplyingKafkaConsumer {

    @KafkaListener(topics = "${kafka.topic.request-topic}")
    @SendTo
    public Message listen(Message request) throws InterruptedException {

        request.setAdditionalProperty("result", request.getField1().concat(" ").concat(request.getField2().concat(" ").concat("return result for search")));
        Set<Restaurant> restaurants = new HashSet<>();
        restaurants.add(new Restaurant("1", "pns ltd"));
        request.setRestaurants(restaurants);
        Set<Food> foods = new HashSet<>();
        foods.add(new Food("1", "1", "rice"));
        request.setFoods(foods);
        return request;
    }

}
