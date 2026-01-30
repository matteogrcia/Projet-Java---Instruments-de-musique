import java.io.*;
import java.util.*;
import java.lang.reflect.Field;

public class GestionnaireCSV {

    public <T> List<T> chargerDonnees(String chemin, Class<T> classeCible) throws Exception {
        List<T> resultat = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] colonnes = ligne.split(";");

                if (colonnes.length == 0 || !colonnes[0].equalsIgnoreCase(classeCible.getSimpleName())) {
                    continue;
                }

                T objet = classeCible.getDeclaredConstructor().newInstance();

                Class<?> actuelle = classeCible;
                while (actuelle != null) {
                    for (Field champ : actuelle.getDeclaredFields()) {
                        if (champ.isAnnotationPresent(CsvCol.class)) {
                            CsvCol annotation = champ.getAnnotation(CsvCol.class);
                            int index = annotation.index(); // 1-based index
                            index--; // on convertit en 0-based

                            if (index < colonnes.length) {
                                champ.setAccessible(true);
                                Object valeurConvertie = convertir(colonnes[index], champ.getType());
                                champ.set(objet, valeurConvertie);
                            }
                        }
                    }
                    actuelle = actuelle.getSuperclass();
                }

                resultat.add(objet);
            }
        }

        resultat.sort(Comparator.comparingInt(this::extraireId));

        return resultat;
    }

    public <T> void sauvegarderDonnees(String chemin, List<T> objets)
            throws IOException, IllegalAccessException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(chemin))) {

            for (T obj : objets) {
                Class<?> classeActuelle = obj.getClass();
                Map<Integer, String> colonnes = new TreeMap<>();

                while (classeActuelle != null) {
                    for (Field champ : classeActuelle.getDeclaredFields()) {
                        if (champ.isAnnotationPresent(CsvCol.class)) {
                            CsvCol annotation = champ.getAnnotation(CsvCol.class);
                            champ.setAccessible(true);
                            Object valeur = champ.get(obj);
                            colonnes.put(annotation.index(),
                                    valeur == null ? "" : valeur.toString());
                        }
                    }
                    classeActuelle = classeActuelle.getSuperclass();
                }

                StringBuilder ligne = new StringBuilder();

                // Nom de la classe
                ligne.append(obj.getClass().getSimpleName());

                // Colonnes triées automatiquement (TreeMap)
                for (String valeur : colonnes.values()) {
                    ligne.append(";").append(valeur);
                }

                bw.write(ligne.toString());
                bw.newLine();
            }
        }
    }


    private Object convertir(String valeur, Class<?> type) {
        if (valeur == null || valeur.trim().isEmpty()) return null;
        try {
            if (type == int.class || type == Integer.class) return Integer.parseInt(valeur);
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
