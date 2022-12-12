package com.sparta.crud.controller;

import com.sparta.crud.dto.ResponseDto;
import com.sparta.crud.dto.CommentRequestDto;
import com.sparta.crud.entity.User;
import com.sparta.crud.security.UserDetailsImpl;
import com.sparta.crud.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/save/comment/{id}")
    public ResponseEntity<ResponseDto> addComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.addComment(id, commentRequestDto, userDetails.getUser()));
    }

    // 댓글 수정
    @PutMapping("update/comment/{boardId}/{cmtId}")
    public ResponseEntity<ResponseDto> updateComment(@PathVariable Long boardId, @PathVariable Long cmtId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.updateComment(boardId, cmtId, commentRequestDto, userDetails.getUser()));
    }

    // 댓글 삭제
    @DeleteMapping("delete/comment/{boardId}/{cmtId}")
    public ResponseEntity<ResponseDto> deleteComment(
            @PathVariable Long boardId,
            @PathVariable Long cmtId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.deleteComment(boardId, cmtId, userDetails.getUser()));
    }

    @PostMapping("save/comment/like/{id}")
    public ResponseEntity<ResponseDto> addlike(
            @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.addlike(id, userDetails.getUser()));
    }

    @DeleteMapping("delete/comment/like/{id}")
    public ResponseEntity<ResponseDto> deletelike(
            @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.deletelike(id, userDetails.getUser()));
    }
}
