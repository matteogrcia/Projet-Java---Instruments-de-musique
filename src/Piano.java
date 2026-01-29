public class Piano extends Corde {

    @CsvCol(index = 7)
    private String type; // droit, à queue

    public Piano(int id, String nom, String marque, double prix, int nbCordes, String type) {
        super(id, nom, marque, prix, nbCordes);
        this.type = type;
    }

    public Piano() {
        super(0, "Inconnu", "Inconnue", 0.0, 0);
        this.type = "Inconnu";
    }

    @Override
    public void jouer() {
        System.out.println("Le piano " + type + " " + nom + " joue une mélodie harmonieuse.");
    }

    public String getType() {
        return type;
    }
}
