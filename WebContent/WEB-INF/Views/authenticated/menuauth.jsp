<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<%--When importing a jsp file into another, you just write the sample code you need don't put all the Doctype, Head information etc --%>
	<nav class="navbar fixed-top navbar-toggleable-md navbar-inverse bg-inverse">

		<div class="container">
			<a class="navbar-brand" href="/JEEGroupProject/authenticated/main">Société Agricole</a>
			<div class="collapse navbar-collapse">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item"> <a class="nav-link" href="/JEEGroupProject/authenticated/main">Accueil</a> </li>
					<li class="nav-item"> <a class="nav-link" href="/JEEGroupProject/authenticated/accounts">Mes Comptes</a> </li>
					<li class="nav-item"> <a class="nav-link" href="/JEEGroupProject/authenticated/personalinformation">Mes Informations Personnelles</a> </li>
					<li class="nav-item"> <a class="nav-link" href="/JEEGroupProject/authenticated/messages">Messagerie</a> </li>
					<c:if test="${authenticatedPerson.isAdvisor}">
						<li class="nav-item"> <a class="nav-link" href="/JEEGroupProject/advisor/main">Je veux voir mon dashboard</a> </li>
					</c:if>
				</ul>
			</div>
			<form class="nav-item" method="post" action="/JEEGroupProject/logout"> <%--TODO : corriger le path --%>
				<input type="submit" value="Déconnexion" class="noLabel" />
			</form>
		</div>
	</nav>
		
		