import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventaireSwing extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JPanel panelSousCategories;
    private Class<? extends Instrument> classeActuelle;

    private Inventaire<Instrument> monInventaire = new Inventaire<>();
    private GestionnaireCSV gestionnaire = new GestionnaireCSV();
    private final String CSV_PATH = "src/instruments.csv";

    public InventaireSwing() {
        setTitle("Magasin de Musique - Gestion Complète");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Chargement initial des données
        monInventaire.chargerDepuisCSV(gestionnaire, CSV_PATH, new Class[]{
                GuitareElectrique.class, GuitareAcoustique.class, Piano.class, Clavier.class,
                Flute.class, Clarinette.class, Saxophone.class,
                Batterie.class, Tambour.class, Triangle.class
        });

        // --- HAUT : GRANDES CATÉGORIES ---
        JPanel panelCategories = new JPanel(new FlowLayout());
        JButton btnCordes = new JButton("Cordes / Claviers");
        JButton btnVents = new JButton("Vents");
        JButton btnPercussions = new JButton("Percussions");

        panelCategories.add(btnCordes);
        panelCategories.add(btnVents);
        panelCategories.add(btnPercussions);
        add(panelCategories, BorderLayout.NORTH);

        // --- GAUCHE : SOUS-CATÉGORIES ---
        panelSousCategories = new JPanel();
        panelSousCategories.setLayout(new BoxLayout(panelSousCategories, BoxLayout.Y_AXIS));
        panelSousCategories.setPreferredSize(new Dimension(200, 0));
        panelSousCategories.setBorder(BorderFactory.createTitledBorder("Instruments"));
        add(panelSousCategories, BorderLayout.WEST);

        // --- CENTRE : TABLEAU ---
        String[] colonnes = {"ID", "Nom", "Marque", "Prix"};
        model = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- BAS : ACTIONS ---
        JPanel panelActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.setBackground(new Color(255, 102, 102)); // On garde juste le rouge pour la sécurité

        panelActions.add(btnAjouter);
        panelActions.add(btnSupprimer);
        add(panelActions, BorderLayout.SOUTH);

        // --- LOGIQUE DES BOUTONS ---
        btnCordes.addActionListener(e -> afficherSousCategories("Cordes"));
        btnVents.addActionListener(e -> afficherSousCategories("Vents"));
        btnPercussions.addActionListener(e -> afficherSousCategories("Percussions"));

        btnSupprimer.addActionListener(e -> actionSupprimer());
        btnAjouter.addActionListener(e -> actionAjouter());
    }

    private void actionSupprimer() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un instrument.");
            return;
        }

        int id = (int) table.getValueAt(row, 0);
        String nom = (String) table.getValueAt(row, 1);

        // CONFIRMATION SUPPRESSION
        int confirm = JOptionPane.showConfirmDialog(this,
                "Supprimer l'instrument : " + nom + " (ID: " + id + ") ?",
                "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                monInventaire.supprimer(id);
                rafraichirTableau(classeActuelle);
            } catch (InstrumentNotFoundException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    private void actionAjouter() {
        if (classeActuelle == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez d'abord une catégorie à gauche.");
            return;
        }

        JTextField nomF = new JTextField();
        JTextField marqueF = new JTextField();
        JTextField prixF = new JTextField();

        Object[] message = {"Nom:", nomF, "Marque:", marqueF, "Prix:", prixF};

        int option = JOptionPane.showConfirmDialog(null, message, "Nouvel instrument (" + classeActuelle.getSimpleName() + ")", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            // CONFIRMATION AJOUT
            int confirm = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment ajouter cet instrument ?", "Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Instrument n = classeActuelle.getDeclaredConstructor().newInstance();
                    n.setNom(nomF.getText());
                    n.setMarque(marqueF.getText());
                    n.setPrix(Double.parseDouble(prixF.getText()));

                    monInventaire.ajouterEtSauvegarder(n, gestionnaire, CSV_PATH);
                    rafraichirTableau(classeActuelle);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
                }
            }
        }
    }

    private void afficherSousCategories(String categorie) {
        panelSousCategories.removeAll();
        switch (categorie) {
            case "Cordes":
                ajouterBoutonType("Guitare Acoustique", GuitareAcoustique.class);
                ajouterBoutonType("Guitare Électrique", GuitareElectrique.class);
                ajouterBoutonType("Piano", Piano.class);
                ajouterBoutonType("Clavier", Clavier.class);
                break;
            case "Vents":
                ajouterBoutonType("Flûte", Flute.class);
                ajouterBoutonType("Clarinette", Clarinette.class);
                ajouterBoutonType("Saxophone", Saxophone.class);
                break;
            case "Percussions":
                ajouterBoutonType("Batterie", Batterie.class);
                ajouterBoutonType("Tambour", Tambour.class);
                ajouterBoutonType("Triangle", Triangle.class);
                break;
        }
        panelSousCategories.revalidate();
        panelSousCategories.repaint();
    }

    private void ajouterBoutonType(String libelle, Class<? extends Instrument> classeCible) {
        JButton btn = new JButton(libelle);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 35));
        btn.addActionListener(e -> {
            this.classeActuelle = classeCible;
            rafraichirTableau(classeCible);
        });
        panelSousCategories.add(btn);
        panelSousCategories.add(Box.createVerticalStrut(5));
    }

    private void rafraichirTableau(Class<? extends Instrument> classeCible) {
        model.setRowCount(0);
        if (classeCible == null) return;

        List<Instrument> stockActuel = monInventaire.getStock();
        for (Instrument inst : stockActuel) {
            if (classeCible.isInstance(inst)) {
                Object[] ligne = { inst.getId(), inst.getNom(), inst.getMarque(), inst.getPrix() + "€" };
                model.addRow(ligne);
            }
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new InventaireSwing().setVisible(true));
    }
}