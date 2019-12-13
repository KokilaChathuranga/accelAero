package com.accelaero.customer.kafka.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "foodId",
        "restaurantId",
        "name"
})
public class Food {
    @JsonProperty("foodId")
    private String foodId;
    @JsonProperty("restaurantId")
    private String restaurantId;
    @JsonProperty("name")
    private String name;

    public Food() {
    }

    public Food(String foodId, String restaurantId, String name) {
        this.foodId = foodId;
        this.restaurantId = restaurantId;
        this.name = name;
    }

    @JsonProperty("foodId")
    public String getFoodId() {
        return foodId;
    }

    @JsonProperty("foodId")
    public void setFoodId(String foodId) {
        this.foodId = foodId;
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
