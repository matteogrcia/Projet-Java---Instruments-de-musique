public class Triangle extends Percussion {

    public Triangle(int id, String nom, String marque, double prix) {
        super(id, nom, marque, prix, false);
    }
    public Triangle() {
        super(0, "Inconnu", "Inconnue", 0.0, false);
    }

    @Override
    public void jouer() {
        System.out.println("Ting ! Le triangle " + nom + " émet un son cristallin.");
    }

    public void faireTrille() {
        System.out.println("Le triangle résonne rapidement : t-t-t-t-ting !");
    }
}