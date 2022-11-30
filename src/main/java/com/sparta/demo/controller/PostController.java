package com.sparta.demo.controller;

import com.sparta.demo.dto.*;
import com.sparta.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")  //공통으로 들어가는 부분 빼주기
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/save/posts")
    public ResponseDto savePosts(@RequestBody PostRequestDto requestDto) {
        return postService.savePost(requestDto);
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
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    @DeleteMapping("/delete/post/{id}")
    public ResponseDto deletePost(@PathVariable Long id, @RequestBody DeleteRequestDto deleteRequestDto) {
        return postService.deletePost(id, deleteRequestDto);
    }
}
