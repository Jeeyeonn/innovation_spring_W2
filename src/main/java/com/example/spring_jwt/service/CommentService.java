package com.example.spring_jwt.service;

import com.example.spring_jwt.Dto.CommentRequestDto;
import com.example.spring_jwt.model.Comment;
import com.example.spring_jwt.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    public CommentRepository repository;

    public List<Comment> getComment(int id){
        List<Comment> comments = repository.findAll();
        List<Comment> id_comment = new ArrayList<>();

        for(int a=0; a<comments.size(); a++){
           if (comments.get(a).getPost_id() == id)
               id_comment.add(comments.get(a));
        }

        return id_comment;
    }

    public Comment upDateComment(Long id, CommentRequestDto requestDto, String username){
        Comment comment = repository.findById(id.intValue()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 댓글 아이디가 없습니다")
        );

        if (comment.getName().equals(username)){
            comment.update(requestDto);
        }

        return comment;
    }

    public void DeleteComment(int id, String username){
        Comment comment = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 댓글 아이디가 없습니다")
        );

        if (comment.getName().equals(username)){
            repository.deleteById(id);
        }
    }

}
