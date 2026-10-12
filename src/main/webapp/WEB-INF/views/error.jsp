<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>오류 - RTS</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/rts.css'/>">
</head>
<body>
<h1>오류</h1>
<p><c:out value="${message}"/></p>
<p><a href="<c:url value='/'/>">처음으로</a></p>
</body>
</html>
