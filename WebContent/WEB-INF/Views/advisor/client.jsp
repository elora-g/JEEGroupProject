<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Détail de mon client</title>
</head>

<body>

	

	<h1>Comptes de mon client n°<%-- <c:out value= ${externalId}/> --%> </h1>
	
		<c:import url="/WEB-INF/Views/advisor/menuadvisor.jsp" />
		
		<p><%--<a href="/JEEGroupProject/advisor/clientinformation/id">Voir les informations personnelles du client</a> --%> <%--TODO: redirect on the right client page --%></p>
		<p><%--<a href="/JEEGroupProject/advisor/clientmessages/id">Messagerie avec le client</a> --%> <%--TODO: redirect on the right client page --%></p>
		
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
					<%-- <c:forEach accounts du client --%>
						<tr>
							<td><%-- <c:out value= ${id}/> --%></td>
							<td><%-- <c:out value= ${type}> --%></td>
							<td><%-- <c:out value= ${balance}/> --%></td>
						</tr>
					<%--</c:forEach> --%>
				</tbody>
			</table>
		</section>
		
	
		<section class="createAccountForm">
			<%--<c:choose> --%>
				<%-- <c:when test = nombre de comptes <=4 > --%>
					<h2>Créer un nouveau compte pour ce client</h2>
				   		<form method="post" action="">
				            <fieldset>
				                            
				                <label for="accountType">Type de compte <span class="required">*</span></label>
				                <input type="radio" name="accountType" value="Checking">Chèque<br />
	  							<input type="radio" name="accountType" value="Savings">Epargne<br />
	 							<input type="radio" name="accountType" value="LifeInsurance">Assurance Vie<br />
				                
				                <label for="balance">Solde de départ</label>
				                <input type="text" name="balance" size="20" maxlength="60" />
				                <br />
				                
				                <%-- <c:if test = nombre de comptes >1 > --%>
				                	<input type="checkbox" id="cbox2" name="isDefaultCbox" value="isDefault"><label for="isDefaultCbox">Cochez cette case si ce compte est le compte principal du client</label>
								<%-- </c:if> --%>
				                <input type="submit" value="createClient" class="noLabel" />
				                <br />
				                
				                <p>${message}</p>
				                
				            </fieldset>
				        </form>
			    <%--</c:when> --%>
			    <%--<c:otherwise>--%>
			    	<p>Vous ne pouvez pas créer de nouveau compte pour ce client, il a déjà 5 comptes différents</p>
			    <%--</c:otherwise>--%>
		   	<%--</c:choose> --%>
		</section>

		
	
		
</body>
</html>