package com.online.food.Controller;

import com.online.food.DTOs.FoodDTO;
import com.online.food.Services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantServices;
    @PostMapping("/addFood/{restaurantId}")
    public ResponseEntity<?> addFood(@PathVariable Long restaurantId, @RequestBody FoodDTO food) {
        ResponseEntity<?> savedFood = restaurantServices.addFood(restaurantId, food);
        return ResponseEntity.ok(savedFood);
    }

    @GetMapping("/getAllFood/{restaurantId}")
    public ResponseEntity<?> getFood(@PathVariable Long restaurantId) {
        ResponseEntity<?> savedFood = restaurantServices.getAllFood(restaurantId);
        return ResponseEntity.ok(savedFood);
    }

    @DeleteMapping("/deleteFood/{foodId}")
    public ResponseEntity<?> deleteFood(@PathVariable Long foodId) {
        ResponseEntity<?> savedFood = restaurantServices.deleteFoodById(foodId);
        return ResponseEntity.ok(savedFood);
    }

    @PutMapping("/updateFood/{foodId}")
    public ResponseEntity<?> updateFood(@PathVariable Long foodId, @RequestBody FoodDTO food) {
        ResponseEntity<?> savedFood = restaurantServices.updateFood(foodId, food);
        return ResponseEntity.ok(savedFood);
    }
}
