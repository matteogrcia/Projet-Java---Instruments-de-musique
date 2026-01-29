public class Flute extends Vent {
    public Flute(int id, String nom, String marque, double prix, String materiau) {
        super(id, nom, marque, prix, materiau);
    }
    public Flute() {
        super(0, "Inconnu", "Inconnue", 0.0, "Inconnue");
    }
    @Override
    public void jouer() {
        System.out.println("La flûte en " + materiau + " produit un son léger et aérien.");
    }
}