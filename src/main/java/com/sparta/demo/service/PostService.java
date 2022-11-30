package com.sparta.demo.service;

import com.sparta.demo.dto.*;
import com.sparta.demo.entity.Post;
import com.sparta.demo.repository.PostRepository;
//import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public ResponseDto savePost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        postRepository.save(post);
        return new ResponseDto("포스트 등록 완료", HttpStatus.OK.value());
    }

    @Transactional(readOnly = true)
    public PostListResponseDto getPosts() {
        PostListResponseDto postListResponseDto = new PostListResponseDto();
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            postListResponseDto.addPost(new PostResponseDto(post));
        }
        return postListResponseDto;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new RuntimeException("포스트를 찾을 수 없습니다")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new RuntimeException("포스트가 없습니다.")
        );

        if(post.getPassword().equals(requestDto.getPassword())) {
            post.update(requestDto);
        } else {
        }

        return new PostResponseDto(post);
    }

    @Transactional
    public ResponseDto deletePost(Long id, DeleteRequestDto deleteRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new RuntimeException("포스트가 없습니다.")
        );

        if(post.getPassword().equals(deleteRequestDto.getPassword())) {
            postRepository.delete(post);;
            return new ResponseDto("포스트 삭제 완료", HttpStatus.OK.value());
        } else {
            return new ResponseDto("비밀번호가 달라용", HttpStatus.OK.value());
        }
    }
}
