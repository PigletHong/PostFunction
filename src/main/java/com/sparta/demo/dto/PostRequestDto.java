package com.sparta.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@Setter

public class PostRequestDto {
    private String username;
    private String content;
    private String title;
//    private List<CommentResponseDto> comments;
}

