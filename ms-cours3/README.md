# [Readme] ms-basket



Ce fichier a pour but de vous guider dans le déploiement et l'utilisation des API.

Afin de suivre ce guide de manière optimale, il conviendrait de commencer par la lecture du fichier se trouvant dans `ms-cours3`  puis de revenir sur celui-ci.



###1) Docker

> On considèrera ici que la commande `docker login`  a été précédemment executée.

```shell
cd ms-basket
mvn clean install -Dmaven.test.skip=true
docker build -t ms-basket .

cd ..
docker-compose restart

cd ms-basket
docker build -t mandy200/ms-basket:v1.0 .
docker push mandy200/ms-basket:v1.0
```





###2) Test API (ms-basket)

> Les différents Tokens et identifiants n'ont pas été précisés car ils changent à chaque exécution.



```shell
############################### Création d'un user ###########################
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



############################ Authentifier user ###############################
curl 'http://localhost:8070/v1/esipe/users/authenticate' \
-H 'Content-Type: application/json' \
-d '{
	"login" : "toto.user@e-shop.fr",
	"password" : "toto"
}'



############################ Créer un produit sans token ################################
# Cette opération doit retourner une erreur
curl 'http://localhost:8060/v1/esipe/products' \
-H 'Content-Type: application/json' \
-d '{
    "gtin": "0000000000",
    "label": "test_product",
    "unitPrice": 15,
    "vat": 20
}'



########################## User ajoute produit à son panier ###############################
# Cette opération doit retourner une erreur (aucun produit n'existe)
curl 'http://localhost:8060/v1/esipe/baskets' \
-H 'Content-Type: application/json' \
-H 'Authorization: Bearer
# De-commenter et insérer Token de l'utilisateur : [TOKEN USER]' \
-d ' {
	"user" : "toto.user@e-shop.fr",
	"totalAmount" : 1,
	"status" : "CREATED",
	"items" : [
		{
			"gtin" : "2ba5d5c8-5ca4-4a33-aece-21cff75ab487",
			"label" : "product01",
			"unitPrice" : 10,
			"vat" : 20,
			"quantity" : 2
		}		
	]
}'



######################## Créer produit avec le token user ##############################
# Cette opération doit retourner une erreur (seul un admin peut créer un produit)
curl 'http://localhost:8060/v1/esipe/products' \
-H 'Content-Type: application/json' \
-H 'Authorization: Bearer 
# De-commenter et insérer Token de l'utilisateur : [TOKEN USER]' \
-d '{
    "gtin": "0000000001",
    "label": "product01",
    "unitPrice": 10,
    "vat": 20
}'



################################# Créer un admin ##################################
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



############################### Authentifier l'admin ####################################
curl 'http://localhost:8070/v1/esipe/users/authenticate' \
-H 'Content-Type: application/json' \
-d '{
	"login" : "admin@e-shop.fr",
	"password" : "admin"
}'



#######################################################################################
# Créer produit avec le Token admin
curl 'http://localhost:8060/v1/esipe/products' \
-H 'Content-Type: application/json' \
-H 'Authorization: Bearer 
# De-commenter et insérer Token de l'admin : [TOKEN ADMIN]' \
-d '{
    "gtin": "0000000001",
    "label": "product01",
    "unitPrice": 10,
    "vat": 20
}'


#######################################################################################
# Lister les produits
curl --location --request GET 'http://localhost:8060/v1/esipe/products' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer 
# De-commenter et insérer un Token admin ou utilisateur : [TOKEN ADMIN or USER]'



#######################################################################################
# Créer un panier avec l'user
curl 'http://localhost:8060/v1/esipe/baskets' \
-H'Content-Type: application/json' \
-H 'Authorization: Bearer 
# De-commenter et insérer Token de l'utilisateur : [TOKEN USER]' \
-d ' {
	"user" : "toto.user@e-shop.fr",
	"totalAmount" : 1,
	"status" : "CREATED",
	"items" : [
		{
			"gtin" : "[Remplacer par l'ID d'un Produit]",
			"label" : "product01",
			"unitPrice" : 10,
			"vat" : 20,
			"quantity" : 2
		}		
	]
}'

```



### 3) GIT

> Le GIT étant déjà été initialisé, il est ici uniquement question de commit et push `ms-basket` et le readme.

```shell
git checkout -b develop

git add ms-basket
git commit -m "[ms-basket] Add app"

git add README.md
git commit -m "[ms-basket] Add readme"

git push -u origin develop
```



### 4) Schéma d'architecture

![ComponentDiagram](https://tva1.sinaimg.cn/large/00831rSTgy1gd91rktrfxj30nn09ijrn.jpg)

