package com.example.spring_jwt.controller;

import com.example.spring_jwt.Dto.LoginDto;
import com.example.spring_jwt.Dto.RefreshTokenDto;
import com.example.spring_jwt.Dto.SignupRequestDto;

import com.example.spring_jwt.jwt.JwtTokenProvider;
import com.example.spring_jwt.model.Users;
import com.example.spring_jwt.repository.RefreshTokenRepository;
import com.example.spring_jwt.repository.UserRepository;
import com.example.spring_jwt.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AuthController {

    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AuthUserService authUserService;

    @Autowired
    private final RefreshTokenRepository refreshTokenRepository;

    // 회원가입
    @PostMapping("/join")
    public void join(@RequestBody SignupRequestDto user) {
        authUserService.registerUser(user);
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        Optional<Users> found = userRepository.findByUsername(username);
        if (!found.isPresent()) {
            throw new IllegalArgumentException("ID가 존재하지 않습니다.");
        }

        String u_password = found.get().getPassword();
        if (!password.equals(u_password))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        String accressToken = jwtTokenProvider.createToken(username);
        String refreshToken = jwtTokenProvider.createRefreshToken(username);

        refreshTokenRepository.save(new RefreshTokenDto(refreshToken));

        return "Access Token : " + accressToken+ "\n"
                + "Refresh Toke : "+ refreshToken;
    }


    // 로그인 됐는지 확인
    @GetMapping("/user")
    public Users detail(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String username = jwtTokenProvider.getUserPk(token);

        return authUserService.getuser(username);
    }
}
