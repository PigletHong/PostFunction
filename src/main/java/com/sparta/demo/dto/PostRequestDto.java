package com.sparta.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter

public class PostRequestDto {
    private String name;
    private String content;
    private String title;
    private String password;
}

