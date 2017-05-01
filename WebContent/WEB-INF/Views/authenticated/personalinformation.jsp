<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Informations Personnelles</title>
</head>
<body>

	<h1>Mes informations personnelles</h1>
	
	<c:import url="/WEB-INF/Views/authenticated/menuauth.jsp" />
	
	<section id = "personal_info">
		<h2>Détail de mes informations personnelles</h2>
		<ul>
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
		<p>Remplissez le ou les champs que vous souhaitez modifier.</p>
		<p>Pour changer votre mot de passe, il est nécessaire de remplir les trois champs</p>
		<form method="post">
			<fieldset>
				<label for="email">Adresse email</label>
	            <input type="email" name="email" size="20" maxlength="60" />
	            <br />
	            
	            <label for="phoneNumber">Numéro de Téléphone</label>
	            <input type="text" name="phoneNumber" size="20" maxlength="60" />
	            <br />
	            
	            <button name="changeType" type="submit" value="personalInfo">Enregistrer</button>
	            
	            <p>${messagePersonalInfo}</p>
			</fieldset>
		</form>
		<form method="post">
			<fieldset>
				<%--TODO: logique pour vérifier que l'ancien password correspond, puis que newPassword et checkNewPassword correspondent --%>
	            <label for="oldPassword">Entrez votre ancien mot de passe <span class="required">*</span></label>
	            <input type="password" name="oldPassword" size="20" maxlength="20" />
	            <br />
	            
	           	<label for="newPassword">Entrez votre nouveau mot de passe <span class="required">*</span></label>
	            <input type="password" name="newPassword" size="20" maxlength="20" />
	            <br />
	            
	            <label for="checkNewPassword">Confirmez votre nouveau mot de passe <span class="required">*</span></label>
	            <input type="password" name="newPasswordConfirm" size="20" maxlength="20" />
	            <br />
	
	            <button name="changeType" type="submit" value="Password">Enregistrer</button>
	            <br />
	            
                <p>${messagePassword}</p>
                
            </fieldset>
		</form>
	</section>
	
</body>
</html>