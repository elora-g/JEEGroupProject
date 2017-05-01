<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<%--When importing a jsp file into another, you just write the sample code you need don't put all the Doctype, Head information etc --%>
		<nav class="navbar fixed-top navbar-toggleable-md navbar-inverse bg-inverse">
			<div class="container">
				<a class="navbar-brand" href="/advisor/main">Société Agricole</a>
				<div class="collapse navbar-collapse">
					<ul class="navbar-nav ml-auto">
						<li class="nav-item"> <a class="nav-link" href="/advisor/main">Accueil</a> </li>
						<li class="nav-item"> <a class="nav-link" href="/advisor/disputedoperations">Opérations contestées (<c:out value="${fn:length(authenticatedPerson.disputedOperationsForClients)}"/>)</a> </li>
						<li class="nav-item"> <a class="nav-link" href="/advisor/clients">Gestion de mes clients</a> </li>
						<li class="nav-item"> <a class="nav-link" href="/authenticated/main">Basculer sur mes comptes personnels</a> </li>
					</ul>
				</div>
				<form class="nav-item" method="post" action="/logout"> 
								<input type="submit" value="Déconnexion" class="noLabel" />
				</form>
			</div>
		</nav>
