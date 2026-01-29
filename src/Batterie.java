public class Batterie extends Percussion {
    public Batterie(int id, String nom, String marque, double prix) {
        super(id, nom, marque, prix, true);
    }

    @Override
    public void jouer() {
        System.out.println("La batterie " + marque + " résonne avec puissance !");
    }
}