package com.sparta.demo.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "a-z0-9")
    private String username;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "a-zA-Z0-9")
    private String password;
}