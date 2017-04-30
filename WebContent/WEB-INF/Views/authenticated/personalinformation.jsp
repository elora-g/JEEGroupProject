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

	<p>Mes informations personnelles</p>
	
	<c:import url="/WEB-INF/Views/authenticated/menuauth.jsp" />
	
	<p>Liste de mes informations personnelles</p>

	<p>Formulaire pour éditer mes informations personnelles</p>


	<c:out value="${authenticatedPerson.email}"></c:out>
	
</body>
</html>