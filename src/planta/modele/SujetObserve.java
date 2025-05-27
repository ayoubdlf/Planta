package planta.modele;

import planta.controle.Observateur;

import java.util.ArrayList;
import java.util.List;


public class SujetObserve {

    private ArrayList<Observateur> observateurs;

    public SujetObserve() {
        this.observateurs = new ArrayList<>();
    }

    public void ajouterObservateur(Observateur observateur) {
        assert (observateur != null) : "L'observateur ne doit pas etre nul";

        this.observateurs.add(observateur);
    }

    public void notifierObservateurs() {
        List<Observateur> observateurs = new ArrayList<>(this.observateurs);

        for (Observateur observateur : observateurs) {
            observateur.reagir();
        }
    }

}
