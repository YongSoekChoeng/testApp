<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
	<script type="text/javascript" src="/resources/js/jquery/jquery-1.11.3.js"></script>
	<meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="theme-color" content="#3a7bfe"/>
	<meta http-equiv="Cache-Control" content="max-age=86400">
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<meta name="viewport" content="user-scalable=no, viewport-fit=cover, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width">
	
	<title>테스트 앱 (testApp)</title>
	
<style>
body, html {
    height: 100%;
    margin: 0;
}
</style>
	<script>
	$(document).ready(function() {
		$("#loginForm").submit();
	});
	</script>
</head>

<body>
	<form name="loginForm" id="loginForm" action="/login.do" method="POST">
		<input type="hidden" name="mb_id" value="${mb_id}"/>
		<input type="hidden" name="login_id" value="${login_id }">
		<input type="hidden" name="login_pw" value="notNecessaryPassword!@#">
		<input type="hidden" name="access_token" value="${access_token }">
	</form>
</body>
</html>