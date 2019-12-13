package com.accelaero.customer.kafka.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "restaurantId",
        "name"
})
public class Restaurant {
    @JsonProperty("restaurantId")
    private String restaurantId;
    @JsonProperty("name")
    private String name;

    public Restaurant() {
    }

    public Restaurant(String restaurantId, String name) {
        this.restaurantId = restaurantId;
        this.name = name;
    }

    @JsonProperty("restaurantId")
    public String getRestaurantId() {
        return restaurantId;
    }

    @JsonProperty("restaurantId")
    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
}
