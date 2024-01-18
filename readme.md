# Scalapompe

Le site web pour trouver les stations d'essences les moins chères près de chez vous !

Utilise les données ouvertes du ministère de l'économie (https://data.economie.gouv.fr/explore/dataset/prix-des-carburants-en-france-flux-instantane-v2)

## Démarrage

* Installez le SDK scala sur le site officiel.
* Exécutez `sbt run` pour démarrer le projet. Cela va ouvrir un serveur.

## Usage

Pour le moment, seul un seul endpoint est activé: https://localhost:8080/search

Il faut mentionner les paramètres suivants : 
* `lat=[double]` et `lon=[double]`, qui sont la latitude et la longitude de l'emplacement actuel de recherche. S'ils sont invalides, sont remplacés par l'adresse de l'EFREI.
* `distance=[int]` distance de recherche en km. Les stations au delà du radius seront retirées des résultats.
* `offline=[true]` récupère les données depuis le fichier json au lieu de l'API temps réel.

exemple : 
* https://localhost:8080/search?lat=48.7886889&lon=2.358667&distance=100
