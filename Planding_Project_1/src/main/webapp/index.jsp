<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>PlanDing - Fund for Our Planet</title>
<style>
    body {
        margin: 0;
        padding: 0;
        overflow: hidden; /* 스크롤을 숨기고, 이미지가 화면을 넘어가면 짤리도록 설정 */
    }

    .image-container {
        position: absolute; /* 위치를 절대 위치로 설정 */
        top: 0;
        left: 0;
        width: 100vw; /* 화면 가로 너비에 맞게 설정 */
        height: 100vh; /* 화면 세로 높이에 맞게 설정 */
    }

    .image-container img {
        width: 100%; /* 이미지를 부모 컨테이너의 가로 크기로 조절 */
        height: 100%; /* 이미지를 부모 컨테이너의 세로 크기로 조절 */
        object-fit: cover; /* 이미지가 컨테이너에 가득 차도록 설정 */
    }
</style>
</head>
<body>

	<div class="image-container">
	    <a href="userMain.usr">
	        <img alt="indexImage" src="resources/img/index/index-Laptop.jpg">
	    </a>
	</div>

</body>
</html>
