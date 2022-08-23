# 🌩️SPRING 숙련주차 개인 과제

[⛅ SPRING 숙련주차 개인과제 API 명세서](https://www.notion.so/ca222e7053114cc48444d58f3fabe5e0)

## ⛅ 요구사항

1. 아래 요구사항에 맞는 API 명세서와 ERD 설계
**ERD 설계 →** [https://www.erdcloud.com/](https://www.erdcloud.com/)
    
    **API 명세서 작성 툴 →** [https://learnote-dev.com/java/Spring-A-문서-작성하기/](https://learnote-dev.com/java/Spring-A-%EB%AC%B8%EC%84%9C-%EC%9E%91%EC%84%B1%ED%95%98%EA%B8%B0/)
    
- 회원 가입 API
    - [x]  닉네임, 비밀번호, 비밀번호 확인을 request에서 전달받기
    - [ ]  닉네임은 `최소 4자 이상, 12자 이하 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성하기
    - [ ]  비밀번호는 `최소 4자 이상이며, 32자 이하 알파벳 소문자(a~z), 숫자(0~9)` 로 구성하기
    - [x]  비밀번호 확인은 비밀번호와 정확하게 일치하기
- 로그인 API
    - [ ]  닉네임, 비밀번호를 request에서 전달받기
    - [ ]  로그인 버튼을 누른 경우 닉네임과 비밀번호가 데이터베이스에 등록됐는지 확인하기
    - [ ]  로그인 성공 시, JWT를 활용하여 AccessToken을 발급하고, 
    발급한 AccessToken은 Header의 Access-Token에 담아서 반환하기
    - [ ]  로그인 성공 시, JWT를 활용하여 RefreshToken을 발급하고,
    발급한 RefreshToken은 Header의 Refresh-Token에 담아서 반환하기
    - **참고 자료**
        1. [https://www.youtube.com/watch?v=ewslpCROKXY&t=440s](https://www.youtube.com/watch?v=ewslpCROKXY&t=440s)
        2. [https://www.inflearn.com/course/스프링부트-jwt](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-jwt)
        3. [https://bcp0109.tistory.com/301](https://bcp0109.tistory.com/301)
    
- 로그인 검사
    - [ ]  `아래 API를 제외하고` 모두 AccessToken, RefreshToken을 전달한 경우만 정상 response를 전달받을 수 있도록 하기
        - 회원가입 API
        - 로그인 API
        - 게시글 목록 조회 API
        - 게시글 조회 API
        - 댓글 목록 조회 API
    - cf. Authorization에 담긴 AccessToken으로 사용자 판단
- 댓글 목록 조회 API
    - [ ]  AccessToken이 없어도 댓글 목록 조회가 가능하도록 하기
    - [ ]  조회하는 게시글에 작성된 모든 댓글을 response에 포함하기
- 댓글 작성 API
    - [ ]  AccessToken이 있고, 유효한 Token일 때만 댓글 작성이 가능하도록 하기
- 댓글 수정 API
    - [ ]  AccessToken이 있고, 유효한 Token이면서 해당 사용자가 작성한 댓글만 수정 가능하도록 하기
- 댓글 삭제 API
    - [ ]  AccessToken이 있고, 유효한 Token이면서 해당  사용자가 작성한 댓글만 삭제 가능하도록 하기
- 예외 처리
    - [ ]  Refresh Token을 전달하지 않거나 정상 토큰이 아닐 때는 "Token이 유효하지 않습니다." 라는 에러 메세지를 response에 포함하기
    - [ ]  데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "중복된 닉네임입니다." 라는 에러메세지를 response에 포함하기
    - [ ]  비밀번호와 비밀번호 확인 값이 일치하지 않을 때 “비밀번호와 비밀번호 확인이 일치하지 않습니다.” 라는 에러 메세지를 resonse에 포함하기
    - [ ]  로그인 시, 전달된 닉네임과 비밀번호 중 맞지 않는 정보가 있다면 "사용자를 찾을 수 없습니다."라는 에러 메세지를 response에 포함하기
    - [ ]  AccessToken이 있고, 유효한 Token이면서 해당 사용자가 작성한 게시글/댓글이 아닌 경우에는 “작성자만 삭제할 수 있습니다.”라는 에러 메세지를 response에 포함하기
