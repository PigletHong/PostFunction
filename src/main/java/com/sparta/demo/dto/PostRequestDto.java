package com.sparta.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter

public class PostRequestDto {
    private String username;
    private String content;
    private String title;
}

