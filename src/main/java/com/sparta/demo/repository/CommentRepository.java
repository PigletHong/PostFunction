package com.sparta.demo.repository;

import com.sparta.demo.entity.Comment;
import com.sparta.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<User> findByUsername(String username);
}
