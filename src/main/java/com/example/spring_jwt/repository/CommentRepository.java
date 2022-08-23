package com.example.spring_jwt.repository;

import com.example.spring_jwt.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}