<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board_페이징목록</title>
<style>
	table, td, th{
		border : 1px solid black;
		border-collapse : collapse;
		padding : 10px;
	}
</style>
</head>
<body>
	<table>
	<caption>페이징 목록</caption>
	
	<tr>
		<th>번호</th>
		<th>작성자</th>
		<th>제목</th>
		<th>작성일</th>
		<th>조회수</th>
	</tr>
	
	<c:forEach var="list" items="${pagingList}">
	<tr>
		<td>${list.boNum}</td>
		<td>${list.boWriter}</td>
		<td><a href="bView?boNum=${list.boNum}">${list.boTitle}</a></td>
		<td>${list.boDate}</td>
		<td>${list.boHit}</td>
	</tr>
	</c:forEach>
	<tr>
			<td colspan="5">
				<!-- 검색기능 -->
				<form action="bSearch" method="GET">
				
				<!-- 카테고리 -->
				<select name="category">
					<option value="BOWRITER">작성자</option>				
					<option value="BOTITLE">글 제목</option>	
					<option value="BOCONTENT">글 내용</option>							
				</select>
				
				<!-- 검색어 -->
				<input type="text" name="keyword"/>
				
				<!-- 검색버튼 -->
				<input type="submit" value="검색"/>	
							
				</form>
			</td>
		</tr>
	</table>
</body>
</html>