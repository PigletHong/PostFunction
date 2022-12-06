package com.sparta.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
//    @Size(min = 4, max = 10, message = "아이디 길이 규정을 확인해 주세요.")
//    @Pattern(regexp = "[a-z0-9]*$", message = "아이디 형식이 일치하지 않습니다.")
    private String username;

    @Column(nullable = false)
//    @Size(min = 8, max = 15, message = "패스워드 길이 규정을 확인해 주세요.")
//    @Pattern(regexp = "[A-Za-z0-9]*$", message = "패스워드 형식이 일치하지 않습니다.")
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    private String  adminkey;



    public User(String username, String password, UserRoleEnum role, String adminkey) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.adminkey = this.adminkey;
    }

}