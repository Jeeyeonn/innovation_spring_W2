package com.example.spring_jwt.controller;

import com.example.spring_jwt.Dto.CommentRequestDto;
import com.example.spring_jwt.jwt.JwtTokenProvider;
import com.example.spring_jwt.model.Comment;
import com.example.spring_jwt.model.ResponseModel;
import com.example.spring_jwt.repository.CommentRepository;
import com.example.spring_jwt.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    public CommentService commentService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CommentRepository repository;


    //해당 게시글 댓글 가져오기
    @GetMapping("/comment/{id}")
    public ResponseEntity<ResponseModel> getComment(@PathVariable Long id){
        int intid = id.intValue();
        List<Comment> comments = commentService.getComment(intid);
        ResponseModel responseModel = ResponseModel.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("해당 게시글 댓글 조회 성공")
                .commentlist(new ArrayList<>(comments)).build();

        return new ResponseEntity<>(responseModel, responseModel.getHttpStatus());
    }

    //댓글 작성
    @PostMapping("/auth/comment")
    public ResponseEntity<ResponseModel> postComment(HttpServletRequest request, @RequestBody CommentRequestDto requestDto){
        String token = request.getHeader("Authorization");
        String username = jwtTokenProvider.getUserPk(token);

        Comment comment = new Comment(requestDto,username);
        repository.save(comment);

        ArrayList<Comment> c = new ArrayList<>();
        c.add(comment);

        ResponseModel responseModel = ResponseModel.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("댓글 작성 완료")
                .commentlist(new ArrayList<>(c)).build();

        return new ResponseEntity<>(responseModel, responseModel.getHttpStatus());
    }

    //댓글 수정
    @PutMapping("/auth/comment/{id}")
    public ResponseEntity<ResponseModel> putComment(HttpServletRequest request,
                                                    @RequestBody CommentRequestDto requestDto, @PathVariable Long id){
        String token = request.getHeader("Authorization");
        String username = jwtTokenProvider.getUserPk(token);

        Comment comment = commentService.upDateComment(id, requestDto, username);

        ArrayList<Comment> c = new ArrayList<>();
        c.add(comment);

        ResponseModel responseModel = ResponseModel.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("댓글 수정 완료")
                .commentlist(new ArrayList<>(c)).build();

        return new ResponseEntity<>(responseModel, responseModel.getHttpStatus());
    }

    @DeleteMapping("/auth/comment/{id}")
    public ResponseEntity<ResponseModel> DeleteComment(HttpServletRequest request,
                                                       @PathVariable Long id){
        String token = request.getHeader("Authorization");
        String username = jwtTokenProvider.getUserPk(token);
        int intid = id.intValue();
        commentService.DeleteComment(intid, username);

        ResponseModel responseModel = ResponseModel.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("댓글 삭제 완료").build();
        return new ResponseEntity<>(responseModel, responseModel.getHttpStatus());
    }

}
