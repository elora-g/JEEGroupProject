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
	<title>Détail de mon client</title>
</head>

<body>

	<c:import url="/WEB-INF/Views/advisor/menuadvisor.jsp" />
	
	<div class="container2">
	
		<h1>Comptes de mon client n°<c:out value= "${currentClient.externalId}"/> - <c:out value= "${currentClient.firstname}"/> <c:out value= "${currentClient.lastname}"/></h1>	
		
		<p><a href="/advisor/clientinformation?id=<c:out value="${currentClient.externalId}"/>">Voir les informations personnelles du client</a></p>
		<p><a href="/advisor/clientmessages?id=<c:out value="${currentClient.externalId}"/>">Messagerie avec le client</a></p>
		
		<section class="clientAccounts"> 
			<table class="table table-striped">
				<thead>
					<tr>
						<th scope="row" class="col-lg-2">Numéro de Compte</th>
						<th class="col-lg-5">Type</th>
						<th class="col-lg-5">Solde</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="account" items="${currentClient.accounts}">
						<tr>
							<th scope="row"><c:out value= "${account.id}"/></th>
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
				            	<div class="form-group row">           
					                <label for="accountType" class="col-2">Compte Chèque
					                <input type="radio" name="accountType" value="CHECKING">
					                </label>
					                <label for="accountType" class="col-2">Compte épargne 
		  							<input type="radio" name="accountType" value="SAVINGS">
		  							</label>
		  							<label for="accountType" class="col-2">Assurance vie  
		 							<input type="radio" name="accountType" value="LIFE_INSURRANCE">
		 							</label>
	 							</div>
				                
				                <div class="form-group row">
					                <label for="balance" class="col-2">Solde de départ <span class="required">*</span></label>
					                <input type="text" class="form-control col-4" name="balance" maxlength="60">
				                </div>
				                
				                <c:if test ="${fn:length(currentClient.accounts) >= 1 }">
				                	<div class="form-group row">
					                	<label for="isDefault" class="col-4">
  										<span class="custom-control-description"> Le compte est le compte principal de votre client</span>
					                	<input type="radio" name="isDefault" value="1">
					                	</label>
					                	<label for="isDefault" class="col-4">
  										<span class="custom-control-description">Le compte n'est pas le compte principal de votre client</span>
					                	<input type="radio" name="isDefault" value="0"> 
					                	</label>
				                	</div>	
								</c:if>
				                <input type="submit" value="créer le compte" class="btn btn-primary">

				                
				                <p>${message}</p>
				                
				            </fieldset>
				        </form>
			    </c:when> 
			    <c:otherwise>
			    	<p>Vous ne pouvez pas créer de nouveau compte pour ce client, il a déjà 5 comptes différents</p>
			    </c:otherwise>
		   	</c:choose>
		</section>
	</div>
	<script src="/assets/jquery/jquery.min.js"></script>
    <script src="/assets/tether/tether.min.js"></script>
    <script src="/assets/js/bootstrap.min.js"></script>
	
		
</body>
</html>