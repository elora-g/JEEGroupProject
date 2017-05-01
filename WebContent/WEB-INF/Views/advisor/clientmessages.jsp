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

	<link href="/assets/css/bootstrap.min.css" rel="stylesheet">
	<link href="/assets/css/generalstyle.css" rel="stylesheet" type="text/css">
	<title>Mes messages avec mon client</title>
</head>
<body>

	<c:import url="/WEB-INF/Views/advisor/menuadvisor.jsp" />
	
	<div class="container2">
	
	<h1>Bienvenue sur votre messagerie avec votre client n° <c:out value="${currentClient.externalId}"/> : <c:out value="${currentClient.firstname}"/> <c:out value="${currentClient.lastname}"/></h1>
	
		<section>
			<h2>Envoyer un message</h2>
			<p>Attention, votre message ne doit pas dépasser 128 charactères</p>
			<form method ="post">
				<fieldset>
					<%--attention le message doit faire max 128 charactères --%>
					<label for="messageToClient">Mon Message <span class="required">*</span></label>
					<textarea name="messageToClient"></textarea>
					<br/>
					<input type="submit" value="Send" class="noLabel" />
					<p>${message}</p>
				</fieldset>
			</form>
		</section>
		
		
		<section class="client_message">
			<h2>Echanges avec mon client n°<c:out value="${currentClient.externalId}"/></h2>
			<c:forEach var="message" items="${currentClient.messagesWithAdvisor}" >
				<article>
					<h3>De 
						<c:choose>
							<c:when test="${message.from == authenticatedPerson.id}">
								vous :
							</c:when>
							<c:otherwise>
								votre Client :
							</c:otherwise>
						</c:choose>
						<c:out value="${message.createdAt}"></c:out>
					</h3>
					<p><c:out value="${message.content}"></c:out></p>
				</article>
			</c:forEach>
		</section>
	</div>
	<script src="/assets/jquery/jquery.min.js"></script>
    <script src="/assets/tether/tether.min.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
	
</body>
</html>