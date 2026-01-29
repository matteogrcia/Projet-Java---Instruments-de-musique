public class GuitareAcoustique extends Corde {

    public GuitareAcoustique(int id, String nom, String marque, double prix, int nbCordes) {
        super(id, nom, marque, prix, nbCordes);
    }
    public GuitareAcoustique() {
        super(0, "Inconnu", "Inconnue", 0.0, 0);
    }

    @Override
    public void jouer() {
        System.out.println("La guitare acoustique " + nom + " résonne naturellement.");
    }
}