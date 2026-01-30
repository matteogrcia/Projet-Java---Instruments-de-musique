public class Tambour extends Percussion{


        public Tambour(int id, String nom, String marque, double prix, boolean estPeau) {
            super(id, nom, marque, prix, estPeau);
        }

        public Tambour() {
            super(0, "Inconnu", "Inconnue", 0.0, true);
        }

        @Override
        public void jouer() {
            System.out.println(
                    "Le tambour " + nom +
                            (estPeau ? " avec une peau" : " sans peau") +
                            " produit un rythme puissant."
            );
        }


}
