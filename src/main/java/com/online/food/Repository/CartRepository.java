package com.online.food.Repository;

import com.online.food.Entity.Cart;
import com.online.food.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long userId);
    Cart findByUser(User user);
}
