import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        GestionnaireCSV gestionnaire = new GestionnaireCSV();

        // On demande au gestionnaire de charger des Guitares depuis le CSV
        List<GuitareElectrique> mesGuitares = gestionnaire.chargerDonnees("src/instruments.csv", GuitareElectrique.class);

        // Grâce au tri persistant, elles sont déjà dans le bon ordre !
        for (GuitareElectrique g : mesGuitares) {
            g.afficherInfos();
        }
    }
}