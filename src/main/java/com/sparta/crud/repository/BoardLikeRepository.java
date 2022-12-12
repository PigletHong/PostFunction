package com.sparta.crud.repository;

import com.sparta.crud.entity.Board;
import com.sparta.crud.entity.BoardLike;
import com.sparta.crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByBoardAndUser(Board board, User user);
    @Transactional
    void deleteByBoardAndUser(Board board, User user);
}
