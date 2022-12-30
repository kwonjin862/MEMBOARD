<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board_게시글수정</title>
</head>
<body>

	 <form action="bModify" method="POST" enctype="multipart/form-data">
	 <input type="hidden" name="boNum" value="${modify.boNum}"/>
	 <input type="hidden" name="boFileName" value="${modify.boFileName}"/>
	 
	 <table>
	 <caption>게시글 수정</caption>
	 	
	 	<tr>
	 		<th>작성자</th>
	 		<td>${modify.boWriter}<input type="hidden" name="boWriter" value="${modify.boWriter}"/></td>
	 	</tr>
	 	<tr>
	 		<th>제목</th>
	 		<td><input type="text" name="boTitle" placeholder="${modify.boTitle}"/></td>
	 	</tr>
	 	<tr>
	 		<th>내용</th>
	 		<td><textarea name="boContent" placeholder="${modify.boContent}"></textarea></td>
	 	</tr>
	 	
	 	<tr>
	 		<th>파일업로드</th>
	 		<td><input type="file" name="boFile"/></td>
	 	</tr>
	 	<tr>
	 		<th colspan="2"><input type="submit" value="수정"/></th>
	 	</tr>
	 </table>
	 
	 
	 </form>
	 
</body>
</html>