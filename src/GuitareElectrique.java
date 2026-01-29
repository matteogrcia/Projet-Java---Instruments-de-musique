public class GuitareElectrique extends Corde implements Electrique {

    private boolean estBranchee = false;

    public GuitareElectrique(int id, String nom, String marque, double prix, int nbCordes) {
        super(id, nom, marque, prix, nbCordes);
    }

    @Override
    public void jouer() {
        if (estBranchee) {
            System.out.println("DISTORSION ! La guitare " + nom + " envoie du gros son !");
        } else {
            System.out.println("On entend à peine le son métallique des cordes de la " + nom + "...");
        }
    }

    @Override
    public void brancher() {
        this.estBranchee = true;
        System.out.println("Câble jack inséré. Guitare branchée.");
    }

    @Override
    public void reglerVolume(int niveau) {
        System.out.println("Volume de la guitare réglé sur " + niveau + "/10.");
    }
}