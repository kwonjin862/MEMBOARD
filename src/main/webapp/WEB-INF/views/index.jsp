<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MEMBOARD</title>
</head>
<body>
	<h1>회원제 게시판 프로젝트</h1>
	<c:choose>
	
	<c:when test="${loginId eq 'admin'}">
		<h3>관리자로 로그인 합니다.</h3>
		<button onclick="location.href='mList'">회원목록</button>
		<button onclick="location.href='mLogout'">로그아웃</button>
		<button onclick="location.href='bWriteForm?loginId=${loginId}'">게시글작성</button>
		<button onclick="location.href='bList'">게시글목록</button>
	</c:when>
	
	<c:when test="${loginId != null}">
		<h3>${loginId}님 환영합니다.</h3>
		<button onclick="location.href ='mView?memId=${loginId}'">내 정보보기</button>
		<button onclick="location.href='mLogout'">로그아웃</button>
		<button onclick="location.href='bWriteForm?loginId=${loginId}'">게시글작성</button>
		<button onclick="location.href='bList'">게시글목록</button>
	</c:when>
	
	<c:otherwise>
		<button onclick="location.href='mJoinForm'">회원가입</button>
		<button onclick="location.href='mLoginForm'">로그인</button>
		<button onclick="location.href='bList'">게시글목록</button>
	</c:otherwise>
	
	</c:choose>
	
</body>
</html>