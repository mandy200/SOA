# [Readme] ms-membership



Ce fichier a pour but de vous guider dans le déploiement et l'utilisation des API.

Afin de suivre ce guide de manière optimale, il conviendrait de commencer par la lecture de ce fichier puis de suivre le fichier se trouvant dans  `ms-basket`.



### 1) Docker

```shell
docker login

cd ms-cours3 
mvn clean install -Dmaven.test.skip=true
docker build -t ms-membership .

cd ..
docker-compose up -d

cd ms-cours3
docker build -t mandy200/ms-membership:v1.0 .
docker push mandy200/ms-membership:v1.0

```





### 2) Test API (ms-membership)

```shell
#Création d'un user
curl 'http://localhost:8070/v1/esipe/users' \
-H 'Content-Type: application/json' \
-d '{
	"email" : "toto.user@e-shop.fr",
	"firstname" : "toto",
	"lastname" : "user",
	"age" : 30,
	"password" : "toto",
	"authorities" : [""]
}'


#Authentifier user
curl 'http://localhost:8070/v1/esipe/users/authenticate' \
-H 'Content-Type: application/json' \
-d '{
	"login" : "toto.user@e-shop.fr",
	"password" : "toto"
}'


#Création d'un admin
curl 'http://localhost:8070/v1/esipe/users' \
-H 'Content-Type:application/json' \
-d '{
	"email" : "admin@e-shop.fr",
	"firstname" : "admin",
	"lastname" : "admin",
	"age" : 30,
	"password" : "admin",
	"authorities" : [ "admin"]
}'


# Authentification admin
curl 'http://localhost:8070/v1/esipe/users/authenticate' \
-H 'Content-Type: application/json' \
-d'{
	"login" : "admin@e-shop.fr",
	"password" : "admin"
}'


#Lister user
curl --location --request GET 'http://localhost:8070/v1/esipe/users' \
--header 'Content-Type: application/json'

```





### 3) GIT

Initialisation de GIT et création d'une branche develop:

```shell
git init
git checkout -b develop
```



Après exécution de `vim .gitignore` on insère les lignes suivantes au fichier: 

```powershell
**.idea
**.mvn
**/target
**/mvnw
**/mvnw.cmd
**.DS_Store
**.class
**.iml
```



Après enregistrer et quitter le fichier, on peut alors exécuter les commandes suivantes:

```shell
git add .gitignore
git commit -m "[Init] Add .gitignore"

git add docker-compose.yml
git commit -m "[Docker] Add docker-compose"

git add ms-cours3
git commit -m "[ms-membership] Add app"

git add README.md
git commit -m "[ms-membership] Add readme"

git remote add origin https://github.com/mandy200/SOA.git
git push -u origin develop
```





### 4) Schéma d'architecture

![ComponentDiagram](https://tva1.sinaimg.cn/large/00831rSTgy1gd91rktrfxj30nn09ijrn.jpg)
