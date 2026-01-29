import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireCSV {
    private String cheminFichier;

    public GestionnaireCSV(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public List<Instrument> chargerInstruments() {
        List<Instrument> liste = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] d = ligne.split(";");
                String typeClasse = d[0];

                Instrument ins = creerInstance(typeClasse, d);
                if (ins != null) liste.add(ins);
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture : " + e.getMessage());
        }
        return liste;
    }

    public void sauvegarderInstruments(List<Instrument> liste) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(cheminFichier))) {
            for (Instrument i : liste) {
                pw.println(construireLigneCSV(i));
            }
        } catch (IOException e) {
            System.out.println("Erreur d'écriture : " + e.getMessage());
        }
    }

    private Instrument creerInstance(String type, String[] data) {
        try {
            Class<?> clazz = Class.forName(type);

            if (type.equals("GuitareElectrique")) {
                return (Instrument) clazz.getConstructor(int.class, String.class, String.class, double.class, int.class)
                        .newInstance(Integer.parseInt(data[1]), data[2], data[3], Double.parseDouble(data[4]), Integer.parseInt(data[5]));
            } else if (type.equals("Triangle")) {
                return (Instrument) clazz.getConstructor(int.class, String.class, String.class, double.class)
                        .newInstance(Integer.parseInt(data[1]), data[2], data[3], Double.parseDouble(data[4]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String construireLigneCSV(Instrument i) {
        String ligne = i.getClass().getSimpleName() + ";" + i.id + ";" + i.nom + ";" + i.marque + ";" + i.prix;

        if (i instanceof Corde) ligne += ";" + ((Corde)i).getNbCordes();
        else if (i instanceof Saxophone) ligne += ";" + ((Saxophone)i).getType();

        return ligne;
    }
}