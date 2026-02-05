import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import java.io.IOException;

public class Inventaire<T extends Instrument> {

    private List<T> stock;
    // Définition du logger
    private static final Logger logger = Logger.getLogger(Inventaire.class.getName());

    public Inventaire() {
        this.stock = new ArrayList<>();
        configurerLogger();
    }

    private void configurerLogger() {
        try {
            // Création d'un gestionnaire de fichier (append = true pour ne pas effacer les anciens logs)
            FileHandler fh = new FileHandler("inventaire.log", true);
            fh.setFormatter(new SimpleFormatter()); // Format texte lisible
            logger.addHandler(fh);

            // On peut aussi définir le niveau de précision
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Impossible d'initialiser le fichier de log : " + e.getMessage());
        }
    }

    private int genererProchainId() {
        return stock.stream()
                .mapToInt(Instrument::getId)
                .max()
                .orElse(0) + 1;
    }

    public void ajouterEtSauvegarder(T instrument, GestionnaireCSV g, String fichier) throws Exception {
        int nouvelId = genererProchainId();
        instrument.setId(nouvelId);

        stock.add(instrument);
        g.sauvegarderDonnees(fichier, stock);

        // Log d'information
        logger.info("Nouvel instrument ajouté : ID " + nouvelId + " (" + instrument.getClass().getSimpleName() + ")");
    }

    public void supprimer(int id, GestionnaireCSV g, String fichier) throws Exception {
        try {
            T instr = trouverParId(id);
            stock.remove(instr);
            g.sauvegarderDonnees(fichier, stock);
            logger.info("Instrument avec ID " + id + " supprimé avec succès.");
        } catch (InstrumentNotFoundException e) {
            // Log d'avertissement
            logger.warning("Tentative de suppression échouée : " + e.getMessage());
            throw e;
        }
    }

    public T trouverParId(int id) throws InstrumentNotFoundException {
        return stock.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElseThrow(() -> {
                    String msg = "Instrument avec ID " + id + " introuvable.";
                    logger.fine(msg); // Log de niveau bas (debug)
                    return new InstrumentNotFoundException(msg);
                });
    }

    public void chargerDepuisCSV(GestionnaireCSV gestionnaire, String fichier, Class<? extends T>[] classes) {
        try {
            for (Class<? extends T> classe : classes) {
                List<? extends T> items = gestionnaire.chargerDonnees(fichier, classe);
                this.stock.addAll(items);
            }
            logger.info(stock.size() + " instruments chargés depuis le fichier " + fichier);
        } catch (Exception e) {
            // Log d'erreur sévère
            logger.severe("Erreur critique lors du chargement CSV : " + e.getMessage());
        }
    }

    public List<T> getStock() {
        return stock;
    }
}