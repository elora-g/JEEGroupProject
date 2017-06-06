<%@ page contentType ="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    	<meta name="description" content="Connexion Page">
    	<meta name="author" content="Elora Guyader">
	  	<link href="/assets/css/bootstrap.min.css" rel="stylesheet">
		<link href="/assets/css/generalstyle.css" rel="stylesheet" type="text/css">
        <title>Se connecter</title>
   	</head>
   	<body>
   		<nav class="navbar fixed-top navbar-toggleable-md navbar-inverse bg-inverse">
			<div class="container">
				<a class="navbar-brand" href="/login">Société Agricole - Bienvenue sur la page de connexion</a>
			</div>
		</nav>
   		<div class ="container3">
	   		 <form method="post" action="">
	            <fieldset>
	                <legend>Connexion</legend>
	                <p>Vous pouvez vous connecter via ce formulaire.</p>
	                
	                <div class="form-group">
	              	  <label for="email">Adresse email</label>
	               	  <input type="email" class="form-control" id="email" name="email" maxlength="60">
	                </div>
	                
	                <div class="form-group">
	                <label for="externalId">Client Id</label>
	                <input type="text" class="form-control" id="externalId" name="externalId" maxlength="60">
	                </div>
					
					<div class="form-group">
	                <label for="password">Mot de passe <span class="required">*</span></label>
	                <input type="password" class="form-control" id="password" name="password" maxlength="20">
	                </div>
	
	                <input type="submit" class="btn btn-primary" value="Connexion">
	                
	                 <p class="error">${message}</p>
	                
	            </fieldset>
	        </form>
	      </div>
	      <div class="container4">
	        <h2>Données de test fournies pour l'exercice</h2>
	        <table class="table table-stripped">
				<thead>
					<tr>
						<th>E-mail</th>
						<th>Id</th>
						<th>Mot de passe</th>
						<th>Conseiller?</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>lskai@lskai.sw</td>
						<td>63758986</td>
						<td>JeSuisMonPere</td>
						<td>non</td>
					</tr>
					<tr>
						<td>logono@deathstar.com</td>
						<td>45975853</td>
						<td>LORGONO</td>
						<td>non</td>
					</tr>
					<tr>
						<td>daenerys@dothraki.got</td>
						<td>90765765</td>
						<td>MamanDragon</td>
						<td>non</td>
					</tr>
					<tr>
						<td>estark@7royaumes.co</td>
						<td>23645766</td>
						<td>TeteTranchee</td>
						<td>non</td>
					</tr>
					<tr>
						<td>jsnow@gardedenuit.info</td>
						<td>53469557</td>
						<td>WinterIsComming</td>
						<td>non</td>
					</tr>
					<tr>
						<td>bankable@picsou.acme</td>
						<td>05677764</td>
						<td>arrrrrrrrgent</td>
						<td>oui</td>
					</tr>
					<tr>
						<td>jordan@evilbank.biz</td>
						<td>84628456</td>
						<td>MoneyLovr</td>
						<td>oui</td>
					</tr>
				</tbody>
			</table>
        </div>
        <script src="/assets/jquery/jquery.min.js"></script>
   		<script src="/assets/tether/tether.min.js"></script>
    	<script src="/assets/js/bootstrap.min.js"></script>
    	
   	</body>
</html>