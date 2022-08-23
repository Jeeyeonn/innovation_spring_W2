package com.example.spring_jwt.Dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDto {

    @Id
    @Column(nullable = false)
    private String refreshToken;
}