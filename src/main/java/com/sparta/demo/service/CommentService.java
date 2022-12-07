package com.sparta.demo.service;

import com.sparta.demo.dto.*;
import com.sparta.demo.entity.Comment;
import com.sparta.demo.entity.Post;
import com.sparta.demo.entity.User;
import com.sparta.demo.entity.UserRoleEnum;
import com.sparta.demo.jwt.JwtUtil;
import com.sparta.demo.repository.CommentRepository;
import com.sparta.demo.repository.PostRepository;
import com.sparta.demo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postsRepository;
//    private final CommentResponseDto commentResponseDto;
    private final JwtUtil jwtUtil;
    private  final PostRepository postRepository;

    /* CREATE */
    @Transactional
    public CommentResponseDto commentSave(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        Comment comment = null;

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Post post = postsRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + id));

            commentRequestDto.setUser(user);
            commentRequestDto.setPost(post);
            commentRequestDto.setUsername(user.getUsername());


            comment = commentRequestDto.toEntity();
            commentRepository.save(comment);

        }
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("댓글이 없습니다.")
            );

            if (comment.getUsername().equals(user.getUsername())) {
                comment.update(commentRequestDto);
            } else if(user.getRole().equals(UserRoleEnum.ADMIN)){
                comment.update(commentRequestDto);
            } else {
                throw new IllegalArgumentException("권한이 없습니다.");
            }

//            post.update(requestDto);

            return new CommentResponseDto(comment);
        } else {
            return null;
        }
    }

    @Transactional
    public ResponseDto deleteComment(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("댓글이 없습니다.")
            );

            if (comment.getUsername().equals(user.getUsername())) {
                commentRepository.delete(comment);
            } else if(user.getRole().equals(UserRoleEnum.ADMIN)){
                commentRepository.delete(comment);
            } else {
                throw new IllegalArgumentException("권한이 없습니다.");
            }

//            post.update(requestDto);

            return new ResponseDto("코멘트 삭제 완료", HttpStatus.OK.value());
        } else {
            return null;
        }
    }
}