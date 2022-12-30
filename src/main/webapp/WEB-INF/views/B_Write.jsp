<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board_게시글작성</title>
</head>
<body>
	<h1>게시글 작성페이지</h1>
	<!-- 
		BoardDTO의 내용을 보고 
		form태그로 넘겨야 할 정보를 테이블에 작성하기
		- name값
		- type형태
		
		action = "bWrite"
		method = "POST"
	
	 -->
	 <form action="bWrite" method="POST" enctype="multipart/form-data">
	 <table>
	 <caption>게시글 작성</caption>
	 	
	 	<tr>
	 		<th>작성자</th>
	 		<td>${loginId}<input type="hidden" name="boWriter" value="${loginId}"/></td>
	 	</tr>
	 	<tr>
	 		<th>제목</th>
	 		<td><input type="text" name="boTitle"/></td>
	 	</tr>
	 	<tr>
	 		<th>내용</th>
	 		<td><textarea name="boContent"></textarea></td>
	 	</tr>
	 	
	 	<tr>
	 		<th>파일업로드</th>
	 		<td><input type="file" name="boFile"/></td>
	 	</tr>
	 	<tr>
	 		<th colspan="2"><input type="submit" value="등록"/></th>
	 	</tr>
	 </table>
	 </form>
	 
</body>
</html>