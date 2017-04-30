<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bienvenue</title>
</head>
<body>

	
	<h1>Les informations personnelles du client n° <%--<c:out value=${externalId}></c:out> --%></h1>
	
	<c:import url="/WEB-INF/Views/advisor/menuadvisor.jsp" />
	
	<section id = "personal_info_by_advisor">
		<h2>Détail des informations personnelles</h2>
		<ul>
			<li>Nom : <%-- <c:out value= ${personLastName}/> --%></li>
			<li>Prénom : <%-- <c:out value= ${personFirstName}/> --%></li>
			<li>Identifiant :<%-- <c:out value= ${personExternalId}/> --%> </li>
			<li>Date de naissance : <%-- <c:out value= ${personDob}/> --%></li>
			<li>Email : <%-- <c:out value= ${personEmail}/> --%> </li>
			<li>Téléphone : <%-- <c:out value= ${personPhoneNumber}/> --%> </li>
		</ul>
	</section>
	
	<section id = "personal_info_modify_form_by_advisor">
		<h2>Formulaire pour éditer les informations personnelles du client</h2>
		<p>Vous ne pouvez pas modifier l'état civil (nom, prénom et date de naissance) du client sans pièce justificative et autorisation du responsable d'agence.</p>
		<form>
			<fieldset>
				<label for="email">Adresse email</label>
	            <input type="email" name="email" size="20" maxlength="60" />
	            <br />
	            
	            <label for="phoneNumber">Numéro de Téléphone</label>
	            <input type="text" name="phoneNumber" size="20" maxlength="60" />
	            <br />
				
	            <input type="submit" value="changeInfo" class="noLabel" />
	            <br />
                
            </fieldset>
		</form>
	</section>
</body>
</html>