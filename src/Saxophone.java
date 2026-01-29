public class Saxophone extends Vent {

    @CsvCol(index = 7) private String type;

    public Saxophone(int id, String nom, String marque, double prix, String materiau, String type) {
        super(id, nom, marque, prix, materiau);
        this.type = type;
    }
    public Saxophone() {
        super(0, "Inconnu", "Inconnue", 0.0, "inconnu");
    }

    @Override
    public void jouer() {
        System.out.println("Le saxophone " + type + " " + nom + " joue une mélodie jazzy.");
    }

    public String getType() {
        return type;
    }
}