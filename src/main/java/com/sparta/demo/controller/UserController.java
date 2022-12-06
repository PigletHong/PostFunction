package com.sparta.demo.controller;

import com.sparta.demo.dto.LoginRequestDto;
import com.sparta.demo.dto.ResponseDto;
import com.sparta.demo.dto.SignupRequestDto;
import com.sparta.demo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @ResponseBody
    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return new ResponseDto("성공했습니다", HttpStatus.OK.value());
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return new ResponseDto("성공했습니다", HttpStatus.OK.value());
    }

}
