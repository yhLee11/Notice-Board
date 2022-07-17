# Notice-Board
Spring boot 게시판 구현 프로젝트
요구사항
- 커뮤니티 사이트
- 로그인 기능
- 회원 테이블에 2명의 회원
    - 1번 회원(admin)은 관리자 회원
    - 2번 회원(user1)은 일반 회원
- 게시글의 CRUD
- 게시판은 2개(1번 게시판은 공지사항, 2번 게시판은 자유게시판)
- 메인페이지에는, 각 게시판의 최신글이 10개 노출
- MySQL
- 공지사항 게시물은 전부 1번 회원(admin)이 작성한 걸로 되어야 합니다.
- 자유 게시물은 전부 2번 회원(user)가 작성한 걸로 되어야 합니다.
- JDBC 템플릿
- 2번 회원(user1)은 본인이 작성한 게시물만 수정/삭제 가능
- 1번 회원(admin)은 타인이 작성한 게시물을 삭제 가능, 수정은 불가능
---
구현과정
1. 테이블 설계

members 테이블

member_id|member_password
---|---|
admin|1234
user1|1234

posts 테이블

post_id|member_id|post_type|title|contents|updated_at|created_at
---|---|---|---|---|---|---|
0|admin|NOTICE|게시글1|안녕|2022.06.06|2022.06.06
1|user1|FREE|게시글2|안녕|2022.06.06|2022.06.06

