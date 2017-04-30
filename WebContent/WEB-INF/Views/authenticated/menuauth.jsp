<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<%--When importing a jsp file into another, you just write the sample code you need don't put all the Doctype, Head information etc --%>
	<nav class="navbar">
		<div class="container-fluid">
			<ul class="nav navbar-nav">
				<li > <a href="#">Accueil</a> </li>
				<li> <a href="#">Mes Comptes</a> </li>
				<li> <a href="#">Mes Informations Personnelles</a> </li>
				<li> <a href="#">Messagerie</a> </li>
				<c:if test="${authenticatedPerson.isAdvisor}">
					<li> <a href="/JEEGroupProject/advisor/main">Je veux voir mon dashboard</a> </li>
				</c:if>
				<li> 
					<form method="post" action="/JEEGroupProject/logout"> <%--TODO : corriger le path --%>
						<input type="submit" value="Déconnexion" class="noLabel" />
					</form>
				</li>
			</ul>
		</div>
	</nav>
		
	
