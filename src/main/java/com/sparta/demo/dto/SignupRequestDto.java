package com.sparta.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class SignupRequestDto {

    @Size(min = 4, max = 10, message = "아이디 길이 규정을 확인해 주세요.")
    @Pattern(regexp = "[a-z0-9]*$", message = "아이디 형식이 일치하지 않습니다.")
    private String username;

    @Size(min = 8, max = 15, message = "패스워드 길이 규정을 확인해 주세요.")
    @Pattern(regexp = "[A-Za-z0-9]*$", message = "패스워드 형식이 일치하지 않습니다.")
    private String password;
    private boolean admin = true;
//    private String admin = "";
    private String admintoken = "";
    private String adminkey = "";
}