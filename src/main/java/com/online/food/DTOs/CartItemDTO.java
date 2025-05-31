package com.online.food.DTOs;

import com.online.food.Entity.Cart;
import com.online.food.Entity.Food;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long id;
    private FoodDTO food;
    private int quantity;
}
