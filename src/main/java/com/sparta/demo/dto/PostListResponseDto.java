package com.sparta.demo.dto;


import com.sparta.demo.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostListResponseDto {
//    private String username;
//    private String content;
//    private String title;
//    private Long id;
//
//    private List<CommentResponseDto> comments;
//    @CreatedDate
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    private LocalDateTime modifiedAt;

    List<PostCommentResponseDto> postList = new ArrayList<>();

    public PostListResponseDto(Post post) {
//        this.username = post.getUsername();
//        this.content = post.getContent();
//        this.title = post.getTitle();
//        this.id = post.getId();
//        this.createdAt = post.getCreatedAt();
//        this.modifiedAt = post.getModifiedAt();
//        this.comments = post.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    public void addPost(PostCommentResponseDto postCommentResponseDto) {
        postList.add(postCommentResponseDto);
    }
}
