package com.online.food.Services;

import com.online.food.DTOs.CartDTO;
import com.online.food.DTOs.SignUpDTO;
import com.online.food.DTOs.UserDTO;
import com.online.food.Entity.Cart;
import com.online.food.Entity.CartItem;
import com.online.food.Entity.Food;
import com.online.food.Entity.User;
import com.online.food.Exceptions.ResourceNotFoundException;
import com.online.food.Repository.CartRepository;
import com.online.food.Repository.FoodRepository;
import com.online.food.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
//    public Cart cart=new Cart();

    public ResponseEntity<?> addItemsInCart(Long FoodId,int quantity) {
        try {
//            Cart cart = cartRepository.findByUserId(1L);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Cart cart = cartRepository.findByUser(user);
            if (cart==null){
                cart = new Cart();
            }
            Food food = foodRepository.findById(FoodId).orElseThrow(() -> new ResourceNotFoundException("Food not found with given Id : " + FoodId));
            CartItem cartItem=new CartItem();
            cartItem.setFood(food);
            cartItem.setQuantity(quantity);
            cart.addOrderItem(cartItem);
            cart.setUser(user);
            Cart savedCart = cartRepository.save(cart);
            CartDTO mappedCArt = modelMapper.map(savedCart, CartDTO.class);
            return new ResponseEntity<>(mappedCArt, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("user not found with mail "+username));
    }

    public ResponseEntity<UserDTO> signUp(SignUpDTO signUpDTO){
        try {
            Optional<User> user = userRepository.findByEmail(signUpDTO.getEmail());
            if (user.isPresent()){
                throw new BadCredentialsException("user already exist");
            }
            User createdUser = modelMapper.map(signUpDTO, User.class);
            createdUser.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
            User savedUser = userRepository.save(createdUser);
            UserDTO userDTO = modelMapper.map(savedUser, UserDTO.class);
            return new ResponseEntity<>(userDTO,HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
