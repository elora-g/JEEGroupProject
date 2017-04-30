<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Bienvenue</title>
</head>
<body>
	<p>Bienvenue Advisor</p>
	
	<c:out value="${authenticatedPerson.email}"></c:out>
	<br />
	
	<p>Nombre de clients pour cet advisor <c:out value="${authenticatedPerson.clients}"></c:out></p>
	
	<c:import url="/WEB-INF/Views/advisor/menuadvisor.jsp" />
	<br />
	

	

</body>
</html>