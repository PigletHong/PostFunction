package com.sparta.demo.dto;

import com.sparta.demo.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostCommentResponseDto {
    private String username;
    private String content;
    private String title;
    private Long id;

    private List<CommentResponseDto> comments;

    public PostCommentResponseDto(Post post) {
        this.username = post.getUsername();
        this.content = post.getContent();
        this.title = post.getTitle();
        this.id = post.getId();
        this.comments = post.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

}
