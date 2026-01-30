import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Inventaire<T extends Instrument> {

    private List<T> stock;

    public Inventaire() {
        this.stock = new ArrayList<>();
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
        System.out.println("Instrument ajouté et fichier mis à jour !");
    }

    public void supprimer(int id) throws InstrumentNotFoundException {
        T instr = trouverParId(id);
        stock.remove(instr);
    }

    public void trierParPrix() {
        stock.sort(Comparator.comparingDouble(Instrument::getPrix));
        System.out.println("Inventaire trié par prix.");
    }

    public void trierParNom() {
        stock.sort(Comparator.comparing(Instrument::getNom));
        System.out.println("Inventaire trié par nom.");
    }

    public void afficherInventaire() {
        System.out.println("État de l'Inventaire ");
        for (T instrument : stock) {
            instrument.afficherInfos();
        }
    }

    public T trouverParId(int id) throws InstrumentNotFoundException {
        return stock.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new InstrumentNotFoundException("Instrument avec ID " + id + " introuvable."));
    }

    public List<T> rechercher(String motCle) {
        return stock.stream()
                .filter(i -> i.getNom().toLowerCase().contains(motCle.toLowerCase())
                        || i.getMarque().toLowerCase().contains(motCle.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<T> getStock() {
        return stock;
    }

    public void chargerDepuisCSV(GestionnaireCSV gestionnaire, String fichier, Class<? extends T>[] classes) {
        try {
            for (Class<? extends T> classe : classes) {
                List<? extends T> items = gestionnaire.chargerDonnees(fichier, classe);
                this.stock.addAll(items);
            }
            System.out.println(stock.size() + " instruments chargés avec succès.");
        } catch (Exception e) {
            System.err.println("Erreur de chargement : " + e.getMessage());
        }
    }
}