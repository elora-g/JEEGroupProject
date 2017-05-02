# JEEGroupProject

Projet de création d'une plateforme de gestion clients et interface clients pour une banque

Projet héberger par Heroku [Home Page](https://societeagricoleproject.herokuapp.com) merci @Gruck pour l'aide au déploiement! 

ATTENTION : IL Y A UNE LEGERE MODIFICATION DU SERVER TOMCAT8.5 A EFFECTUER
1. Double cliquer sur l'icône du serveur dans l'onglet serveur (eclipse)
2. Onglet "Modules" du document ouvert
3. Sélectionner la ligne correspondant au projet
4. Cliquer sur Edit et retirer la valeur de path de la fenêtre

ATTENTION, pour tester l'implémentation de Bcrypt pour les passwords, ils ont été updatés dans la database fournie
Merci donc d'utiliser le fichier de requête sql fourni à la racine (et pas celui envoyé initialement par Clément)

Pour mémoire et test rapide du projet voici les tuples email - external_id - password - hashed password BCrypt12 :

lskai@lskai.sw - 63758986 - JeSuisMonPere - $2a$12$b5etdnstrUJznYfKBw1wqeEfIi4RM.Ojx0CC42c5PsJu44mzvBmyi
logono@deathstar.com - 45975853 - LORGONO - $2a$12$eEJh5BsZszHs3SzR9wBAIe7AD5PB05wZJVeppq2CYS1xKZd5zLSkW
daenerys@dothraki.got - 90765765 - MamanDragon - $2a$12$unSqyIZQEcMr4jCUZ399jevmLER5hAtjLM6bwJdCPdMa/qzAu1BB.
estark@7royaumes.co - 23645766 - TeteTranchee - $2a$12$Gszw1xrtqMWsUsuxjVN9K.Vbkz.slrKvOgdp0AP9gNd45KMKV68YC
jsnow@gardedenuit.info - 53469557 - WinterIsComming - $2a$12$ZEqtG5R7og2DAF43sGKeSOYeSs2jz6qPUD81IOcVYJPeLDSwFZGyq
bankable@picsou.acme - 05677764 - arrrrrrrrgent - $2a$12$XPM0jyYGvBU98.NmoouKaeJEwkXZsuMeGiltb14CLYygiwPB/U6OS
jordan@evilbank.biz - 84628456 - MoneyLovr - $2a$12$K8hcq2MHxR8ZgGrtliD1kuZF0875ygBUhmlejnI2SiV1CVVS5wuY2
