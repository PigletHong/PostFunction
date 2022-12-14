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

<<<<<<< HEAD:src/main/java/com/sparta/crud/entity/User.java
    @OneToMany(mappedBy = "user")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

=======
    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
>>>>>>> parent of 4810b3e (- Spring Security 적용 및 URI별 권한 부여):src/main/java/com/sparta/demo/entity/User.java
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING) // index가 아닌 String 값을 저장하기 위한 코드
    private UserRoleEnum role;




    public User(String username, String password, UserRoleEnum role) {
        this.username = username; // 입력받은 username 데이터 저장
        this.password = password; // 입력받은 password 데이터 저장
        this.role = role; // 입력받은 role 데이터 저장
    }

}