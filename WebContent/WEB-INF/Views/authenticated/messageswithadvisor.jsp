<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Messagerie</title>
</head>
<body>

	<h1>Bienvenue sur votre messagerie</h1>
	
	<c:import url="/WEB-INF/Views/authenticated/menuauth.jsp" />
		
	<section>
		<h2>Envoyer un message</h2>
		<p>Attention, votre message ne doit pas dépasser 256 charactères</p>
		<form method ="post">
			<fieldset>
				<%--attention le message doit faire max 256 charactères --%>
				<label for="messageToAdvisor">Mon Message <span class="required">*</span></label>
				<input type="text" name="messageToAdvisor"/>
				<br/>
				<input type="submit" value="Send" class="noLabel" />
				<p>${message}</p>
			</fieldset>
		</form>
	</section>
	
	
	<section class="client_message">
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

	
		
</body>
</html>