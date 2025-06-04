package com.online.food.DTOs;

import com.online.food.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    private String email;
    private String password;
    private String name;
    private Role role;
    private String phone;
}
