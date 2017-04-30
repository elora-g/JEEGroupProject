<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bienvenue</title>
</head>
<body>
	<p>importer la jsp menuauth</p>
	<p>Bienvenue Client</p>
	<c:import url="/WEB-INF/Views/authenticated/menuauth.jsp" />

	
	<c:out value="${authenticatedPerson.email}"></c:out>
	
</body>
</html>