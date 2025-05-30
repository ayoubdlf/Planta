package planta.modele;


public class Plante {

    private String nom, description, image, origine;
    private TypePlante type;
    private double prix;
    private int eau;
    private int[] temperature, humidite;
    private boolean estToxique;

    public Plante() {}

    /*
    public Plante(String nom, String type, String description, double prix, String image, String origine, int eau, int[] temperature, int[] humidite, boolean estToxique) {
        this.setNom(nom);
        this.setType(type);
        this.setPrix(prix);
        this.setDescription(description);
        this.setImage(image);
        this.setEau(eau);
        this.setTemperature(temperature);
        this.setEstToxique(estToxique);
        this.setOrigine(origine);
        this.setHumidite(humidite);
    }
    */

    public String getNom()                  { return this.nom;  }
    public TypePlante getType()             { return this.type; }
    public double getPrix()                 { return this.prix; }
    public String getDescription()          { return this.description; }
    public String getImage()                { return this.image; }
    public int getEau()                     { return this.eau; }
    public int[] getTemperature()           { return this.temperature; }
    public int[] getHumidite()              { return this.humidite; }
    public boolean getEstToxique()          { return this.estToxique; }
    public String getOrigine()              { return this.origine; }


    public void setNom(String nom)                 { this.nom         = nom;  }
    public void setType(TypePlante type)           { this.type        = type; }
    public void setPrix(double prix)               { this.prix        = prix; }
    public void setDescription(String desc)        { this.description = desc; }
    public void setImage(String url)               { this.image       = url; }
    public void setEau(int eau)                    { this.eau         = eau; }
    public void setTemperature(int[] temperature)  { this.temperature = temperature; }
    public void setHumidite(int[] humidite)        { this.humidite    = humidite; }
    public void setEstToxique(boolean estToxique)  { this.estToxique  = estToxique; }
    public void setOrigine(String origine)         { this.origine     = origine; }

    @Override public String toString()             { return getNom(); }
}
