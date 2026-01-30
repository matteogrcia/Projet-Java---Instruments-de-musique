import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventaireSwing extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private GestionnaireCSV gestionnaire = new GestionnaireCSV();

    public InventaireSwing() {
        setTitle("Gestion de l'Inventaire - Musique");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Colonnes du tableau
        String[] colonnes = {"ID", "Nom", "Marque", "Prix"};
        model = new DefaultTableModel(colonnes, 0);
        table = new JTable(model);

        // 2. Bouton de chargement
        JButton btnCharger = new JButton("Charger les Guitares");
        btnCharger.addActionListener(e -> chargerDonneesDansTableau());

        // 3. Mise en page
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnCharger, BorderLayout.SOUTH);
    }

    private void chargerDonneesDansTableau() {
        try {
            // Utilisation de votre GestionnaireCSV
            List<GuitareElectrique> guitares = gestionnaire.chargerDonnees("src/instruments.csv", GuitareElectrique.class);

            // On vide le tableau avant d'ajouter
            model.setRowCount(0);

            for (GuitareElectrique g : guitares) {
                // On utilise les getters de vos classes Instrument et GuitareElectrique
                Object[] ligne = { g.getId(), g.getNom(), g.getMarque(), g.getPrix() + "€" };
                model.addRow(ligne);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        // Lancement de l'interface sur le thread dédié à l'affichage
        SwingUtilities.invokeLater(() -> {
            new InventaireSwing().setVisible(true);
        });
    }
}