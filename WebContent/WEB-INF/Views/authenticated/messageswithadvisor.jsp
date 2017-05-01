<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="Connexion Page">
	<meta name="author" content="Elora Guyader">

	<link href="/JEEGroupProject/assets/css/bootstrap.min.css" rel="stylesheet">
	<link href="/JEEGroupProject/assets/css/generalstyle.css" rel="stylesheet" type="text/css">
	<title>Messagerie</title>
</head>
<body>
	
	<c:import url="/WEB-INF/Views/authenticated/menuauth.jsp" />
	
	<div class="container2">	
		
		<h1>Bienvenue sur votre messagerie</h1>
	
		<section>
			<h2>Envoyer un message</h2>
			<p>Attention, votre message ne doit pas dépasser 128 charactères</p>
			<form method ="post">
				<fieldset>
					<%--attention le message doit faire max 128 charactères --%>
					<label for="messageToAdvisor">Mon Message <span class="required">*</span></label>
					<textarea name="messageToAdvisor">
					</textarea>
					<br/>
					<input type="submit" value="Send" class="noLabel" />
					<p>${message}</p>
				</fieldset>
			</form>
		</section>
		
		
		<section class="advisor_message">
			<h2>Echanges avec mon Conseiller</h2>
			<c:forEach var="message" items="${authenticatedPerson.messagesWithAdvisor}" >
				<article>
					<h3>De 
						<c:choose>
							<c:when test="${message.from == authenticatedPerson.id}">
								vous :
							</c:when>
							<c:otherwise>
								votre Conseiller :
							</c:otherwise>
						</c:choose>
						<c:out value="${message.createdAt}"></c:out>
					</h3>
					<p><c:out value="${message.content}"></c:out></p>
				</article>
			</c:forEach>
		</section>
	</div>
	<script src="/JEEGroupProject/assets/jquery/jquery.min.js"></script>
    <script src="/JEEGroupProject/assets/tether/tether.min.js"></script>
    <script src="/JEEGroupProject/assets/js/bootstrap.min.js"></script>
		
</body>
</html>