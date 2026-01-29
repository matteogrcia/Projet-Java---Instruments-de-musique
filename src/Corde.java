public abstract class Corde extends Instrument {

    @CsvCol(index = 6) protected int nbCordes;

    public Corde(int id, String nom, String marque, double prix, int nbCordes) {
        super(id, nom, marque, prix);
        this.nbCordes = nbCordes;
    }

    public void accorder() {
        System.out.println("L'instrument à " + nbCordes + " cordes est en train d'être accordé...");
    }

    public int getNbCordes() {
        return nbCordes;
    }
}