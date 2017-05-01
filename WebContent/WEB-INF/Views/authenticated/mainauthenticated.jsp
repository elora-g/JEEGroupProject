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
	
	<title>Bienvenue</title>
</head>
<body>

	<c:import url="/WEB-INF/Views/authenticated/menuauth.jsp" />
	
<section class = "container2">

		<h1>Bienvenue <c:out value="${authenticatedPerson.firstname}"/> <c:out value="${authenticatedPerson.lastname}"/></h1>
	
		<h2>Présentation de votre espace personnel</h2>
		
		<article class ="row">
			<div class="col-lg-12">
				<h3><a href="/JEEGroupProject/authenticated/accounts">Mes comptes</a></h3>
				<p>
					Dans cette page vous pourrez voir la liste de vos comptes et toutes les opérations associées.
				</p>
				<p>
					Un problème avec une opération? Vous pourrez la contester facilement et votre conseiller en sera notifié immédiatement.
				</p>
			</div>
		</article>
		
		<article class ="row">
			<div class="col-lg-12">
				<h3><a href="/JEEGroupProject/authenticated/personalinformation">Mes informations personnelles</a></h3>
				<p>
					Dans cette page vous pourrez accéder à vos informations personnelles et les modifier.
				</p>
			</div>
		</article>
		
		<article class ="row">
			<div class="col-lg-12">
				<h3><a href="/JEEGroupProject/authenticated/messages">Messagerie</a></h3>
				<p>
					Une question, une information à partager? Vous pourrez échanger avec votre conseiller sur cette page.
				</p>
			</div>
		</article>
	
	</section>

	<script src="/JEEGroupProject/assets/jquery/jquery.min.js"></script>
    <script src="/JEEGroupProject/assets/tether/tether.min.js"></script>
    <script src="/JEEGroupProject/assets/js/bootstrap.min.js"></script>
    
</body>
</html>