import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Initialisation des outils
        Inventaire<Instrument> monMagasin = new Inventaire<>();
        GestionnaireCSV gestionnaire = new GestionnaireCSV();

        String fichier = "src/instruments.csv";
        Class[] typesACharger = {GuitareElectrique.class, Saxophone.class, Triangle.class};

        try {
            // 2. CHARGEMENT initial
            System.out.println("=== Initialisation du magasin ===");
            monMagasin.chargerDepuisCSV(gestionnaire, fichier, typesACharger);
            monMagasin.afficherInventaire();

            // 3. TEST DE L'AJOUT AVEC ID AUTOMATIQUE
            System.out.println("\n=== Ajout d'un nouvel instrument ===");
            // On met 0 pour l'ID, la méthode ajouterEtSauvegarder va le remplacer par le bon
            GuitareElectrique nouvelleGuitare = new GuitareElectrique(0, "Telecaster", "Fender", 950.0, 6);

            monMagasin.ajouterEtSauvegarder(nouvelleGuitare, gestionnaire, fichier);

            // 4. TEST DE RECHERCHE
            System.out.println("\n=== Recherche des instruments 'Yamaha' ===");
            List<Instrument> resultats = monMagasin.rechercher("Yamaha");
            if (resultats.isEmpty()) {
                System.out.println("Aucun résultat trouvé.");
            } else {
                resultats.forEach(Instrument::afficherInfos);
            }

            // 5. TEST DE SUPPRESSION
            // On tente de supprimer l'ID 2 (le Saxophone par exemple)
            System.out.println("\n=== Vente (Suppression) de l'ID 2 ===");
            try {
                monMagasin.supprimer(2);
                // Important : On sauvegarde après la suppression pour que ce soit réel !
                gestionnaire.sauvegarderDonnees(fichier, monMagasin.getStock());
                System.out.println("L'instrument ID 2 a été retiré du fichier CSV.");
            } catch (InstrumentNotFoundException e) {
                System.out.println("Erreur : " + e.getMessage());
            }

            // 6. AFFICHAGE FINAL TRIÉ
            System.out.println("\n=== Inventaire final mis à jour et trié par prix ===");
            monMagasin.trierParPrix();
            monMagasin.afficherInventaire();

            monMagasin.trierParNom();
            monMagasin.afficherInventaire();

        } catch (Exception e) {
            System.err.println("ERREUR CRITIQUE : " + e.getMessage());
            e.printStackTrace();
        }
    }
}