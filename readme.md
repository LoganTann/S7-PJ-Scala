# Scalapompe

L'API web pour trouver les stations d'essences les moins chères près de chez vous !

Utilise les données ouvertes du ministère de l'économie (https://data.economie.gouv.fr/explore/dataset/prix-des-carburants-en-france-flux-instantane-v2)

## Démarrage

* Installez le SDK scala sur le site officiel.
* Exécutez `sbt run` pour démarrer le projet. Cela va ouvrir un serveur.

## Usage

Pour le moment, seul un seul endpoint est activé: https://localhost:8080/search

Il faut mentionner les paramètres suivants : 
* `lat=<double>` et `lon=<double>`, qui sont la latitude et la longitude de l'emplacement actuel de recherche. Si l'un deux est invalide, alors la position sera celle de l'EFREI.
* `distance=<int>` distance de recherche en km. Les stations au delà du radius seront retirées des résultats.
* `[offline=true]` récupère les données depuis le fichier json au lieu de l'API temps réel.

exemple : 
* https://localhost:8080/search?lat=48.7886889&lon=2.358667&distance=100

## Tests

Les tests sont situés dans le dossier [src/test/scala/fr/scalapompe](src/test/scala/fr/scalapompe). Pour démarrer les tests, exécuter `sbt test`.

## Informations techniques

Tout le code est dans le package main.scala.fr.scalapompe. L'entrypoint est Main.scala

* Le dossier `controllers` contient les classes de contrôleur. Grâce au trait ControllerTrait, il est possible de faire charger très facilement de nouveaux contrôleurs, même si c'est un peu trop overengineered pour ce projet.
* Le dossier `models` contient les entités. On retrouvera dans StationEntity les entités d'entrées et dans QueryResponse les entités de sortie.
* Le dossier `repositories` contient les classes d'accès aux données
* Le dossier `service` contient les classes de logique et de traitement des données
