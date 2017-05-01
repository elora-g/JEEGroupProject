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

	<p>Bienvenue <c:out value="${authenticatedPerson.firstname}"/> <c:out value="${authenticatedPerson.lastname}"/></p>

	<c:import url="/WEB-INF/Views/advisor/menuadvisor.jsp" />

</body>
</html>