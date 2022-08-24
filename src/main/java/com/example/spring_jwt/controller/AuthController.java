package com.example.spring_jwt.controller;

import com.example.spring_jwt.Dto.LoginDto;
import com.example.spring_jwt.Dto.RefreshTokenDto;
import com.example.spring_jwt.Dto.SignupRequestDto;

import com.example.spring_jwt.jwt.JwtTokenProvider;
import com.example.spring_jwt.model.ResponseModel;
import com.example.spring_jwt.model.Users;
import com.example.spring_jwt.repository.RefreshTokenRepository;
import com.example.spring_jwt.repository.UserRepository;
import com.example.spring_jwt.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AuthController {
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final AuthUserService authUserService;


    // 회원가입
    @PostMapping("/join")
    public void join(@RequestBody SignupRequestDto user) {
        authUserService.registerUser(user);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ResponseModel> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

        authUserService.login(loginDto.getUsername(), loginDto.getPassword());

        String accressToken = jwtTokenProvider.createToken(loginDto.getUsername());
        String refreshToken = jwtTokenProvider.createRefreshToken(loginDto.getUsername());

        authUserService.tokenPostHeader(accressToken, refreshToken, response);

        ResponseModel responseModel = ResponseModel.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("로그인 완료").build();
        return new ResponseEntity<>(responseModel, responseModel.getHttpStatus());
    }


    // 로그인 됐는지 확인
    @GetMapping("/user")
    public Users detail(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String username = jwtTokenProvider.getUserPk(token);

        return authUserService.getuser(username);
    }
}
