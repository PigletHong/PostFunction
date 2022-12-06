package com.sparta.demo.service;

import com.sparta.demo.dto.CommentRequestDto;
import com.sparta.demo.dto.CommentResponseDto;
import com.sparta.demo.entity.Comment;
import com.sparta.demo.entity.Post;
import com.sparta.demo.entity.User;
import com.sparta.demo.jwt.JwtUtil;
import com.sparta.demo.repository.CommentRepository;
import com.sparta.demo.repository.PostRepository;
import com.sparta.demo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postsRepository;
//    private final CommentResponseDto commentResponseDto;
    private final JwtUtil jwtUtil;

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
}