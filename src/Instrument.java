public abstract class Instrument {
    @CsvCol(index = 1) protected int id;
    @CsvCol(index = 2) protected String nom;
    @CsvCol(index = 3) protected String marque;
    @CsvCol(index = 4) protected double prix;

    public Instrument(int id, String nom, String marque, double prix) {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.prix = prix;
    }

    public abstract void jouer();

    public void afficherInfos() {
        System.out.println("Instrument : " + nom + " | Marque : " + marque + " | Prix : " + prix + "€");
    }

    public String getNom() { return nom; }
    public double getPrix() { return prix; }
    public int getId() {
        return id;
    }
    public String getMarque() {
        return marque;
    }
}