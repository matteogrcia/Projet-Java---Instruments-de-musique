public class GuitareAcoustique extends Corde {

    public GuitareAcoustique(int id, String nom, String marque, double prix, int nbCordes) {
        super(id, nom, marque, prix, nbCordes);
    }

    @Override
    public void jouer() {
        System.out.println("La guitare acoustique " + nom + " résonne naturellement.");
    }
}