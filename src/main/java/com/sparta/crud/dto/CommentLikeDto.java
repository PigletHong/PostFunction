package com.sparta.crud.dto;

import com.sparta.crud.entity.Comment;
import com.sparta.crud.entity.User;
import lombok.Getter;

@Getter
public class CommentLikeDto {
    private Long id;
    private Comment comment;
    private User user;

    public CommentLikeDto(Long id, Comment comment, User user) {
        this.id = id;
        this.comment = comment;
        this.user = user;
    }
}
