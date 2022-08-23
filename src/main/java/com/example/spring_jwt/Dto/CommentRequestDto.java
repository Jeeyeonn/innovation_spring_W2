package com.example.spring_jwt.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private int post_id;
    private String comment;
}
