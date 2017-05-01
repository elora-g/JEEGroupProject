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
	<title>Informations Personnelles</title>
</head>
<body>

	<c:import url="/WEB-INF/Views/authenticated/menuauth.jsp" />
	
	<div class="container2">	
	
		<h1>Mes informations personnelles</h1>
		
		<section id = "personal_info">
			<h2>Détail de mes informations personnelles</h2>
			<ul class="nodeco">
				<li>Nom : <c:out value="${authenticatedPerson.lastname}"/>  </li>
				<li>Prénom :   <c:out value="${authenticatedPerson.firstname}"/>  </li>
				<li>Identifiant : <c:out value="${authenticatedPerson.externalId}"/>   </li>
				<li>Date de naissance :   <c:out value= "${authenticatedPerson.dob}"/> </li>
				<li>Email :   <c:out value="${authenticatedPerson.email}"/>  </li>
				<li>Téléphone :  <c:out value="${authenticatedPerson.phoneNumber}"/>  </li>
			</ul>
		</section>
		
		<section id = "personal_info_modify_form">
			<h2>Formulaire pour éditer mes informations personnelles</h2>
			
			<h5>Modifier l'email ou le numéro de téléphone</h5>
			
			<p>Remplissez le ou les champs que vous souhaitez modifier.</p>
			
			<form method="post">
				<fieldset>
					<div class="form-group row">      
						<label for="email" class="col-3">Adresse email</label>
		            	<input type="email" name="email" maxlength="60" class="form-control col-2">
		            </div>
		            
		            <div class="form-group row">      
		           		<label for="phoneNumber" class="col-3">Numéro de Téléphone</label>
		            	<input type="text" name="phoneNumber" maxlength="60" class="form-control col-2">
		            </div>   
					
		             <button name="changeType" type="submit" value="personalInfo" class="btn btn-primary" >Enregistrer</button>
		            
		            <p class="error">${messagePersonalInfo}</p>
				</fieldset>
			</form>
			
			<h5>Modifier le mot de passe</h5>
			
			<p>Pour changer votre mot de passe, il est nécessaire de remplir les trois champs</p>
			
			<form method="post">
				<fieldset>
				
					<div class="form-group row">
		            	<label for="oldPassword" class="col-3">Entrez votre ancien mot de passe <span class="required">*</span></label>
		            	<input type="password" name="oldPassword" maxlength="20" class="form-control col-3">
		            </div>
		            
		            <div class="form-group row">
		           		<label for="newPassword" class="col-3">Entrez votre nouveau mot de passe <span class="required">*</span></label>
		            	<input type="password" name="newPassword" maxlength="20" class="form-control col-3">
		            </div>
		            
		            <div class="form-group row">
		            	<label for="checkNewPassword" class="col-3">Confirmez votre nouveau mot de passe <span class="required">*</span></label>
		            	<input type="password" name="newPasswordConfirm" maxlength="20" class="form-control col-3">
		            </div>
		
		            <button name="changeType" type="submit" value="Password" class="btn btn-primary" >Enregistrer</button>
		            
	                <p class="error">${messagePassword}</p>
	                
	            </fieldset>
			</form>
		</section>
	</div>
	<script src="/assets/jquery/jquery.min.js"></script>
    <script src="/assets/tether/tether.min.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
    
</body>
</html>