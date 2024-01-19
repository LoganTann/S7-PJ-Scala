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
* `[offline=true]` récupère les données depuis le fichier json au lieu de l'API temps réel. Note : le mode offline ne possède que 20 stations dans son dataset. Créé au départ à des fins d'essais, il est recommandé de ne pas utiliser cette option.

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

**Choix techniques**

- Un serveur back-end pour afficher les données plutôt qu'une application console ou native, pour ajouter un front-end en piste d'améliorations. Le serveur est architecturé en Domain (contrôleur, service, repository, entities).
- Zio-http pour créer le serveur dans le runtime ZIO. Sert aussi de client.
- Zstream pour la lecture de fichiers et décodage en String. Les manipulations natives de listes en scala étaient largement suffisantes pour le filtrage de donneés.
- Zio-json pour sérialiser / désérialiser les case classes et d'avoir une gestion d'erreur avec son système de streams.
- Des case classes avec des companions objects (qui peuvent avoir des extension methods), plutôt que des classes simples. Encouragé par zio-json et nous a fait gagner du temps grâce à sa simplicité.
- Zio-test car permet de faire des tests simples comme tester des streams ZIO.
- Pas de récursion, car non pertinent pour le genre d'opérations que nous faisions.

**Feedback**

On comprends les avantages de la programmation fonctionnelle, et scala propose une syntaxe très élégante comparé à d'autres langages similaires (haskell, ocaml). C'est très pratique pour le traitement des données. La gestion d'erreur est également élégante, bien que moins intuitive par rapport aux langages classiques (tel que java).

Le sujet que nous avons choisi était peut être un peu trop difficile à partager en groupe de quatre. Le début pouvait être distribué, mais la mise en commun et les améliorations / erreurs de dernière minutes ne l'était pas vraiment.

En tout cas, le projet était très intéressant et abordable. La difficulté n'est pas très haute, et tant mieux, car nous avons passés énormément de temps pour faire fonctionner des choses très basiques (désérialisation, import correct du client HTTP...). Il était difficile de trouver beaucoup d'exemples, la documentation était parfois incomplète.
