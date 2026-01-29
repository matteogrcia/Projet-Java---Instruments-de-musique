public abstract class Percussion extends Instrument {
    @CsvCol(index = 5) protected boolean estPeau;

    public Percussion(int id, String nom, String marque, double prix, boolean estPeau) {
        super(id, nom, marque, prix);
        this.estPeau = estPeau;
    }
}