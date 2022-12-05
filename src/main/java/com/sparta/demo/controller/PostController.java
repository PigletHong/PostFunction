package com.sparta.demo.controller;

import com.sparta.demo.dto.*;
import com.sparta.demo.jwt.JwtUtil;
import com.sparta.demo.repository.PostRepository;
import com.sparta.demo.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")  //공통으로 들어가는 부분 빼주기
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtUtil jwtUtil;
    private final PostRepository postRepository;

    @PostMapping("/save/posts")
    public PostResponseDto savePosts(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.savePosts(requestDto, request);
    }

    @GetMapping("/get/posts")
    public PostListResponseDto getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/get/post")
    public PostResponseDto getPost(@RequestParam Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/update/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.updatePost(id, requestDto, request);
    }

    @DeleteMapping("/delete/post/{id}")
    public ResponseDto deletePost(@PathVariable Long id, HttpServletRequest request) {
        return postService.deletePost(id, request);
    }
}
