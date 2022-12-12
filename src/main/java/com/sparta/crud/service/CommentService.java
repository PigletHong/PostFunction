package com.sparta.crud.service;

import com.sparta.crud.dto.*;
import com.sparta.crud.entity.*;
import com.sparta.crud.jwt.JwtUtil;
import com.sparta.crud.repository.BoardRepository;
import com.sparta.crud.repository.CommentLikeRepository;
import com.sparta.crud.repository.CommentRepository;
import com.sparta.crud.repository.UserRepository;
import com.sparta.crud.util.exception.CutomException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static com.sparta.crud.util.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public ResponseDto addComment(Long id, CommentRequestDto commentRequestDto, User user) {
        // 게시글의 DB 저장 유무 확인
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CutomException(NOT_FOUND_BOARD)
        );

            // 요청 받은 DTO로 DB에 저장할 객체 만들기
            Comment comment = commentRepository.save(new Comment(commentRequestDto, board, user));

            return new CommentResponseDto(StatusEnum.OK, comment);
    }

    @Transactional
    public ResponseDto updateComment(Long boardId, Long cmtId, CommentRequestDto commentRequestDto, User user) {
        // 게시글의 DB 저장 유무 확인
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CutomException(NOT_FOUND_BOARD)
        );
            UserRoleEnum userRoleEnum = user.getRole();

            Comment comment;

            if (userRoleEnum == UserRoleEnum.ADMIN) {
                // 입력 받은 댓글 id와 일치하는 DB 조회
                comment = commentRepository.findById(cmtId).orElseThrow(
                        () -> new CutomException(NOT_FOUND_COMMENT)
                );
            } else {
                // 입력 받은 댓글 id, 토큰에서 가져온 userId와 일치하는 DB 조회
                comment = commentRepository.findByIdAndUserId(cmtId, user.getId()).orElseThrow(
                        () -> new CutomException(AUTHORIZATION)
                );
            }

            // 요청 받은 DTO로 DB에 업데이트
            comment.update(commentRequestDto);

            return new CommentResponseDto(StatusEnum.OK, comment);
    }

    @Transactional
    public ResponseDto deleteComment(Long boardId, Long cmtId, User user) {
        // 게시글의 DB 저장 유무 확인
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CutomException(NOT_FOUND_BOARD)
        );
            // 사용자 권한 가져와서 ADMIN 이면 무조건 수정 가능, USER 면 본인이 작성한 댓글일 때만 수정 가능
            UserRoleEnum userRoleEnum = user.getRole();

            Comment comment;

            if (userRoleEnum == UserRoleEnum.ADMIN) {
                // 입력 받은 댓글 id와 일치하는 DB 조회
                comment = commentRepository.findById(cmtId).orElseThrow(
                        () -> new CutomException(NOT_FOUND_COMMENT)
                );
            } else {
                // 입력 받은 댓글 id, 토큰에서 가져온 userId와 일치하는 DB 조회
                comment = commentRepository.findByIdAndUserId(cmtId, user.getId()).orElseThrow(
                        () -> new CutomException(AUTHORIZATION)
                );
            }

            // 해당 댓글 삭제
            commentRepository.deleteById(cmtId);

            return new ResponseDto(StatusEnum.OK);
    }

    @Transactional
    public ResponseDto addlike(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CutomException(NOT_FOUND_COMMENT)
        );

        Optional<CommentLike> found = commentLikeRepository.findByCommentAndUser(comment, user);

        if (found.isPresent()) {
            throw new CutomException(NOT_FOUND_COMMENT);
        }
        CommentLike commentLike = new CommentLike(comment, user);
        commentLikeRepository.save(commentLike);
        return new ResponseDto("댓글 좋아요 완료", HttpStatus.OK.value());
    }

    @Transactional
    public ResponseDto deletelike(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CutomException(NOT_FOUND_COMMENT)
        );

        commentLikeRepository.findByCommentAndUser(comment, user).orElseThrow(
                () -> new CutomException(NOT_FOUND_COMMENT)
        );
        commentLikeRepository.deleteByCommentAndUser(comment, user);
        return new ResponseDto("댓글 좋아요 삭제", HttpStatus.OK.value());
    }
}
