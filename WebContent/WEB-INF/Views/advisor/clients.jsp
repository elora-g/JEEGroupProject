<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste de mes clients</title>
</head>

<body>
	<h1>Liste de mes clients</h1>
	<c:import url="/WEB-INF/Views/advisor/menuadvisor.jsp" />
	<section class="clientList"> 
		<table>
			<thead>
				<tr>
					<td>Identifiant</td>
					<td>Nom</td>
					<td>Prénom</td>
					<td>Voir ce client</td>
				</tr>
			</thead>
			<tbody>
				<%-- <c:forEach client du conseiller --%>
					<tr>
						<td><%-- <c:out value= ${externalId}/> --%></td>
						<td><%-- <c:out value= ${Lastname}> --%></td>
						<td><%-- <c:out value= ${Firstname}/> --%></td>
						<td><%--<a href="/JEEGroupProject/advisor/client/id">Voir ce client</a> --%> <%--TODO: redirect on the right client page --%></td>
					</tr>
				<%--</c:forEach> --%>
			</tbody>
		</table>
	</section>
		
	<section class="createClientForm">
	<h2>Créer un nouveau client</h2>
   		<form method="post" action="">
            <fieldset>
                            
                <label for="lastName">Nom <span class="required">*</span></label>
                <input type="text" name="lastName" size="20" maxlength="60" />
                <br />
                
                <label for="firstName">Prénom <span class="required">*</span></label>
                <input type="text" name="firstName" size="20" maxlength="60" />
                <br />
                
                <label for="email">Email <span class="required">*</span></label>
                <input type="email" name="email" size="20" maxlength="60" />
                <br />

                <label for="password">Entrez un mot de passe pour votre client <span class="required">*</span></label> <%--TODO générer le mot de passe automatiquement --%>
                <input type="password" name="password" size="20" maxlength="20" />
                <br />
                
                <label for="phoneNumber">Téléphone <span class="required">*</span></label>
                <input type="text" name="phoneNumber" size="20" maxlength="60" />
                <br />
                
                <input type="checkbox" id="cbox" name="isAdvisorCbox" value="isAdvisor"><label for="isAdvisorCbox">Cochez cette case si le client est un employé de la banque</label>

                <input type="submit" value="createClient" class="noLabel" />
                <br />
                
                <p>${message}</p>
                
            </fieldset>
        </form>
	</section>
</body>
</html>