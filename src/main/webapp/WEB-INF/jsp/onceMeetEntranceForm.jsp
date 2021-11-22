<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<body>
<div class="container">

    <form action="/once/new/createUser" method="post">
    <div class="form-group">
        <label for="page_pw">방 비밀번호</label>
        <input type="password" id="page_pw" name="page_pw" placeholder="방 비밀번호를 입력하세요">
            </div>
        <div class="form-group">
            <label for="name">이름</label>
            <!--      dto 에서 받을 변수명과 동일하게 name 값 지정-->
            <input type="text" id="name" name="name" placeholder="이름을
입력하세요">
        </div>
        <div class="form-group">
            <label for="upw">비밀번호</label>
            <!--      dto 에서 받을 변수명과 동일하게 name 값 지정-->
            <input type="password" id="upw" name="upw" placeholder="비밀번호를
입력하세요">
        </div>
        <input type="hidden" id="url_id" name="url_id" value="${urlid}">
        <input type="hidden" id="title" name="title" value="${title}">
<!-- model에서 보낸 title, urlid 출력-->
        <button type="submit">등록</button>
    </form>
</div> <!-- /container -->
</body>
</html>