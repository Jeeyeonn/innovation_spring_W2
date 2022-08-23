package com.example.spring_jwt.repository;

import com.example.spring_jwt.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}