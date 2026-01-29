import java.io.*;
import java.util.*;
import java.lang.reflect.Field;

public class GestionnaireCSV{

    public <T> List<T> chargerDonnees(String cheminFichier, Class<T> classeCible) throws Exception {
        List<T> resultat = new ArrayList<>();
        BufferedReader lecteur = new BufferedReader(new FileReader(cheminFichier));
        String ligne;

        while ((ligne = lecteur.readLine()) != null) {
            String[] colonnes = ligne.split(";");
            T objet = classeCible.getDeclaredConstructor().newInstance();

            // ON REMONTE LA GÉNÉALOGIE (C'est l'astuce pour Corde + Instrument)
            Class<?> classeActuelle = classeCible;
            while (classeActuelle != null) {

                for (Field champ : classeActuelle.getDeclaredFields()) {
                    if (champ.isAnnotationPresent(CsvCol.class)) {
                        CsvCol etiquette = champ.getAnnotation(CsvCol.class);
                        int index = etiquette.index() - 1; //

                        if (index < colonnes.length) {
                            champ.setAccessible(true);
                            Object valeur = convertir(colonnes[index], champ.getType());
                            champ.set(objet, valeur);
                        }
                    }
                }
                // On passe au parent (ex: de Corde à Instrument)
                classeActuelle = classeActuelle.getSuperclass();
            }
            resultat.add(objet);
        }
        lecteur.close();

        // TRI PERSISTANT PAR ID (Index 1)
        resultat.sort(Comparator.comparingInt(this::extraireId));

        return resultat;
    }

    private Object convertir(String valeur, Class<?> type) {
        if (type == int.class) return Integer.parseInt(valeur);
        if (type == double.class) return Double.parseDouble(valeur);
        return valeur;
    }

    private int extraireId(Object obj) {
        try {
            // Cherche le champ "id" dans Instrument
            Field f = obj.getClass().getSuperclass().getSuperclass().getDeclaredField("id");
            f.setAccessible(true);
            return (int) f.get(obj);
        } catch (Exception e) { return 0; }
    }
}