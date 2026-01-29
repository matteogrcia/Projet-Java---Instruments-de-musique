import java.io.*;
import java.util.*;
import java.lang.reflect.Field;

public class GestionnaireCSV {

    public <T> List<T> chargerDonnees(String chemin, Class<T> classeCible) throws Exception {
        List<T> resultat = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(chemin));
        String ligne;

        while ((ligne = br.readLine()) != null) {
            String[] colonnes = ligne.split(";");

            // FILTRE : On vérifie si la ligne commence par le nom de la classe (ex: "GuitareElectrique")
            if (colonnes.length == 0 || !colonnes[0].equalsIgnoreCase(classeCible.getSimpleName())) {
                continue;
            }

            // Création de l'objet (nécessite un constructeur vide dans vos classes)
            T objet = classeCible.getDeclaredConstructor().newInstance();

            // Remplissage des champs via Réflexion
            Class<?> actuelle = classeCible;
            while (actuelle != null) {
                for (Field champ : actuelle.getDeclaredFields()) {
                    if (champ.isAnnotationPresent(CsvCol.class)) {
                        CsvCol annotation = champ.getAnnotation(CsvCol.class);
                        int index = annotation.index() - 1; // On ajuste l'index (0, 1, 2...)

                        if (index < colonnes.length) {
                            champ.setAccessible(true);
                            Object valeurConvertie = convertir(colonnes[index], champ.getType());
                            champ.set(objet, valeurConvertie);
                        }
                    }
                }
                actuelle = actuelle.getSuperclass(); // On remonte vers Instrument
            }
            resultat.add(objet);
        }
        br.close();

        // Application du tri persistant par ID comme demandé
        resultat.sort(Comparator.comparingInt(this::extraireId));

        return resultat;
    }

    private Object convertir(String valeur, Class<?> type) {
        if (valeur == null || valeur.trim().isEmpty()) return null;
        try {
            if (type == int.class || type == Integer.class) return (int) Double.parseDouble(valeur);
            if (type == double.class || type == Double.class) return Double.parseDouble(valeur);
            if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(valeur);
        } catch (Exception e) {
            if (type == int.class) return 0;
            if (type == double.class) return 0.0;
        }
        return valeur;
    }

    private int extraireId(Object obj) {
        Class<?> c = obj.getClass();
        while (c != null) {
            try {
                Field f = c.getDeclaredField("id");
                f.setAccessible(true);
                return (int) f.get(obj);
            } catch (NoSuchFieldException e) {
                c = c.getSuperclass();
            } catch (Exception e) {
                break;
            }
        }
        return 0;
    }
}