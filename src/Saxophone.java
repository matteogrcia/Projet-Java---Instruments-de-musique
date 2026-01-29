public class Saxophone extends Vent {

    @CsvCol(index = 6) private String type;

    public Saxophone(int id, String nom, String marque, double prix, String materiau, String type) {
        super(id, nom, marque, prix, materiau);
        this.type = type;
    }

    @Override
    public void jouer() {
        System.out.println("Le saxophone " + type + " " + nom + " joue une mélodie jazzy.");
    }

    public String getType() {
        return type;
    }
}