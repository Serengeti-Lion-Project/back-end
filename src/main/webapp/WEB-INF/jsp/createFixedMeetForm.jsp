<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" >
<head>
    <meta charset="UTF-8">
    <title>팀 meet 만드는 페이지</title>
</head>
<body>
<h3>구성자체는 일회용 meet 생성 페이지 (onceGroup.html)이랑 똑같은데, team_id를 저장해둬야해서,,, jsp로 만들었어요</h3>
<form action="/fixed/newmeet" method="post">
    제목 입력하세요 : <input id = "title" name ="title" type="text" placeholder="제목을 입력하세요"> <br>
    시작날짜 : <input id = "start_date" name ="start_date" type="date"> <br>
    끝 날짜 : <input id = "end_date" name ="end_date" type="date"> <br>
    시작시간 : <input id = "start_time" name="start_time" type="text"> <br>
    끝 시간 : <input id = "end_time" name="end_time" type="text"> <br>
    <input type="submit" value = "시작하기">
    <input type="hidden" id="team_id" name="team_id" value="${team_id}">
</form>

</body>
</html>