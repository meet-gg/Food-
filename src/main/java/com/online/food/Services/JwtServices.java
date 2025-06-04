package com.online.food.Services;


import com.online.food.Entity.User;

import javax.crypto.SecretKey;

public interface JwtServices {
     SecretKey getSecretKey();
     String generateAccessToken(User user);
     String generateRefreshToken(User user);
     Long getUserIdFromToken(String token);
}
