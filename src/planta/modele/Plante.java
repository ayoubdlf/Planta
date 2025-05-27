package planta.modele;

/**
 * Modele d'une plante
 */
public class Plante {
    private String     nom;
    private TypePlante type;
    private String     description;
    private double     eau;
    private boolean    toxique;
    private String     origine;
    private int[]      temperature; // [min, max]
    private int[]      humidite;    // [min, max]
    private double     prix;
    private boolean    populaire;
    private String     image;


    public Plante(String nom, TypePlante type, String description, double eau, int[] temperature, boolean toxique, String origine, int[] humidite, double prix, boolean populaire, String image) {
        assert nom != null && !nom.isEmpty()                  : "Le nom de la plante ne doit pas etre vide";
        assert type != null                                   : "Le type de plante ne peut pas etre nul";
        assert description != null                            : "La description ne peut pas etre nulle";
        assert eau >= 0                                       : "La quantité d'eau doit etre supérieur ou égale a 0";
        assert origine != null && !origine.isEmpty()          : "L'origine ne doit pas etre vide";
        assert prix >= 0                                      : "Le prix doit etre supérieur ou égale a 0.00";
        assert image != null && !image.isEmpty()              : "L'image de la plante ne doit pas etre vide";

        assert temperature != null && temperature.length == 2 : "La température doit contenir exactement deux valeurs : min et max";
        assert humidite    != null && humidite.length    == 2 : "L'humidité doit contenir exactement deux valeurs : min et max";
        assert humidite[0] >= 0 && humidite[1] <= 100         : "L'humidité doit être comprise entre 0% et 100%";
        assert temperature[0] <= temperature[1]               : "La température minimale doit être inférieure ou égale à la maximale";
        assert humidite[0]    <= humidite[1]                  : "L'humidité minimale doit être inférieure ou égale à la maximale";



        this.nom         = nom;
        this.type        = type;
        this.description = description;
        this.eau         = eau;
        this.temperature = temperature;
        this.toxique     = toxique;
        this.origine     = origine;
        this.humidite    = humidite;
        this.prix        = prix;
        this.populaire   = populaire;
        this.image       = image;
    }

    /* ——————————  GETTERS  —————————— */

    public String getNom() {
        return this.nom;
    }

    public TypePlante getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public double getEau() {
        return this.eau;
    }

    public int[] getTemperature() {
        return this.temperature;
    }

    public boolean estToxique() {
        return this.toxique;
    }

    public String getOrigine() {
        return this.origine;
    }

    public int[] getHumidite() {
        return this.humidite;
    }

    public double getPrix() {
        return this.prix;
    }

    public boolean estPopulaire() {
        return this.populaire;
    }

    public String getImage() {
        return this.image;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.getNom(), (this.estPopulaire() ? " ★" : ""));
    }
}
