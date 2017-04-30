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
			<li>Nom : <%-- <c:out value= ${personLastName}/> --%></li>
			<li>Prénom : <%-- <c:out value= ${personFirstName}/> --%></li>
			<li>Identifiant :<%-- <c:out value= ${personExternalId}/> --%> </li>
			<li>Date de naissance : <%-- <c:out value= ${personDob}/> --%></li>
			<li>Email : <%-- <c:out value= ${personEmail}/> --%> </li>
			<li>Téléphone : <%-- <c:out value= ${personPhoneNumber}/> --%> </li>
		</ul>
	</section>
	
	<section id = "personal_info_modify_form">
		<h2>Formulaire pour éditer mes informations personnelles</h2>
		<p>Remplissez le ou les champs que vous souhaitez modifier.</p>
		<p>Pour changer votre mot de passe, il est nécessaire de remplir les trois champs</p>
		<form>
			<fieldset>
				<label for="email">Adresse email</label>
	            <input type="email" name="email" size="20" maxlength="60" />
	            <br />
	            
	            <label for="phoneNumber">Numéro de Téléphone</label>
	            <input type="text" name="phoneNumber" size="20" maxlength="60" />
	            <br />
				
				<%--TODO: logique pour vérifier que l'ancien password correspond, puis que newPassword et checkNewPassword correspondent --%>
	            <label for="oldPassword">Entrez votre ancien mot de passe <span class="required">*</span></label>
	            <input type="password" name="oldPassword" size="20" maxlength="20" />
	            <br />
	            
	           	<label for="newPassword">Entrez votre nouveau mot de passe <span class="required">*</span></label>
	            <input type="password" name="newPassword" size="20" maxlength="20" />
	            <br />
	            
	            <label for="checkNewPassword">Confirmez votre nouveau mot de passe <span class="required">*</span></label>
	            <input type="password" name="checkNewPassword" size="20" maxlength="20" />
	            <br />
	
	            <input type="submit" value="changeInfo" class="noLabel" />
	            <br />
	            
                <p>${message}</p>
                
            </fieldset>
		</form>
	</section>
	
</body>
</html>