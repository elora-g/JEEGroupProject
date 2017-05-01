<%@ page contentType ="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    	<meta name="description" content="Connexion Page">
    	<meta name="author" content="Elora Guyader">
	  	<link href="/JEEGroupProject/assets/css/bootstrap.min.css" rel="stylesheet">
		<link href="/JEEGroupProject/assets/css/generalstyle.css" rel="stylesheet" type="text/css">
        <title>Se connecter</title>
   	</head>
   	<body>
   		<div class ="container3">
	   		 <form method="post" action="">
	            <fieldset>
	                <legend>Connexion</legend>
	                <p>Vous pouvez vous connecter via ce formulaire.</p>
	                
	                <div class="form-group">
	              	  <label for="email">Adresse email</label>
	               	  <input type="email" class="form-control" id="email" name="email" size="20" maxlength="60" />
	                </div>
	                
	                <div class="form-group">
	                <label for="externalId">Client Id</label>
	                <input type="text" class="form-control" id="externalId" name="externalId" size="20" maxlength="60" />
	                </div>
					
					<div class="form-group">
	                <label for="password">Mot de passe <span class="required">*</span></label>
	                <input type="password" class="form-control" id="password" name="password" size="20" maxlength="20" />
	                </div>
	
	                <input type="submit" class="btn btn-primary" value="Connexion" class="noLabel" />
	                
	                 <p class="error">${message}</p>
	                
	            </fieldset>
	        </form>
        </div>
        <script src="/JEEGroupProject/assets/jquery/jquery.min.js"></script>
   		<script src="/JEEGroupProject/assets/tether/tether.min.js"></script>
    	<script src="/JEEGroupProject/assets/js/bootstrap.min.js"></script>
    	
   	</body>
</html>