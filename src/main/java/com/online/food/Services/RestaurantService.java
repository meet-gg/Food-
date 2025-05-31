package com.online.food.Services;

import com.online.food.DTOs.FoodDTO;
import com.online.food.DTOs.FoodDTOResponse;
import com.online.food.Entity.Food;
import com.online.food.Entity.Restaurant;
import com.online.food.Exceptions.ResourceNotFoundException;
import com.online.food.Repository.FoodRepository;
import com.online.food.Repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<?> addFood(Long restaurantId, FoodDTO foodDTO) {
        try {
            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with given Id : " + restaurantId));
            Food food = modelMapper.map(foodDTO, Food.class);
            food.setRestaurant(restaurant);
            Food savedFood = foodRepository.save(food);
            FoodDTOResponse foodDTOResponse = modelMapper.map(savedFood, FoodDTOResponse.class);
            return new ResponseEntity<>(foodDTOResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getAllFood(Long restaurantId) {
        try {
            restaurantRepository.findById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with given Id : " + restaurantId));
            List<FoodDTOResponse> allByRestaurantId = foodRepository.findAllByRestaurantId(restaurantId).stream().map(food -> modelMapper.map(food, FoodDTOResponse.class)).collect(Collectors.toList());
            return new ResponseEntity<>(allByRestaurantId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteFoodById(Long foodId) {
        try {
            foodRepository.findById(foodId).orElseThrow(() -> new ResourceNotFoundException("food not found with given Id : " + foodId));
            foodRepository.deleteById(foodId);
            return new ResponseEntity<>("delete successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateFood(Long foodId, FoodDTO foodDTO) {
        try {
            Food food1 = foodRepository.findById(foodId).orElseThrow(() -> new ResourceNotFoundException("food not found with given Id : " + foodId));
            Food food = modelMapper.map(foodDTO, Food.class);
            food.setId(foodId);
            food.setRestaurant(food1.getRestaurant());
            Food updateFood = foodRepository.save(food);
            FoodDTOResponse foodDTOResponse = modelMapper.map(updateFood, FoodDTOResponse.class);
            return new ResponseEntity<>(foodDTOResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
