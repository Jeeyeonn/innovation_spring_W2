package com.example.spring_jwt.service;

import com.example.spring_jwt.Dto.LoginDto;
import com.example.spring_jwt.Dto.SignupRequestDto;
import com.example.spring_jwt.jwt.JwtTokenProvider;
import com.example.spring_jwt.repository.UserRepository;
import com.example.spring_jwt.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthUserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;


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

        String answer = "";

        Optional<Users> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        if (!pw.equals(pw_confirm)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());


        Users users = new Users();
        users.setUsername(username);
        users.setPassword(pw);

        userRepository.save(users);
    }

    public Users getuser(String username){
        Users users = userRepository.findUsersByUsername(username);
        return users;
    }


}