package com.online.food.Controller;

import com.online.food.DTOs.LoginDTO;
import com.online.food.DTOs.LoginResponseDTO;
import com.online.food.DTOs.SignUpDTO;
import com.online.food.DTOs.UserDTO;
import com.online.food.Repository.UserRepository;
import com.online.food.Services.AuthServices;
import com.online.food.Services.JwtServicesImpl;
import com.online.food.Services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserService userServices;
    private final AuthServices authServices;
//    private final SessionRepo sessionRepo;
    private final JwtServicesImpl jwtServicesImpl;
    private final UserRepository userRepo;
//    private final PaymentServicesImpl paymentServices;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpDTO signUpDTO) {
        ResponseEntity<UserDTO> userDTOResponseEntity = userServices.signUp(signUpDTO);
        return ResponseEntity.ok(userDTOResponseEntity.getBody());
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        LoginResponseDTO loginResponseDTO = authServices.login(loginDTO);
        Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
//        cookie.setSecure(false); when use https production time it is true
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponseDTO);
    }
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(cookie -> cookie.getValue())
                .orElseThrow(() -> new RuntimeException("Refresh token not found inside cookie"));
//        log.info("{}",refreshToken);
        LoginResponseDTO loginResponseDTO=authServices.refreshToken(refreshToken);
        return ResponseEntity.ok(loginResponseDTO) ;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
//        String refreshToken = Arrays.stream(request.getCookies())
//                .filter(cookie -> cookie.getName().equals("refreshToken"))
//                .findFirst()
//                .map(cookie -> cookie.getValue())
//                .orElseThrow(() -> new RuntimeException("Refresh token not found inside cookie"));
//        Session session=sessionRepo.findByRefreshToken(refreshToken).get();
//        Long id=session.getUser().getId();
//        String accessToken= jwtServices.generateAccessToken(userRepo.findById(id).get());
//        sessionRepo.delete(session);

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true in production with HTTPS
        response.addCookie(cookie);
//
        return "log-out";
    }

//    @GetMapping("confirm-payment/{value}/{quantity}")
//    private ResponseEntity<?> confirmPayment(@PathVariable Long value, @PathVariable Long quantity) {
//        ResponseEntity<?> confirmPayment = paymentServices.confirmationPayment(value, quantity);
//        return ResponseEntity.ok(confirmPayment);
//    }
}
