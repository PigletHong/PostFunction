package com.sparta.crud.repository;

import com.sparta.crud.entity.Comment;
import com.sparta.crud.entity.CommentLike;
import com.sparta.crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);

    @Transactional
    void deleteByCommentAndUser(Comment comment, User user);
}
