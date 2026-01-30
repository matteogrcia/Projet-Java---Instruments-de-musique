public class PianoClavier extends Corde implements Electrique{


    private String type; // numérique, hybride
    private boolean estBranchee = false;

        public PianoClavier(int id, String nom, String marque, double prix,
                            int nbCordes,  String type) {
            super(id, nom, marque, prix, nbCordes);
            this.type = type;
        }

        public PianoClavier() {
            super(0, "Inconnu", "Inconnue", 0.0, 0);
            this.type = "Inconnu";
        }

        @Override
        public void jouer() {
            if (estBranchee){
                System.out.println("Le piano clavier " + type + " " + nom + " joue avec amplification.");
            }
            else{
                System.out.println("Le piano clavier ne sors pas de ") ;
            }

        }

        public String getType() {
            return type;
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


