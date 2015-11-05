# TweetEnOr


# Installation des dépendences

## Twitter 4j

* Récupérez la dernière version de Twitter4J sur http://twitter4j.org/en/index.html
* Extraire le fichier lib/twitter4j-core-4.0.4.jar
* Importer le fichier jar dans votre IDE (/!\ à ne pas le commit)

## Tree tagger

* Suivre les instructions ici : http://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/
* Ou si vous êtes sous Linux: télécharger le fichier (http://amoki.fr/tree-tagger.tar.gz) et décompressez le
* Installer le jar : http://mvnrepository.com/artifact/org.annolab.tt4j/org.annolab.tt4j/1.2.1
* Définir le chemin du répertoire de tree-tagger via la variable d'environnement TREE_TAGGER_PATH  (ex: /usr/share/tree-tagger/)


# Configuration des identifiants Twitter
* Ils se trouvent sur le trello, dans la carte "credentials twitter"
* Ils sont sous la forme CLE=VALEUR
* Sous Eclipse, allez dans run > run configuration > [Selectionner le main] > Environment > New
