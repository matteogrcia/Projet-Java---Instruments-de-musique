public abstract class Vent extends Instrument {
    protected String materiau;

    public Vent(int id, String nom, String marque, double prix, String materiau) {
        super(id, nom, marque, prix);
        this.materiau = materiau;
    }

    public String getMateriau() { return materiau; }
}