package com.example.spring_jwt.repository;

import com.example.spring_jwt.Dto.RefreshTokenDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenDto, Long> {

    boolean existsByRefreshToken(String token);
}