package com.sparta.demo.controller;

import com.sparta.demo.dto.*;
import com.sparta.demo.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {

    private final CommentService commentService;

    /* CREATE */
    @PostMapping("/post/{id}/comments")
    public ResponseEntity commentSave(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return ResponseEntity.ok(commentService.commentSave(id, commentRequestDto, request));
    }

    @PutMapping("/update/{id}/comments")
    public CommentResponseDto updatePost(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.updateComment(id, commentRequestDto, request);
    }

    @DeleteMapping("/delete/{id}/comments")
    public ResponseDto deletePost(@PathVariable Long id, HttpServletRequest request) {
        return commentService.deleteComment(id, request);
    }
}