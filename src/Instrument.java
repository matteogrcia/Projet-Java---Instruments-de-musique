public abstract class Instrument {
    protected int id;
    protected String nom;
    protected String marque;
    protected double prix;

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
}