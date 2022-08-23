package com.example.spring_jwt.service;

import com.example.spring_jwt.Dto.PostRequestDto;
import com.example.spring_jwt.model.Post;
import com.example.spring_jwt.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service // 스프링에게 이 클래스는 서비스임을 명시
public class PostService {

    // final: 서비스에게 꼭 필요한 녀석임을 명시
    private final PostRepository postRepository;


    @Transactional // SQL 쿼리가 일어나야 함을 스프링에게 알려줌
    public Long update(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        post.update(requestDto);
        return post.getId();
    }

    public Post getPostID(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        return post;

    }



}