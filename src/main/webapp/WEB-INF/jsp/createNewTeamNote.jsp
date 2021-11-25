<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" >
<head>
    <meta charset="UTF-8">
    <title>회의록 추가하기</title>
</head>
<body>
<h3>회의록 추가하기</h3>
<form action="/teampage/new" method="post">
    <input id = "note_title" name ="note_title" type="text" placeholder="제목을 입력하세요"> <br>
    글내용 : <input id = "note_content" name ="note_content" type="text"> <br>
    작성날짜 : <input type="date" id="write_date" name="write_date" > <br>
    <p>작성날짜는 프론트에서 알아서 처리해줄거니까 일단 직접 입력하는거로 </p>
    <input type="submit" value = "Save">
    <input type="hidden" id="team_id" name="team_id" value="${team_id}">

</form>

</body>
</html>