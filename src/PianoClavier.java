public class PianoClavier extends Corde{


        @CsvCol(index = 8)
        private String type; // numérique, hybride

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
            System.out.println("Le piano clavier " + type + " " + nom + " joue avec amplification.");
        }

        public String getType() {
            return type;
        }
    }

