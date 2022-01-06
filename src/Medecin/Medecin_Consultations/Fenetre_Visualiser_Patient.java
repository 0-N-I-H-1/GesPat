package Medecin.Medecin_Consultations;
import Medecin.Medecin;
import Medecin.Medecin_Consultations.Visualisation.Fenetre_Visualiser;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableCellRenderer;
import javax.swing.DefaultCellEditor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;
import java.awt.Graphics2D;

public class Fenetre_Visualiser_Patient {
    
    protected JFrame pageVisualiserPatient;
    protected static JPanel panelPrincipal, panelSecondaire, panelTableau;
    protected static JButton boutonRetour, boutonVisualiser, boutonModifier, boutonSupprimer, boutonAjouter,  boutonRechercher;
    protected JTable tableauConsultations;
    private JScrollPane scrollPaneConsultations;
    private DefaultTableCellRenderer centrerTextCelulle;
    private DefaultTableModel modelTableConsultations;
    private TableColumn colonneTableConsultations;
    private JLabel labelUserType, labelConsultations, labelNom, labelPrenom, labelNumeroSecuriteSociale, labelDateNaissance, labelInfo;
    protected String nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut;
    private int ligneModelTableConsultations, mouseX, mouseY;
    private ImageIcon logo;
    private Boolean verrouiller;

    public Fenetre_Visualiser_Patient(String nom, String prenom, String numeroSecuriteSociale, String dateNaissance, Boolean verrouiller) {

        //La variable 'verrouiller' permet de désactiver les listeners lorsqu'une fenêtre de dialogue est ouverte par-dessus.
        this.verrouiller = verrouiller;

        pageVisualiserPatient = new JFrame();

        panelPrincipal = new JPanel();
        panelSecondaire = new JPanel();
        panelTableau = new JPanel();

        logo = new ImageIcon("lib/logo.png");

        if (!verrouiller) {
            panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
			    public void mouseDragged(MouseEvent e) {
				    pageVisualiserPatient.setLocation(pageVisualiserPatient.getX() + e.getX() - mouseX, pageVisualiserPatient.getY() + e.getY() - mouseY);
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

        labelUserType = new JLabel("Medecin");
        labelConsultations = new JLabel("Les Consultations du Patient :");
        labelNom = new JLabel("Nom : " + nom);
        labelPrenom = new JLabel("Prénom : " + prenom);
        labelNumeroSecuriteSociale = new JLabel("<html><div align=left>Numéro Sécurité Sociale : <br><div align=center>" + numeroSecuriteSociale + "</html>");
        labelDateNaissance = new JLabel("<html><div align=left>Date de Naissance : <br><div align=center>" + dateNaissance + "</html>");
        labelInfo = new JLabel("<html><div align=center>*Ici, vous pouvez ajouter ou rechercher<br>une consultation pour ce patient.</html>");

        this.nom = nom;
        this.prenom = prenom;
        this.numeroSecuriteSociale = numeroSecuriteSociale;
        this.dateNaissance = dateNaissance;

        //Le tableau pour afficher les consultations est instancié avant d'être affiché.
        instanciationTableauConsultations();
        fenetreVisualiserPatient();

    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetreVisualiserPatient() {        
        boutonRetour.setBounds(10,10,25,25);
        boutonRetour.setBackground(new Color(166, 58, 80, 255));
        boutonAjouter.setBounds(25,200,165,50);
        boutonAjouter.setBackground(new Color(215, 175, 112, 255));
        boutonRechercher.setBounds(25,288,165,50);
        boutonRechercher.setBackground(new Color(215, 175, 112, 255));
        labelUserType.setBounds(400,10,150,25);
        labelUserType.setForeground(new Color(215, 175, 112, 255));
        labelConsultations.setBounds(25,5,200,25);
        labelConsultations.setForeground(new Color(215, 175, 112, 255));
        labelNom.setBounds(25,40,300,30);
        labelNom.setForeground(new Color(215, 175, 112, 255));
        labelPrenom.setBounds(25,80,300,30);
        labelPrenom.setForeground(new Color(215, 175, 112, 255));
        labelNumeroSecuriteSociale.setBounds(25,120,300,30);
        labelNumeroSecuriteSociale.setForeground(new Color(215, 175, 112, 255));
        labelDateNaissance.setBounds(25,160,300,30);
        labelDateNaissance.setForeground(new Color(215, 175, 112, 255));
        labelInfo.setFont(new Font("Dialog",Font.BOLD,9));
        labelInfo.setBounds(15,252,200,30);
        labelInfo.setForeground(new Color(215, 175, 112, 255));
        tableauConsultations.setPreferredScrollableViewportSize(new Dimension(580, 310));
        panelPrincipal.setLayout(null);
        panelPrincipal.setBounds(0,0,850,445);
        panelPrincipal.setBackground(new Color(51, 101, 138, 255));
        panelPrincipal.add(boutonRetour);
        panelPrincipal.add(labelUserType);
        panelPrincipal.add(panelSecondaire);
        panelSecondaire.setLayout(null);
        panelSecondaire.setBounds(27,80,796,345);
        panelSecondaire.setBackground(new Color(51, 101, 138, 255));
        panelSecondaire.add(labelConsultations);
        panelSecondaire.add(labelNom);
        panelSecondaire.add(labelPrenom);
        panelSecondaire.add(labelNumeroSecuriteSociale);
        panelSecondaire.add(labelDateNaissance);
        panelSecondaire.add(labelInfo);
        panelSecondaire.add(boutonAjouter);
        panelSecondaire.add(boutonRechercher);
        panelSecondaire.add(panelTableau);
        panelTableau.setBounds(195,0,600,345);
        panelTableau.setBackground(new Color(51, 101, 138, 255));
        panelTableau.add(scrollPaneConsultations);        
        pageVisualiserPatient.add(panelPrincipal);
        pageVisualiserPatient.setSize(850,445);
        pageVisualiserPatient.setTitle("GesPat : Gestion d'Appareillage Médical");
        pageVisualiserPatient.setIconImage(logo.getImage());
        pageVisualiserPatient.setLayout(null);
        pageVisualiserPatient.setUndecorated(true);
        pageVisualiserPatient.setResizable(false);
        pageVisualiserPatient.setLocationRelativeTo(null);
        pageVisualiserPatient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pageVisualiserPatient.setVisible(true);
    }

    //Création d'un tableau et de ses paramètres.
    private void instanciationTableauConsultations() {
        modelTableConsultations = new DefaultTableModel(new String[][] {}, new String[] { "Nom", "Prénom", "Numéro de Sécurité Sociale", "Date de Naissance", "Nom du Medecin", "Date de Consultation", "Pathologies", "Appareillage", "Statut", "Visualiser", "Modifier", "Supprimer" } ) {

            boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false, false, true, true, true };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        //Lecture du fichier consultations pour disposer les données dans le tableau.
        lectureConsultationsTXT();
        tableauConsultations = new JTable(modelTableConsultations);
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableauConsultations.getModel());
        
        tableauConsultations.setFillsViewportHeight(true);
        tableauConsultations.setBackground(new Color(215, 175, 112, 255));

        tableauConsultations.setRowSorter(sorter);
        colonneTableConsultations = tableauConsultations.getColumnModel().getColumn(0);
        colonneTableConsultations.setMinWidth(100);
        colonneTableConsultations.setMaxWidth(100);
        colonneTableConsultations = tableauConsultations.getColumnModel().getColumn(1);
        colonneTableConsultations.setMinWidth(0);
        colonneTableConsultations.setMaxWidth(0);
        colonneTableConsultations = tableauConsultations.getColumnModel().getColumn(2);
        colonneTableConsultations.setMinWidth(0);
        colonneTableConsultations.setMaxWidth(0);
        colonneTableConsultations = tableauConsultations.getColumnModel().getColumn(3);
        colonneTableConsultations.setMinWidth(0);
        colonneTableConsultations.setMaxWidth(0);
        colonneTableConsultations = tableauConsultations.getColumnModel().getColumn(4);
        colonneTableConsultations.setMinWidth(120);
        colonneTableConsultations.setMaxWidth(120);
        colonneTableConsultations = tableauConsultations.getColumnModel().getColumn(5);
        colonneTableConsultations.setMinWidth(140);
        colonneTableConsultations.setMaxWidth(140);
        colonneTableConsultations = tableauConsultations.getColumnModel().getColumn(6);
        colonneTableConsultations.setMinWidth(0);
        colonneTableConsultations.setMaxWidth(0);
        colonneTableConsultations = tableauConsultations.getColumnModel().getColumn(7);
        colonneTableConsultations.setMinWidth(0);
        colonneTableConsultations.setMaxWidth(0);
        colonneTableConsultations = tableauConsultations.getColumnModel().getColumn(8);
        colonneTableConsultations.setMinWidth(0);
        colonneTableConsultations.setMaxWidth(0);
        colonneTableConsultations = tableauConsultations.getColumnModel().getColumn(9);
        colonneTableConsultations.setMinWidth(70);
        colonneTableConsultations.setMaxWidth(70);
        colonneTableConsultations = tableauConsultations.getColumnModel().getColumn(10);
        colonneTableConsultations.setMinWidth(70);
        colonneTableConsultations.setMaxWidth(70);
        centrerTextCelulle = new DefaultTableCellRenderer();
        centrerTextCelulle.setHorizontalAlignment(JLabel.CENTER);
        tableauConsultations.getColumnModel().getColumn(5).setCellRenderer(centrerTextCelulle);
        tableauConsultations.getTableHeader().setReorderingAllowed(false);
        tableauConsultations.getColumn("Visualiser").setCellRenderer(new VisualiserButtonRenderer());
        tableauConsultations.getColumn("Visualiser").setCellEditor(new VisualiserButtonEditor(new JCheckBox()));
        if (!verrouiller) {
            boutonVisualiser.addActionListener(new BoutonVisualiser());
        }
        tableauConsultations.getColumn("Modifier").setCellRenderer(new ModifierButtonRenderer());
        tableauConsultations.getColumn("Modifier").setCellEditor(new ModifierButtonEditor(new JCheckBox()));
        if (!verrouiller) {
            boutonModifier.addActionListener(new BoutonModifier());
        }
        tableauConsultations.getColumn("Supprimer").setCellRenderer(new SupprimerButtonRenderer());
        tableauConsultations.getColumn("Supprimer").setCellEditor(new SupprimerButtonEditor(new JCheckBox()));
        if (!verrouiller) {
            boutonSupprimer.addActionListener(new BoutonSupprimer());
        }
        scrollPaneConsultations = new JScrollPane(tableauConsultations);
        scrollPaneConsultations.setBackground(new Color(215, 175, 112, 255));
        
        scrollPaneConsultations.getVerticalScrollBar().setUI(new BasicScrollBarUI()
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
        tableauConsultations.getTableHeader().setBackground(new Color(215, 175, 112, 255));
        tableauConsultations.setGridColor(new Color(51, 101, 138, 255));
        filtrerTableau();
    }

    /*Le fichier consultations est lu ligne par ligne, toutes les données sont séparer dès la lecture du caractère ';' pour ensuite répartir 
    les données dans chaque cellules du tableauPatient.*/
    private void lectureConsultationsTXT() {
        try (BufferedReader lectureFichierConsultations = new BufferedReader(new InputStreamReader(new FileInputStream("lib/consultations.txt"), "UTF-8"))) {
            
            Object[] tableLines = lectureFichierConsultations.lines().toArray();
            
            for (int i = 0; i < tableLines.length; i++){
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split(";");
                modelTableConsultations.addRow(dataRow);
            }

            lectureFichierConsultations.close();

        } catch (IOException ioe) {
            File fichierConsultations = new File("lib/consultations.txt");
            try {
                fichierConsultations.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Le tableau est filtré pour que les consultations du patient appelé uniquement et uniquement les siennes apparaissent sur l'interface utilisateur.
    private void filtrerTableau() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableauConsultations.getModel());

        tableauConsultations.setRowSorter(sorter);
    
        List<RowFilter<Object,Object>> filtres = new ArrayList<RowFilter<Object,Object>>(4);

        RowFilter<TableModel, Object> rowfilter = RowFilter.andFilter(filtres);

        sorter.setRowFilter(rowfilter);

        filtres.add(RowFilter.regexFilter(nom,0));
        filtres.add(RowFilter.regexFilter(prenom,1));
        filtres.add(RowFilter.regexFilter(numeroSecuriteSociale,2));
        filtres.add(RowFilter.regexFilter(dateNaissance,3));
        sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));
    }

    //Mise en place de bouton personnalisés dans les cellules de tableauConsultations.
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
    private void stringInstanciationViaLigneTableau(int ligneModelTableConsultations) {
        nom = (String) modelTableConsultations.getValueAt(ligneModelTableConsultations,0);
        prenom = (String) modelTableConsultations.getValueAt(ligneModelTableConsultations,1);
        numeroSecuriteSociale = (String) modelTableConsultations.getValueAt(ligneModelTableConsultations,2);
        dateNaissance = (String) modelTableConsultations.getValueAt(ligneModelTableConsultations,3);
        nomMedecin = (String) modelTableConsultations.getValueAt(ligneModelTableConsultations,4);
        dateConsultation = (String) modelTableConsultations.getValueAt(ligneModelTableConsultations,5);
        pathologies = (String) modelTableConsultations.getValueAt(ligneModelTableConsultations,6);
        appareillage = (String) modelTableConsultations.getValueAt(ligneModelTableConsultations,7);
        statut = (String) modelTableConsultations.getValueAt(ligneModelTableConsultations,8);
        this.ligneModelTableConsultations = ligneModelTableConsultations;
        //Ici, nous restituons les retours à la ligne pour que l'utilisateur consulte les données telles qu'elles furent écrites.
        pathologies = pathologies.replaceAll("<br>", "\n");
    }

    //Définition des listeners de chaque bouton ainsi que des informations à transmettre aux autres classes.
    protected class BoutonVisualiser implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            /*Définis la ligne sélectionner par l'utilisateur en recherchant dans le modèle du tableau à quelle ligne exacte, elle appartient.
                Cela peut être utile, car si nous ajoutons des filtres de recherche au tableau, la ligne sélectionnée par l'utilisateur sera peut-être
            différente de sa valeur dans le modèle du tableau qui elle est 'égal'(Suivant l'opération) a la ligne dans le fichier.
                La conversion est donc nécessaire pour par la suite pouvoir agir sur les données stockées dans le fichier en question via une interface.*/
            ligneModelTableConsultations = tableauConsultations.convertRowIndexToModel(tableauConsultations.getSelectedRow());
            stringInstanciationViaLigneTableau(ligneModelTableConsultations);
            //Transmissions des variables instanciées à la classe de relève.
            new Fenetre_Visualiser(nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut, ligneModelTableConsultations, false);
            pageVisualiserPatient.dispose();
        }
    }

    protected class BoutonModifier implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            ligneModelTableConsultations = tableauConsultations.convertRowIndexToModel(tableauConsultations.getSelectedRow());
            stringInstanciationViaLigneTableau(ligneModelTableConsultations);
            new Fenetre_Modifier(nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut, ligneModelTableConsultations);
            pageVisualiserPatient.dispose();
        }
    }

    protected class BoutonSupprimer implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            ligneModelTableConsultations = tableauConsultations.convertRowIndexToModel(tableauConsultations.getSelectedRow());
            stringInstanciationViaLigneTableau(ligneModelTableConsultations);
            new Fenetre_Supprimer(nom, prenom, numeroSecuriteSociale, dateNaissance, ligneModelTableConsultations);
            pageVisualiserPatient.dispose();
        }
    }

    protected class BoutonAjouter implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Ajouter(nom, prenom, numeroSecuriteSociale, dateNaissance);
            pageVisualiserPatient.dispose();
        }
    }

    protected class BoutonRechercher implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Rechercher(nom, prenom, numeroSecuriteSociale, dateNaissance);
            pageVisualiserPatient.dispose();
        }
    }

    protected class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Medecin(false);
            pageVisualiserPatient.dispose();
        }
    }
}