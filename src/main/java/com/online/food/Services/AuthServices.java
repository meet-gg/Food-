package com.online.food.Services;

import com.online.food.DTOs.LoginDTO;
import com.online.food.DTOs.LoginResponseDTO;

public interface AuthServices {
     LoginResponseDTO login(LoginDTO loginDTO);
     LoginResponseDTO refreshToken(String refreshToken);
}
