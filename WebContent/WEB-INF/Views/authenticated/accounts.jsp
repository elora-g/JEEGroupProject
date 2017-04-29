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
	<p>Bienvenue Client Accounts</p>

	<p>Mes comptes</p>
	<p>Mon Compte 1</p>
	<p>Opérations sur mon compte 1</p>
	<p>Opération en cours 1 + bouton contester</p>
	<p>Opération comptabilisée 1 + bouton contester</p>

	<c:out value="${authenticatedPerson.email}"></c:out>
	

	
</body>
</html>