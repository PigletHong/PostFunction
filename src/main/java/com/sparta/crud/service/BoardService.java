package com.sparta.crud.service;

import com.sparta.crud.dto.*;
import com.sparta.crud.entity.*;
import com.sparta.crud.jwt.JwtUtil;
import com.sparta.crud.repository.BoardLikeRepository;
import com.sparta.crud.repository.BoardRepository;
import com.sparta.crud.repository.UserRepository;
import com.sparta.crud.util.exception.CutomException;
import com.sparta.crud.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.crud.util.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BoardLikeRepository boardLikeRepository;

    @Transactional
    public BoardDto createBoard(BoardRequestDto requestDto, User user) {
        Board board = boardRepository.saveAndFlush(new Board(requestDto, user.getUsername(), user.getPassword(), user));

        return new BoardDto(board);
    }

    @Transactional(readOnly = true)
    public ResponseDto getBoardList() {
        BoardListResponseDto boardListResponseDto = new BoardListResponseDto(StatusEnum.OK);
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        List<CommentDto> commentList = new ArrayList<>();
        for (Board board : boardList) {
            for (Comment comment : board.getComments()) {
                commentList.add(new CommentDto(comment));
            }
            boardListResponseDto.addBoard(new BoardDto(board, commentList));
        }
        return boardListResponseDto;
    }

    @Transactional(readOnly = true)
    public ResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        List<CommentDto> commentList = new ArrayList<>();
        for (Comment comment : board.getComments()) {
            commentList.add(new CommentDto(comment));
        }
        return new BoardCommentResponseDto(StatusEnum.OK, board, commentList);
    }

    @Transactional
    public ResponseDto update(Long id, BoardRequestDto requestDto, User user) {
            // 사용자 권한 가져와서 ADMIN 이면 무조건 수정 가능, USER 면 본인이 작성한 글일 때만 수정 가능
            UserRoleEnum userRoleEnum = user.getRole();

            Board board;

            if (userRoleEnum == UserRoleEnum.ADMIN) {
                // 입력 받은 게시글 id와 일치하는 DB 조회
                board = boardRepository.findById(id).orElseThrow(
                        () -> new CutomException(NOT_FOUND_BOARD)
                );

            } else {
                // 입력 받은 게시글 id, 토큰에서 가져온 userId와 일치하는 DB 조회
                board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                        () -> new CutomException(AUTHORIZATION)
                );
            }

            board.update(requestDto);

            List<CommentDto> commentList = new ArrayList<>();
            for (Comment comment : board.getComments()) {
                commentList.add(new CommentDto(comment));
            }

            return new BoardCommentResponseDto(StatusEnum.OK, board, commentList);
    }

    @Transactional
    public ResponseDto deleteBoard(Long id, User user) {
            UserRoleEnum userRoleEnum = user.getRole();

            Board board;

            if (userRoleEnum == UserRoleEnum.ADMIN) {
                board = boardRepository.findById(id).orElseThrow(
                        () -> new CutomException(NOT_FOUND_BOARD)
                );

            } else {
                board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                        () -> new CutomException(AUTHORIZATION)
                );
            }

            boardRepository.deleteById(id);

            return new ResponseDto(StatusEnum.OK);
    }

    public ResponseDto addlike(Long id, User user) {
        // 1. Select Memo
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CutomException(NOT_FOUND_BOARD)
        );

        Optional<BoardLike> found = boardLikeRepository.findByBoardAndUser(board, user);

        if (found.isPresent()) {
            throw new CutomException(NOT_FOUND_BOARD);
        }
        BoardLike boardLike = new BoardLike(board, user);          // DTO -> Entity

        // 3. DB insert
        boardLikeRepository.save(boardLike);
        return new ResponseDto("게시글 좋아요 등록", HttpStatus.OK.value());
    }

    public ResponseDto deletelike(Long id, User user) {
        // 1. Select Memo
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CutomException(NOT_FOUND_BOARD)
        );
        // 2. Select MemoLike
        boardLikeRepository.findByBoardAndUser(board, user).orElseThrow(
                () -> new CutomException(NOT_FOUND_BOARD)
        );
        // 3. DB Delete
        boardLikeRepository.deleteByBoardAndUser(board, user);
        return  new ResponseDto("게시글 좋아요 삭제", HttpStatus.OK.value());
    }
}
