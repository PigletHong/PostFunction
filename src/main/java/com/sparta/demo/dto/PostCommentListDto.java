package com.sparta.demo.dto;

import com.sparta.demo.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostCommentListDto extends PostCommentResponseDto {

    static List<PostCommentResponseDto> postList = new ArrayList<>();

    public PostCommentListDto(Post post) {
    }

    public void addPost(PostCommentResponseDto postCommentResponseDto) {
        postList.add(postCommentResponseDto);
    }
}
