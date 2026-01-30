public class Clarinette extends Vent{


        public Clarinette(int id, String nom, String marque, double prix, String materiau) {
            super(id, nom, marque, prix, materiau);
        }

        public Clarinette() {
            super(0, "Inconnu", "Inconnue", 0.0, "bois");
        }

        @Override
        public void jouer() {
            System.out.println("La clarinette " + nom + " produit un son doux.");
        }

}
