import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Inventaire<T extends Instrument> {

    private List<T> stock;

    public Inventaire() {
        this.stock = new ArrayList<>();
    }

    public void ajouter(T instrument) {
        stock.add(instrument);
        System.out.println("Ajouté : " + instrument.getNom());
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
        System.out.println("--- État de l'Inventaire ---");
        for (T instrument : stock) {
            instrument.afficherInfos();
        }
    }

    public List<T> getStock() {
        return stock;
    }
}