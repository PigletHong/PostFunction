package com.sparta.demo.entity;

import com.sparta.demo.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post extends com.sparta.demo.entity.Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments;


    public Post(PostRequestDto requestDto,String username, Long userId) {
        this.username = username;
        this.content = requestDto.getContent();
        this.title = requestDto.getTitle();
        this.userId = userId;
    }

    public void update(PostRequestDto responseDto) {
        this.title = responseDto.getTitle();
        this.content = responseDto.getContent();
    }
}
