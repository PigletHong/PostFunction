package com.sparta.demo.repository;


import com.sparta.demo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Object> findByUsername(String subject);
    Optional<Post> findByIdAndUserId(Long id, Long userId);
}
