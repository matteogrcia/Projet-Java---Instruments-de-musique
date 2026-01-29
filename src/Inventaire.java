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

    public boolean existe(int id) {
        return stock.stream().anyMatch(i -> i.getId() == id);
    }

    public void ajouter(T instrument) {
        if (existe(instrument.getId())) {
            throw new IllegalArgumentException(
                    "Un instrument avec l'ID" + instrument.getId() + "existe déjà."
            );
        }
        stock.add(instrument);
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

    public boolean estVide() {
        return stock.isEmpty();
    }

    public List<T> getStock() {
        return stock;
    }
}