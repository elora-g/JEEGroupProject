<%@ page contentType ="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8" />
        <title>Se connecter</title>
   	</head>
   	<body>
   		 <form method="post" action="">
            <fieldset>
                <legend>Connexion</legend>
                <p>Vous pouvez vous connecter via ce formulaire.</p>
                
                
                <label for="email">Adresse email</label>
                <input type="email" id="email" name="email" size="20" maxlength="60" />
                <%--<span class="error">${form.errors['email']}</span> --%>
                <br />
                
                <label for="clientId">Client Id</label>
                <input type="text" id="clientId" name="clientId" size="20" maxlength="60" />
                <%--<span class="error">${form.errors['email']}</span> --%>
                <br />

                <label for="password">Mot de passe <span class="required">*</span></label>
                <input type="password" id="password" name="password" size="20" maxlength="20" />
                <%-- <span class="error">${form.errors['password']}</span> --%>
                <br />
                
                <br />
                <label for="rememberMe">Se souvenir de moi</label>
                <input type="checkbox" id="rememberMe" name="rememberMe" />
                <br />

                <input type="submit" value="Connexion" class="noLabel" />
                <br />
                
                
                 <p>${message}</p>
                
            </fieldset>
        </form>
   	</body>
</html>