package com.example.spring_jwt.controller;

import com.example.spring_jwt.Dto.PostRequestDto;
import com.example.spring_jwt.jwt.JwtTokenProvider;
import com.example.spring_jwt.model.Post;
import com.example.spring_jwt.model.ResponseModel;
import com.example.spring_jwt.repository.PostRepository;
import com.example.spring_jwt.service.PostService;
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
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PostService postService;


    @GetMapping("/post")
    public ResponseEntity<ResponseModel> getPostAll(){
        List<Post> posts = postRepository.findAll();

        ResponseModel responseModel = ResponseModel.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("전체 게시글 조회 성공")
                .data(new ArrayList<>(posts)).build();

        return new ResponseEntity<>(responseModel, responseModel.getHttpStatus());
    }

    @RequestMapping(value="/auth/post", method=RequestMethod.POST)
    public Post postPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request){

        String token = request.getHeader("Authorization");
        String username = jwtTokenProvider.getUserPk(token);
        Post post = new Post(requestDto, username);

        return postRepository.save(post);

    }


    //게시글 상세 조회
    @GetMapping("/post/{id}")
    public ResponseEntity<ResponseModel> getPostId(@PathVariable Long id){
        Post post = postService.getPostID(id);

        ArrayList<Post> postss = new ArrayList<>();
        postss.add(post);

        ResponseModel responseModel = ResponseModel.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("게시글 상세 조회 완료")
                .data(new ArrayList<>(postss)).build();

        return new ResponseEntity<>(responseModel, responseModel.getHttpStatus());
    }


    //게시글 수정
    @PutMapping("/auth/post/{id}")
    public ResponseEntity<ResponseModel> putPost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request){
        Post post = postService.getPostID(id);
        String user = post.getName();
        String token = request.getHeader("Authorization");
        String username = jwtTokenProvider.getUserPk(token);

        if (user.equals(username)){
            post.update(postRequestDto);
        }

        ArrayList<Post> postss = new ArrayList<>();
        postss.add(post);

        ResponseModel responseModel = ResponseModel.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("게시글 수정 완료")
                .data(new ArrayList<>(postss)).build();

        return new ResponseEntity<>(responseModel, responseModel.getHttpStatus());

    }

    @DeleteMapping("/auth/post/{id}")
    public ResponseEntity<ResponseModel> deletePost(@PathVariable Long id, HttpServletRequest request){
        Post post = postService.getPostID(id);
        String user = post.getName();
        String token = request.getHeader("Authorization");
        String username = jwtTokenProvider.getUserPk(token);

        if (user.equals(username)){
            postRepository.deleteById(id);
        }

        ResponseModel responseModel = ResponseModel.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("게시글 삭제 완료").build();

        return new ResponseEntity<>(responseModel, responseModel.getHttpStatus());

    }
}
