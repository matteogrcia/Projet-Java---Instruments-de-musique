public class Flute extends Vent {
    public Flute(int id, String nom, String marque, double prix, String materiau) {
        super(id, nom, marque, prix, materiau);
    }

    @Override
    public void jouer() {
        System.out.println("La flûte en " + materiau + " produit un son léger et aérien.");
    }
}