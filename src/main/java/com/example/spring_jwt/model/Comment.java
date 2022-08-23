package com.example.spring_jwt.model;

import com.example.spring_jwt.Dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Comment extends Timestamped{

    @Id // ID 값, Primary Key로 사용하겠다는 뜻입니다.
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동 증가 명령입니다.
    private Long id;

    @Column(nullable = false)
    private int post_id;

    @Column(nullable = false) // 컬럼 값이고 반드시 값이 존재해야 함을 나타냅니다.
    private String name;

    @Column(nullable = false)
    private String comment;

    public Comment(CommentRequestDto requestDto, String username){
        this.post_id = requestDto.getPost_id();
        this.name = username;
        this.comment = requestDto.getComment();
    }

    public void update(CommentRequestDto requestDto){
        this.post_id = requestDto.getPost_id();
        this.comment = requestDto.getComment();
    }


}
