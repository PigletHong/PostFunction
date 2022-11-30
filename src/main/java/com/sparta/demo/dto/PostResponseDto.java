package com.sparta.demo.dto;


import com.sparta.demo.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private String name;
    private String content;
    private String title;
    private String password;
    private Long id;
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.name = post.getName();
        this.content = post.getContent();
        this.title = post.getTitle();
        this.password = post.getPassword();
        this.id = post.getId();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();

    }
}
