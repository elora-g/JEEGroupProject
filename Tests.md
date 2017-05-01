# Documentation du process de testing

## Création de comptes utilisateurs

### Test unitaire de création de compte utilisateur
Dans notre cas, il s'agit de tester notre méthode Person.persist() qui permet de sauvegarder ou de créer une personne.

#### Tester que l'insertion ajoute bien un élément dans la table
1. On compte combien on a d'éléments dans la table person
2. On appelle persist sur un nouvelle instance de person
3. On compte combien d'éléments sont dans la table
4. Le test passe si il y a exactement un élément supplémentaire dans la table

#### Tester que les valeurs de l'utilisateur inséré sont correctes
1. On instancie une nouvelle instance P1 avec des valeurs d'exemple
2. On appelle persist
3. On récupère l'id généré
4. On appelle Person.getPersonById() et on stocke ça dans une nouvelle instance P2
5. Le test passe si la comparaison des champs deux à deux entre P1 et P2 correspond

### Test du point de vue utilisateur
1. On se connecte en tant qu'advisor
2. On va sur la page Gestion de Mes Clients
3. On remplit le formulaire et on le soumet
4. On vérifie la présence du client dans la liste de nos clients (au dessus dans la page)
5. On se déconnecte
6. On essaye de se connecter avec les informations de connection du client créé
7. Le test passe si les étapes 3,4 et 6 se passent sans difficulté

## Echange de messages

### Test unitaire de création d'un message
Dans notre cas il s'agit de tester notre méthode Message.persist() qui permet de sauvegarder ou de créer un message

#### Tester que l'insertion ajoute bien un élément dans la table
1. On compte combien on a d'éléments dans la table messages
2. On appelle persist sur un nouvelle instance de Message
3. On compte combien d'éléments sont dans la table
4. Le test passe si il y a exactement un élément supplémentaire dans la table

#### Tester que les valeurs de l'utilisateur inséré sont correctes
1. On instancie une nouvelle instance M1 avec des valeurs d'exemple
2. On appelle persist
3. On récupère l'id généré
4. On effectue une requête SQL pour récupérer une nouvelle instance M2 depuis la base de donnée avec cet id
5. Le test passe si la comparaison des champs deux à deux entre M1 et M2 correspond

### Test du point de vue utilisateur
1. On se connecte en tant qu'advisor
2. On va sur la page Gestion de Mes Clients ==> On sélectionne un client ==> on va sur la messagerie avec ce client
3. On remplit le formulaire et on le soumet
4. On vérifie la présence du message dans l'historique des messages (en dessous dans la page)
5. On se déconnecte
6. On se connecte avec les informations de connection du client avec qui on a échangé
7. On va dans la page messages et on vérifie la présence du message
8. On fait la même chose mais en partant du client
9. Le test passe si les étapes 3,4 7 et 8 se passent sans difficulté
