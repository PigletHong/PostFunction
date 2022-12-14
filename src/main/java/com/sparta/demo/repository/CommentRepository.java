package com.sparta.demo.repository;

import com.sparta.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
<<<<<<< HEAD:src/main/java/com/sparta/crud/repository/CommentRepository.java
    Optional<Comment> findByIdAndUserId(Long commentId, Long UserId); // 댓글 ID와 유저 ID로 댓글 DB에서 얻은 값을 Comment 객체에 담는 메소드
=======
    Optional<Comment> findByUsername(String username);
>>>>>>> parent of 4810b3e (- Spring Security 적용 및 URI별 권한 부여):src/main/java/com/sparta/demo/repository/CommentRepository.java
}
