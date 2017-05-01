<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="Connexion Page">
	<meta name="author" content="Elora Guyader">

	<link href="/assets/css/bootstrap.min.css" rel="stylesheet">
	<link href="/assets/css/generalstyle.css" rel="stylesheet" type="text/css">
	
	<title>Bienvenue</title>
</head>
<body>

	<c:import url="/WEB-INF/Views/advisor/menuadvisor.jsp" />
	
	<section class = "container2">
		<h1>Bienvenue <c:out value="${authenticatedPerson.firstname}"/> <c:out value="${authenticatedPerson.lastname}"/></h1>
	
	
	
		<h2>Présentation de votre espace professionnel</h2>
		
		<article class ="row">
			<div class="col-lg-12">
				<h3><a href="/advisor/disputedoperations">Opérations contestées</a></h3>
				<p>
					Dans cette page vous pourrez voir la liste de toutes les opérations contestées par vos clients en attente d'action de votre part.
					Vous pourrez choisir, pour chaque opération, de rembourser votre client ou de valider l'opération en question.
				</p>
			</div>
		</article>
		
		<article class ="row">
			<div class="col-lg-12">
				<h3><a href="/advisor/clients">Gestion de mes clients</a></h3>
				<p>
					Dans cette page vous pourrez voir la liste de vos clients et effectuer les actions suivantes : 
				</p>
				<ul>
					<li>Créer un nouveau client</li>
					<li>Consulter les comptes d'un client</li>
					<li>Ajouter un compte pour un client</li>
					<li>Voir et éditer certaines informations personnelles d'un client</li>
					<li>Echanger des messages avec un client</li>
				</ul>
			</div>
		</article>
		
		<article class ="row">
			<div class="col-lg-12">
				<h3><a href="/authenticated/main">Basculer sur mes comptes personnels</a></h3>
				<p>
					Si vous êtes également client de Société Agricole, vous aurez un lien pour basculer sur la gestion de vos comptes personnels
				</p>
			</div>
		</article>
	
	</section>
	
	<script src="/assets/jquery/jquery.min.js"></script>
    <script src="/assets/tether/tether.min.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>

</body>
</html>