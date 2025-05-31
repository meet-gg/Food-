package com.online.food.DTOs;

import com.online.food.Entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTOResponse {
    private Long id;
    private String name;
    private double price;
    private String description;
    private boolean availability;
    private String category;
    private Long restaurantId;
}
