package planta.modele;

import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;


public class Catalogue {

    private static ObservableList<Plante> plantes = getDonneesFictives();

    public static ObservableList<Plante> getPlantes() {
        return plantes;
    }

    public static void ajouterPlante(Plante plante) {
        plantes.add(plante);
    }

    public static void setPlantesDepuisJSON(ArrayList<Plante> listePlantes) {
        plantes.clear();
        plantes.addAll(listePlantes);
    }

    public static String toJSON() {
        List<Plante> plantes = Catalogue.getPlantes();
        return new Gson().toJson(plantes);
    }

    private static ObservableList<Plante> getDonneesFictives() {
        ObservableList<Plante> liste = FXCollections.observableArrayList();

        // 1. Lys de la Paix
        Plante lysDeLaPaix = new Plante();
        lysDeLaPaix.setNom("Lys de la Paix");
        lysDeLaPaix.setType(TypePlante.INTERIEUR);
        lysDeLaPaix.setPrix(20.0);
        lysDeLaPaix.setDescription("Le Spathiphyllum, communément appelé 'Lys de la Paix', est une plante d'intérieur populaire pour ses feuilles vert foncé et ses fleurs blanches élégantes. Elle est appréciée pour sa capacité à purifier l'air et sa facilité d'entretien.");
        lysDeLaPaix.setEau(500);
        lysDeLaPaix.setTemperature(new int[]{18, 24});
        lysDeLaPaix.setHumidite(new int[]{50, 70});
        lysDeLaPaix.setEstToxique(true);
        lysDeLaPaix.setOrigine("Amérique centrale et du Sud");
        lysDeLaPaix.setImage("/images/p1.jpg");
        liste.add(lysDeLaPaix);

        // 2. Pothos
        Plante pothos = new Plante();
        pothos.setNom("Pothos");
        pothos.setType(TypePlante.INTERIEUR);
        pothos.setPrix(15.0);
        pothos.setDescription("Le Pothos, ou Epipremnum aureum, est une plante grimpante d'intérieur reconnue pour sa robustesse et sa facilité d'entretien. Ses feuilles panachées de vert et de jaune apportent une touche de verdure à tout espace.");
        pothos.setEau(300);
        pothos.setTemperature(new int[]{18, 30});
        pothos.setHumidite(new int[]{40, 60});
        pothos.setEstToxique(true);
        pothos.setOrigine("Polynésie française");
        pothos.setImage("/images/p2.jpg");
        liste.add(pothos);

        // 3. Ficus elastica
        Plante ficusElastica = new Plante();
        ficusElastica.setNom("Ficus elastica");
        ficusElastica.setType(TypePlante.INTERIEUR);
        ficusElastica.setPrix(25.0);
        ficusElastica.setDescription("Le Ficus elastica, ou caoutchouc, est une plante d'intérieur robuste aux grandes feuilles brillantes. Elle est idéale pour ajouter une touche tropicale à votre intérieur.");
        ficusElastica.setEau(400);
        ficusElastica.setTemperature(new int[]{16, 24});
        ficusElastica.setHumidite(new int[]{40, 60});
        ficusElastica.setEstToxique(true);
        ficusElastica.setOrigine("Asie du Sud-Est");
        ficusElastica.setImage("/images/p3.jpg");
        liste.add(ficusElastica);

        // 4. Monstera deliciosa
        Plante monstera = new Plante();
        monstera.setNom("Monstera deliciosa");
        monstera.setType(TypePlante.INTERIEUR);
        monstera.setPrix(30.0);
        monstera.setDescription("La Monstera deliciosa est une plante tropicale appréciée pour ses grandes feuilles perforées. Elle apporte une ambiance exotique et luxuriante à tout intérieur.");
        monstera.setEau(500);
        monstera.setTemperature(new int[]{18, 27});
        monstera.setHumidite(new int[]{60, 80});
        monstera.setEstToxique(true);
        monstera.setOrigine("Amérique centrale");
        monstera.setImage("/images/p1.jpg");
        liste.add(monstera);

        // 5. Sansevieria trifasciata
        Plante sansevieria = new Plante();
        sansevieria.setNom("Sansevieria trifasciata");
        sansevieria.setType(TypePlante.INTERIEUR);
        sansevieria.setPrix(18.0);
        sansevieria.setDescription("La Sansevieria, ou langue de belle-mère, est une plante d'intérieur très résistante, idéale pour les débutants. Elle tolère des conditions de lumière et d'arrosage variées.");
        sansevieria.setEau(200);
        sansevieria.setTemperature(new int[]{15, 30});
        sansevieria.setHumidite(new int[]{30, 50});
        sansevieria.setEstToxique(true);
        sansevieria.setOrigine("Afrique de l'Ouest");
        sansevieria.setImage("/images/p2.jpg");
        liste.add(sansevieria);

        // 6. Chlorophytum comosum
        Plante chlorophytum = new Plante();
        chlorophytum.setNom("Chlorophytum comosum");
        chlorophytum.setType(TypePlante.INTERIEUR);
        chlorophytum.setPrix(12.0);
        chlorophytum.setDescription("Le Chlorophytum comosum, ou plante araignée, est une plante d'intérieur facile à entretenir, connue pour ses longues feuilles arquées et sa capacité à purifier l'air.");
        chlorophytum.setEau(300);
        chlorophytum.setTemperature(new int[]{16, 24});
        chlorophytum.setHumidite(new int[]{40, 60});
        chlorophytum.setEstToxique(false);
        chlorophytum.setOrigine("Afrique du Sud");
        chlorophytum.setImage("/images/p3.jpg");
        liste.add(chlorophytum);

        return liste;
    }
}