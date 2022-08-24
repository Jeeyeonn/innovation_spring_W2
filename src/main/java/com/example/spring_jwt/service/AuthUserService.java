package com.example.spring_jwt.service;

import com.example.spring_jwt.Dto.LoginDto;
import com.example.spring_jwt.Dto.RefreshTokenDto;
import com.example.spring_jwt.Dto.SignupRequestDto;
import com.example.spring_jwt.jwt.JwtTokenProvider;
import com.example.spring_jwt.repository.RefreshTokenRepository;
import com.example.spring_jwt.repository.UserRepository;
import com.example.spring_jwt.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthUserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    public void registerUser(SignupRequestDto requestDto) {
        /* 회원 ID 중복 확인 */
        String username = requestDto.getUsername();
        String pw = requestDto.getPassword();
        String pw_confirm = requestDto.getPasswordConfirm();


        Optional<Users> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        if (!pw.equals(pw_confirm)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        // 패스워드 암호화
        String password = bCryptPasswordEncoder.encode(requestDto.getPassword());


        Users users = new Users();
        users.setUsername(username);
        users.setPassword(password);

        userRepository.save(users);
    }

    public Users getuser(String username){
        Users users = userRepository.findUsersByUsername(username);
        return users;
    }

    public void login(String username, String password){

        Optional<Users> found = userRepository.findByUsername(username);
        if (!found.isPresent()) {
            throw new IllegalArgumentException("ID가 존재하지 않습니다.");
        }

        String u_password = found.get().getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pp = encoder.encode(password);
        if (!encoder.matches(password, u_password)){
            System.out.println(u_password);
            System.out.println(pp);
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        }
    }


    public void tokenPostHeader(String accressToken, String refreshToken, HttpServletResponse response) {
        refreshTokenRepository.save(new RefreshTokenDto(refreshToken));

        response.addHeader("Access-Token", accressToken);
        response.addHeader("Refresh-Token", refreshToken);
    }
}