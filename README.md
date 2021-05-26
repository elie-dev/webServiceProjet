# webServiceProjet
Mon petit forum

Initialisation du projet:
Pour démarrer le projet, créer une base de donnée nommée MonPetitForum,
ensuite dans src/main/java/ressources/aplication.properties, vérifier la bonne configuration par rapport
à vos données locales, nottement pour le username et le password. Vérifier également que la ligne populatedb soit égale à true ("populatedb=true")
lors de la premiere initialisation du projet.Ensuite le repasser en false pour éviter d'ajouter l'utilisateur ADMIN à chaque lancement de l'api.


################################


Voici le script pour insérer les données de la table Categorie:

INSERT INTO `categorie` (`id`, `name`) VALUES
(1, 'Jeux-vidéos'),
(2, 'Animes'),
(3, 'Mangas'),
(4, 'Musique'),
(5, 'Cinéma'),
(6, 'Séries TV'),
(7, 'Littérature'),
(8, 'Sport');

###############################


Le projet n'a malheureusement pas pus etre finalisé à 100%, nous avons eu du mal pour ce qui est des relations de champs entre tables,
pas au niveau de la création des tables mais plutot au niveau des POST, exemple: lors de la création d'un post les champs categorie_id et user_id ne sont pas remplie.
Nous avons également eu des difficultés à la gestion des regles de modérations.
