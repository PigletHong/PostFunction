package com.sparta.demo.service;

import com.sparta.demo.dto.LoginRequestDto;
import com.sparta.demo.dto.SignupRequestDto;
import com.sparta.demo.entity.User;
import com.sparta.demo.entity.UserRoleEnum;
import com.sparta.demo.jwt.JwtUtil;
import com.sparta.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String adminkey = signupRequestDto.getAdminkey();

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        // 회원 중복 확인

        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminkey().equals(ADMIN_TOKEN)) {
                role = UserRoleEnum.USER;
            } else {
                role = UserRoleEnum.ADMIN;
            }
            // 사용자 ROLE 확인
        }
        User user = new User(username, password, role);
        // username, password, role 값을 가진 user 객체 생성
        userRepository.save(user);
<<<<<<< HEAD:src/main/java/com/sparta/crud/service/UserService.java
        // 유저 DB에 user 객체 값 저장
        return new ResponseDto(StatusEnum.OK);
    }

    @Transactional(readOnly = true)
    public ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername(); // loginRequestDto에서 얻은 유저 이름을 username에 저장
        String password = loginRequestDto.getPassword(); // loginRequsetDto에서 얻은 패스워드를 password에 저장
=======
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
>>>>>>> parent of 4810b3e (- Spring Security 적용 및 URI별 권한 부여):src/main/java/com/sparta/demo/service/UserService.java

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
<<<<<<< HEAD:src/main/java/com/sparta/crud/service/UserService.java
        // user 객체에 유저 DB에서 유저 이름으로 얻은 값을 저장 -> 아니면 예외 처리

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CutomException(NOT_FOUND_USER);
        }
        // password의 값과 user에서 얻은 password의 값이 다르다면 예외 처리 문구
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        //response 객채에 얻은 데이터들을 담아서 반환
        return new ResponseDto(StatusEnum.OK);
    }
=======
        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
>>>>>>> parent of 4810b3e (- Spring Security 적용 및 URI별 권한 부여):src/main/java/com/sparta/demo/service/UserService.java

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
    }
}