package com.online.food.Services;

import com.online.food.DTOs.CartDTO;
import com.online.food.Entity.Cart;
import com.online.food.Entity.CartItem;
import com.online.food.Entity.Food;
import com.online.food.Exceptions.ResourceNotFoundException;
import com.online.food.Repository.CartRepository;
import com.online.food.Repository.FoodRepository;
import com.online.food.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Security;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
//    public Cart cart=new Cart();

    public ResponseEntity<?> addItemsInCart(Long FoodId,int quantity) {
        try {
            Cart cart = cartRepository.findByUserId(1L);

            Food food = foodRepository.findById(FoodId).orElseThrow(() -> new ResourceNotFoundException("Food not found with given Id : " + FoodId));
            CartItem cartItem=new CartItem();
            cartItem.setFood(food);
            cartItem.setQuantity(quantity);
            cart.addOrderItem(cartItem);
            cart.setUser(userRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("User not found with given Id : " + 1L)));
            Cart savedCart = cartRepository.save(cart);
            CartDTO mappedCArt = modelMapper.map(savedCart, CartDTO.class);
            return new ResponseEntity<>(mappedCArt, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
