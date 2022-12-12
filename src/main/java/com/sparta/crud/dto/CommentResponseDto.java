package com.sparta.crud.dto;

import com.sparta.crud.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto extends ResponseDto {
    CommentDto comment;
    public CommentResponseDto(StatusEnum status, Comment comment) {
        super(status);
        this.comment = new CommentDto(comment);
    }
}
