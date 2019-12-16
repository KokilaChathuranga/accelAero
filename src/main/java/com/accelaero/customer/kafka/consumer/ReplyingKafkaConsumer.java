package com.accelaero.customer.kafka.consumer;

import java.util.HashSet;
import java.util.Set;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.accelaero.customer.kafka.model.Food;
import com.accelaero.customer.kafka.model.Message;
import com.accelaero.customer.kafka.model.Restaurant;


@Component
public class ReplyingKafkaConsumer {

    @KafkaListener(topics = "${kafka.topic.request-topic}")
    @SendTo
    public Message listen(Message request) throws InterruptedException {

        request.setAdditionalProperty("result", "return result for search");
        Set<Restaurant> restaurants = new HashSet<>();
        if (request.getField1() != null && request.getField1().contains("pns"))
            restaurants.add(new Restaurant("1", "PNS pvt ltd"));
        if (request.getField1() != null && request.getField1().contains("pizza hut"))
            restaurants.add(new Restaurant("2", "Pizza Hut"));
        request.setRestaurants(restaurants);
        Set<Food> foods = new HashSet<>();
        if (request.getField2() != null && request.getField2().contains("rice"))
            foods.add(new Food("1", "1", "vegetable fried rice"));
        if (request.getField2() != null && request.getField2().contains("rice"))
            foods.add(new Food("2", "1", "mixed fried rice"));
        if (request.getField2() != null && request.getField2().contains("pizza"))
            foods.add(new Food("3", "2", "regular pizza"));
        if (request.getField2() != null && request.getField2().contains("pizza"))
            foods.add(new Food("4", "2", "cheese pizza"));
        request.setFoods(foods);
        return request;
    }

}
