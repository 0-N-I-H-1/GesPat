package Agent_Administration;
import Menu.Menu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Rectangle;
import java.awt.Graphics2D;

public class Agent_Administration {

    protected JFrame pagePrincipal;
    protected static JPanel panelPrincipal, panelSecondaire;
    protected static JButton boutonRetour;
    protected static JButton boutonAjouter;
    protected static JButton boutonRechercher;
    protected static JButton boutonVisualiser;
    protected static JButton boutonModifier;
    protected static JButton boutonSupprimer;
    private JLabel labelUserType;
    protected JTable tableauPatient;
    private JScrollPane scrollPanePatient;
    private DefaultTableCellRenderer centrerTextCelulle;
    private DefaultTableModel modelTablePatient;
    private TableColumn colonneTablePatient;
    private String nom, prenom, numeroSecuriteSociale, dateNaissance;
    private int ligneModelTablePatient, mouseX, mouseY;
    private Boolean verrouiller;
    private ImageIcon logo;
    
    public Agent_Administration(Boolean verrouiller) {
        
        //La variable 'verrouiller' permet de désactiver les listeners lorsqu'une fenêtre de dialogue est ouverte par-dessus.
        this.verrouiller = verrouiller;

        pagePrincipal = new JFrame();

        panelPrincipal = new JPanel();
        panelSecondaire = new JPanel();

        logo = new ImageIcon("lib/logo.png");

        if (!verrouiller) {
            panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
			    public void mouseDragged(MouseEvent e) {
				    pagePrincipal.setLocation(pagePrincipal.getX() + e.getX() - mouseX, pagePrincipal.getY() + e.getY() - mouseY);
			    }
		    });

		    panelPrincipal.addMouseListener(new MouseAdapter() {
			    public void mousePressed(MouseEvent e) {
				    mouseX = e.getX();
				    mouseY = e.getY();
			    }
		    });
        }

        boutonRetour = new JButton();
        if (!verrouiller) {
            boutonRetour.addActionListener(new BoutonRetour());
        }

        boutonAjouter = new JButton("Ajouter");
        if (!verrouiller) {
            boutonAjouter.addActionListener(new BoutonAjouter());
        }

        boutonRechercher = new JButton("Rechercher");
        if (!verrouiller) {
            boutonRechercher.addActionListener(new BoutonRechercher());
        }

        boutonVisualiser = new JButton();
        boutonModifier = new JButton();
        boutonSupprimer = new JButton();

        labelUserType = new JLabel("Agent d'Administration");
        
        //Le tableau pour afficher les patients est instancié avant d'être affiché.
        instanciationTableauPatient();
        fenetrePrincipale();
    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetrePrincipale() {        
        boutonRetour.setBounds(10,10,25,25);  
        boutonRetour.setBackground(new Color(166, 58, 80, 255));      
        boutonAjouter.setBounds(45,10,140,50);
        boutonAjouter.setBackground(new Color(215, 175, 112, 255));
        labelUserType.setBounds(360,10,150,25);
        labelUserType.setForeground(new Color(215, 175, 112, 255));
        boutonRechercher.setBounds(700,10,140,50);
        boutonRechercher.setBackground(new Color(215, 175, 112, 255));
        tableauPatient.setPreferredScrollableViewportSize(new Dimension(775, 318));
        panelPrincipal.setLayout(null);
        panelPrincipal.setBounds(0,0,850,445);
        panelPrincipal.setBackground(new Color(51, 101, 138, 255));
        panelPrincipal.add(boutonRetour);
        panelPrincipal.add(boutonAjouter);
        panelPrincipal.add(boutonRechercher);
        panelPrincipal.add(labelUserType);
        panelPrincipal.add(panelSecondaire);
        panelSecondaire.setBounds(27,80,796,345);
        panelSecondaire.setBackground(new Color(51, 101, 138, 255));
        panelSecondaire.add(scrollPanePatient);
        pagePrincipal.add(panelPrincipal);
        pagePrincipal.setSize(850,445);
        pagePrincipal.setTitle("GesPat : Gestion d'Appareillage Médical");
        pagePrincipal.setIconImage(logo.getImage());
        pagePrincipal.setLayout(null);
        pagePrincipal.setUndecorated(true);
        pagePrincipal.setResizable(false);
        pagePrincipal.setLocationRelativeTo(null);
        pagePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pagePrincipal.setVisible(true);
    }

    //Création d'un tableau et de ses paramètres.
    private void instanciationTableauPatient() {
        modelTablePatient = new DefaultTableModel(new String[][] {}, new String[] { "Nom", "Prénom", "Numéro de Sécurité Sociale", "Date de Naissance", "Visualiser", "Modifier", "Supprimer" } ) {

            boolean[] canEdit = new boolean[] { false, false, false, false, true, true, true };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        //Lecture du fichier patient pour disposer les données dans le tableau.
        lecturePatientTXT();
        tableauPatient = new JTable(modelTablePatient);
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableauPatient.getModel());
        
        tableauPatient.setFillsViewportHeight(true);
        tableauPatient.setBackground(new Color(215, 175, 112, 255));

        tableauPatient.setRowSorter(sorter);
        colonneTablePatient = tableauPatient.getColumnModel().getColumn(0);
        colonneTablePatient.setMinWidth(140);
        colonneTablePatient.setMaxWidth(140);
        colonneTablePatient = tableauPatient.getColumnModel().getColumn(1);
        colonneTablePatient.setMinWidth(140);
        colonneTablePatient.setMaxWidth(140);
        colonneTablePatient = tableauPatient.getColumnModel().getColumn(2);
        colonneTablePatient.setMinWidth(165);
        colonneTablePatient.setMaxWidth(165);
        colonneTablePatient = tableauPatient.getColumnModel().getColumn(3);
        colonneTablePatient.setMinWidth(120);
        colonneTablePatient.setMaxWidth(120);
        colonneTablePatient = tableauPatient.getColumnModel().getColumn(4);
        colonneTablePatient.setMinWidth(70);
        colonneTablePatient.setMaxWidth(70);
        colonneTablePatient = tableauPatient.getColumnModel().getColumn(5);
        colonneTablePatient.setMinWidth(70);
        colonneTablePatient.setMaxWidth(70);
        centrerTextCelulle = new DefaultTableCellRenderer();
        centrerTextCelulle.setHorizontalAlignment(JLabel.CENTER);
        tableauPatient.getColumnModel().getColumn(2).setCellRenderer(centrerTextCelulle);
        tableauPatient.getColumnModel().getColumn(3).setCellRenderer(centrerTextCelulle);
        tableauPatient.getTableHeader().setReorderingAllowed(false);
        tableauPatient.getColumn("Visualiser").setCellRenderer(new VisualiserButtonRenderer());
        tableauPatient.getColumn("Visualiser").setCellEditor(new VisualiserButtonEditor(new JCheckBox()));
        if (!verrouiller) {
            boutonVisualiser.addActionListener(new BoutonVisualiser());
        }
        tableauPatient.getColumn("Modifier").setCellRenderer(new ModifierButtonRenderer());
        tableauPatient.getColumn("Modifier").setCellEditor(new ModifierButtonEditor(new JCheckBox()));
        if (!verrouiller) {
            boutonModifier.addActionListener(new BoutonModifier());
        }
        tableauPatient.getColumn("Supprimer").setCellRenderer(new SupprimerButtonRenderer());
        tableauPatient.getColumn("Supprimer").setCellEditor(new SupprimerButtonEditor(new JCheckBox()));
        if (!verrouiller) {
            boutonSupprimer.addActionListener(new BoutonSupprimer());
        }
        scrollPanePatient = new JScrollPane(tableauPatient);
        scrollPanePatient.setBackground(new Color(215, 175, 112, 255));
        
        scrollPanePatient.getVerticalScrollBar().setUI(new BasicScrollBarUI()
        {   
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }
    
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }
    
            private JButton createZeroButton() {
                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                jbutton.setMinimumSize(new Dimension(0, 0));
                jbutton.setMaximumSize(new Dimension(0, 0));
                return jbutton;
            }

            protected void paintThumb (Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D) g.create();
                Color color = new Color(51, 101, 138, 255);               
                g2.setPaint(color);
                g2.fillRect(r.x,r.y,r.width,r.height);
                g2.dispose();
            }
            
            protected void paintTrack (Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D) g.create();
                Color color = new Color(215, 175, 112, 255);                
                g2.setPaint(color);
                g2.fillRect(r.x,r.y,r.width,r.height);
                g2.dispose();
            }
        });
        tableauPatient.getTableHeader().setBackground(new Color(215, 175, 112, 255));
        tableauPatient.setGridColor(new Color(51, 101, 138, 255));
    }

    /*Le fichier patient est lu ligne par ligne, toutes les données sont séparer dès la lecture du caractère ';' pour ensuite répartir 
    les données dans chaque cellules du tableauPatient.*/
    private void lecturePatientTXT() {
        try (BufferedReader lectureFichierPatient = new BufferedReader(new InputStreamReader(new FileInputStream("lib/patient.txt"), "UTF-8"))) {
            
            Object[] tableLines = lectureFichierPatient.lines().toArray();
            
            for (int i = 0; i < tableLines.length; i++){
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split(";");
                modelTablePatient.addRow(dataRow);
            }

            lectureFichierPatient.close();

        } catch (IOException ioe) {
            File fichierPatient = new File("lib/patient.txt");
            try {
                fichierPatient.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Mise en place de bouton personnalisés dans les cellules de tableauPatient.
    private class VisualiserButtonRenderer extends JButton implements TableCellRenderer {
        
        public VisualiserButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(215, 175, 112, 255));
        }
        
        public VisualiserButtonRenderer getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Voir" : value.toString());
            return this;
        }
    }
    
    private class VisualiserButtonEditor extends DefaultCellEditor {
        
        private String label;
    
        public VisualiserButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public JButton getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "Voir" : value.toString();
            boutonVisualiser.setText(label);
            return boutonVisualiser;
        }

        public Object getCellEditorValue() {
            return new String(label);
        }
    }

    private class ModifierButtonRenderer extends JButton implements TableCellRenderer {
        
        public ModifierButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(215, 175, 112, 255));
        }
        
        public ModifierButtonRenderer getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Modif" : value.toString());
            return this;
        }
    }

    private class ModifierButtonEditor extends DefaultCellEditor {
        
        private String label;
    
        public ModifierButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public JButton getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "Modif" : value.toString();
            boutonModifier.setText(label);
            return boutonModifier;
        }

        public Object getCellEditorValue() {
            return new String(label);
        }
    }

    private class SupprimerButtonRenderer extends JButton implements TableCellRenderer {
        
        public SupprimerButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(215, 175, 112, 255));
        }
        
        public SupprimerButtonRenderer getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Suppr" : value.toString());
            return this;
        }
    }

    private class SupprimerButtonEditor extends DefaultCellEditor {
        
        private String label;
    
        public SupprimerButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public JButton getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "Suppr" : value.toString();
            boutonSupprimer.setText(label);
            return boutonSupprimer;
        }

        public Object getCellEditorValue() {
            return new String(label);
        }
    }

    //Attribution aux String des valeurs appartenant à la ligne sélectionnée par l'utilisateur.
    private void stringInstanciationViaLigneTableau(int ligneModelTablePatient) {
        nom = (String) modelTablePatient.getValueAt(ligneModelTablePatient,0);
        prenom = (String) modelTablePatient.getValueAt(ligneModelTablePatient,1);
        numeroSecuriteSociale = (String) modelTablePatient.getValueAt(ligneModelTablePatient,2);
        dateNaissance = (String) modelTablePatient.getValueAt(ligneModelTablePatient,3);
        this.ligneModelTablePatient = ligneModelTablePatient;
    }

    //Définition des listeners de chaque bouton ainsi que des informations à transmettre aux autres classes.
    protected class BoutonVisualiser implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            /*Définis la ligne sélectionner par l'utilisateur en recherchant dans le modèle du tableau à quelle ligne exacte, elle appartient.
                Cela peut être utile, car si nous ajoutons des filtres de recherche au tableau, la ligne sélectionnée par l'utilisateur sera peut-être
            différente de sa valeur dans le modèle du tableau qui elle est 'égal'(Suivant l'opération) a la ligne dans le fichier.
                La conversion est donc nécessaire pour par la suite pouvoir agir sur les données stockées dans le fichier en question via une interface.*/
            ligneModelTablePatient = tableauPatient.convertRowIndexToModel(tableauPatient.getSelectedRow());
            stringInstanciationViaLigneTableau(ligneModelTablePatient);
            //Transmissions des variables instanciées à la classe de relève.
            new Fenetre_Visualiser(nom, prenom, numeroSecuriteSociale, dateNaissance, ligneModelTablePatient);
            pagePrincipal.dispose();
        }
    }

    protected class BoutonModifier implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            ligneModelTablePatient = tableauPatient.convertRowIndexToModel(tableauPatient.getSelectedRow());
            stringInstanciationViaLigneTableau(ligneModelTablePatient);
            new Fenetre_Modifier(nom, prenom, numeroSecuriteSociale, dateNaissance, ligneModelTablePatient);
            pagePrincipal.dispose();
        }
    }

    protected class BoutonSupprimer implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            ligneModelTablePatient = tableauPatient.convertRowIndexToModel(tableauPatient.getSelectedRow());
            stringInstanciationViaLigneTableau(ligneModelTablePatient);
            new Fenetre_Supprimer(nom, prenom, numeroSecuriteSociale, dateNaissance, ligneModelTablePatient);
            pagePrincipal.dispose();
        }
    }
    
    protected class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Menu();
            pagePrincipal.dispose();
        }
    }

    protected class BoutonAjouter implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Ajouter();
            pagePrincipal.dispose();
        }
    }

    protected class BoutonRechercher implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Rechercher();
            pagePrincipal.dispose();
        }
    }
}