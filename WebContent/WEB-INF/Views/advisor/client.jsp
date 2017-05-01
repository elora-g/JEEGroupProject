<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Détail de mon client</title>
</head>

<body>

	

	<h1>Comptes de mon client n°<c:out value= "${currentClient.externalId}"/></h1>
	
		<c:import url="/WEB-INF/Views/advisor/menuadvisor.jsp" />
		
		<p><a href="/JEEGroupProject/advisor/clientinformation?id=<c:out value="${currentClient.externalId}"/>">Voir les informations personnelles du client</a></p>
		<p><a href="/JEEGroupProject/advisor/clientmessages?id=<c:out value="${currentClient.externalId}"/>">Messagerie avec le client</a></p>
		
		<section class="clientAccounts"> 
			<table>
				<thead>
					<tr>
						<td>Numéro de Compte</td>
						<td>Type</td>
						<td>Solde</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="account" items="${currentClient.accounts}">
						<tr>
							<td><c:out value= "${account.id}"/></td>
							<td><c:out value= "${account.type}"/></td>
							<td><c:out value= "${account.balance}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</section>
		
	
		<section class="createAccountForm">
			<c:choose>
				<c:when test ="${fn:length(currentClient.accounts) < 5}" > 
					<h2>Créer un nouveau compte pour ce client</h2>
				   		<form method="post" action="">
				            <fieldset>
				                            
				                <label for="accountType">Type de compte <span class="required">*</span></label>
				                <input type="radio" name="accountType" value="CHECKING">Chèque<br />
	  							<input type="radio" name="accountType" value="SAVINGS">Epargne<br />
	 							<input type="radio" name="accountType" value="LIFE_INSURRANCE">Assurance Vie<br />
				                
				                <label for="balance">Solde de départ <span class="required">*</span></label>
				                <input type="text" name="balance" size="20" maxlength="60" />
				                <br />
				                
				                <c:if test ="${fn:length(currentClient.accounts) >= 1 }">
				                	<label for="isDefault">Le compte est le compte principal de votre client <span class="required">*</span></label>
				                	<input type="radio" name="isDefault" value="0">Non<br />
				                	<input type="radio" name="isDefault" value="1">Oui<br /> 	
								</c:if>
				                <input type="submit" value="créer le compte" class="noLabel" />
				                <br />
				                
				                <p>${message}</p>
				                
				            </fieldset>
				        </form>
			    </c:when> 
			    <c:otherwise>
			    	<p>Vous ne pouvez pas créer de nouveau compte pour ce client, il a déjà 5 comptes différents</p>
			    </c:otherwise>
		   	</c:choose>
		</section>

		
	
		
</body>
</html>