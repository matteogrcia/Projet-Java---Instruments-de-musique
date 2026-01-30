public class Violoncelle extends Corde{

    //private String type;
    public Violoncelle(int id, String nom, String marque, double prix, int nbCordes, String type) {
        super(id, nom, marque, prix, nbCordes);
        //this.type = type;
    }
    public Violoncelle()  {
        super(0, "Inconnu", "Inconnue", 0.0, 0);
    }

    @Override
    public void jouer() {
        System.out.println("Le saxophone " /*+ type + " "*/ + nom + " joue une mélodie douce forte.");
    }

    /*public String getType() {
        return type;
    }*/
}
