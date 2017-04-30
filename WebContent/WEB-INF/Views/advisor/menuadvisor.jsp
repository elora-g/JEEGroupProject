<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<%--When importing a jsp file into another, you just write the sample code you need don't put all the Doctype, Head information etc --%>
		<nav class="navbar">
		<div class="container-fluid">
			<ul class="nav navbar-nav">
				<li> <a href="/JEEGroupProject/advisor/main">Accueil</a> </li>
				<li> <a href="/JEEGroupProject/advisor/disputedoperations">Opérations contestées (<c:out value="${fn:length(authenticatedPerson.disputedOperationsForClients)}"/>)</a> </li>
				<li> <a href="/JEEGroupProject/advisor/clients">Gestion de mes clients</a> </li>
				<li> <a href="/JEEGroupProject/authenticated/main">Basculer sur mes comptes personnels</a> </li>
				<li> 
					<form method="post" action="/JEEGroupProject/logout"> <%--TODO : corriger le path --%>
						<input type="submit" value="Déconnexion" class="noLabel" />
					</form>
				</li>
			</ul>
		</div>
	</nav>
