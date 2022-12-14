package com.sparta.demo.dto;

import com.sparta.demo.entity.Comment;
import com.sparta.demo.entity.Post;
import com.sparta.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {
    private Long id;
    private String comment;
    private User user;
    private Post post;
    private String username;


    /* Dto -> Entity */
    public Comment toEntity() {
        Comment comments = Comment.builder()
                .id(id)
                .comment(comment)
                .user(user)
                .post(post)
                .username(username)
                .build();

        return comments;
    }
}