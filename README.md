# SMTP Client for prank mail

## Description

Ce projet permet d'effectué des envois de mail forgé à des adresses arbitraires.
Il est facilement configurable par un fichier de configuration.
Il vous proposera de choisir le message a envoyé à un groupe donner de manière intéractive.
La gestion d'authentification est intéractive et demandé si le serveur gère la fonctionalité de login.

## Utilisation avec MockMock: Mock SMTP serveur

## Usage

Les deux configurations suivantes permet de définir le comportement et le serveur cible d'envoi.

**config.json** permet de spécifié le serveur cible et le domain d'annonce vers le serveur.
Voici un exemple simple permettant de voir la structure nécessaire.
```json

```

**prank.json** permet de définir une liste de groupe d'avoir avec un envoyeur et au moins deux victimes ainsi qu'une liste de mail qui seront proposé lors de l'execution.
Voici un exemple simple permettant de voir la structure nécessaire pour les informations.
```json

```

## Implementation

La class Client est la classe principal s'occupant de guérer la connection avec le serveur et d'envoyer les emails à l'aide de la class SMTPMessages qui fourni un ensemble de fonction permettant de générer les messages smtp.

La class IOUser contient l'ensemble des méthodes pour dialoguer avec l'utilisateur, permettant de récuperer les informations nécessaires durant le déroulement.

Les models Credential, Mail, MailHeader et MailContent sont utilisé pour stocké les valeurs avant l'envoi.
MailHeader contient les différents informations envoyés avant les données (envoyeur, receveur), le MailContent contient les données du mail (subject, message).
Mail est une instance contenant un MailHeader et un MailContent pour modéliser un mail complet.
Credential stock les crédentials de l'utilisateur en base64.

Le ConfigLoader s'occupe de récupérer et mettre à disposition l'ensemble des informations présentent dans la configuartion.



