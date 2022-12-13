package com.sparta.crud.dto;

import com.sparta.crud.entity.Board;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardCommentResponseDto extends ResponseDto {

    private BoardDto board;

    public BoardCommentResponseDto(StatusEnum status, Board board, List<CommentDto> commentList) {
        super(status);
        this.board = new BoardDto(board, commentList);
    }
}
