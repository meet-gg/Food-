package com.online.food.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items=new ArrayList<>();

    public void addOrderItem(CartItem cartItem) {
        items.add(cartItem);
        cartItem.setCart(this);
    }

    public void removeOrderItem(CartItem cartItem) {
        items.remove(cartItem);
        cartItem.setCart(null);
    }
}
