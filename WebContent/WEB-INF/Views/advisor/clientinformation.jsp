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
	<title>Détail de mon client</title>
</head>
<body>

	<c:import url="/WEB-INF/Views/advisor/menuadvisor.jsp" />
	
	<div class="container2">
	
		<h1>Les informations personnelles du client n°<c:out value= "${currentClient.externalId}"/></h1>
		
		<section id = "personal_info_by_advisor">
			<h2>Détail des informations personnelles</h2>
			<ul>
				<li>Nom : <c:out value= "${currentClient.lastname}"/></li>
				<li>Prénom : <c:out value= "${currentClient.firstname}"/></li>
				<li>Identifiant : <c:out value= "${currentClient.externalId}"/></li>
				<li>Date de naissance : <c:out value= "${currentClient.dob}"/></li>
				<li>Email : <c:out value= "${currentClient.email}"/></li>
				<li>Téléphone : <c:out value= "${currentClient.phoneNumber}"/></li>
			</ul>
		</section>
		
		<section id = "personal_info_modify_form_by_advisor">
			<h2>Formulaire pour éditer les informations personnelles du client</h2>
			<p>Vous ne pouvez pas modifier l'état civil (nom, prénom et date de naissance) du client sans pièce justificative et autorisation du responsable d'agence.</p>
			<form method="post">
				<fieldset>
					<label for="email">Adresse email</label>
		            <input type="email" name="email" size="20" maxlength="60" />
		            <br />
		            
		            <label for="phoneNumber">Numéro de Téléphone</label>
		            <input type="text" name="phoneNumber" size="20" maxlength="60" />
		            <br />
					
		            <input type="submit" value="changeInfo" class="noLabel" />
		            <br />
		            
		            <p>${message}</p>
	                
	            </fieldset>
			</form>
		</section>
	</div>
	<script src="/JEEGroupProject/assets/jquery/jquery.min.js"></script>
    <script src="/JEEGroupProject/assets/tether/tether.min.js"></script>
    <script src="/JEEGroupProject/assets/js/bootstrap.min.js"></script>
</body>
</html>