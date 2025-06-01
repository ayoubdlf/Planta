# Planta 🌱

**Planta** est une application JavaFX simple pour vous aider à gérer votre collection de plantes.

## Fonctionnalités

- 🌿 Afficher un catalogue de plantes
- ➕ Ajouter de nouvelles plantes
- 📝 Modifier les informations d'une plante
- ➖ Supprimer des plantes existantes
- 📂 Charger des plantes à partir d'un fichier JSON
- 💾 Enregistrer des plantes dans un fichier JSON

## Démarrage

### Prérequis
- Java 21
- JavaFX SDK 23.0.2

### Exécution de l'application
Après avoir construit le projet en un JAR, exécutez :

```
java --module-path CHEMIN_JAVAFX/javafx-sdk-23.0.2/lib --add-modules javafx.controls,javafx.fxml -jar collection.jar
```

Remplacez `CHEMIN_JAVAFX` par le chemin vers votre SDK JavaFX local.


Un fichier `planta.json` contenant des données de plantes initiales est fourni. Vous pouvez le charger la première fois pour commencer avec un exemple de collection.