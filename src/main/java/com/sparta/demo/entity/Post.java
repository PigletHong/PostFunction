package com.sparta.demo.entity;

import com.sparta.demo.dto.PostListResponseDto;
import com.sparta.demo.dto.PostRequestDto;
import com.sparta.demo.dto.PostResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Post extends com.sparta.demo.entity.Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String name;
    private String password;
    private String content;


    public Post(PostRequestDto requestDto) {
        this.name = requestDto.getName();
        this.content = requestDto.getContent();
        this.title = requestDto.getTitle();
        this.password = requestDto.getPassword();
    }

    public void update(PostRequestDto responseDto) {
        this.title = responseDto.getTitle();
        this.content = responseDto.getContent();
        this.name = responseDto.getName();
        this.password = responseDto.getPassword();
    }
}
