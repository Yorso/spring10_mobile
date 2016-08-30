<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
	
	<body>
	
		<h1>Tablet Info Page</h1>
		
		Site:
		<!-- Every time you clcik on "Normal" o "Mobile" link, it goes to userList() controller method -->
		<a href="?site_preference=normal">Normal</a>
		|
		<a href="?site_preference=mobile">Mobile</a>
		
		<hr/>
		
		Current Device: <c:out value="${device}"/>
		<br/>
		Current Version: <c:out value="${version}"/>
		<br/>
		Subfolder: <c:out value="${subfolder}"/>
		
	</body>
	
</html>