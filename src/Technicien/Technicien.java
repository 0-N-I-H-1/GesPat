package Technicien;
import Menu.Menu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
import javax.swing.RowFilter;
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

public class Technicien {

    protected JFrame pagePrincipal;
    protected static JPanel panelPrincipal, panelSecondaire;
    protected static JButton boutonRetour, boutonRechercher, boutonVisualiser;
    private JLabel labelUserType, labelInfo;
    protected static JTable tableauConsultations;
    private JScrollPane scrollPaneConsultations;
    private DefaultTableCellRenderer centrerTextCelulle;
    private DefaultTableModel modelTableConsultations;
    private TableColumn colonneTableConsultations;
    private int ligneModelTableConsultations, mouseX, mouseY;
    private Boolean verrouiller;
    private ImageIcon logo;
    protected static TableRowSorter<TableModel> sorter;
    protected static List<RowFilter<Object,Object>> filtres;
    protected static RowFilter<TableModel, Object> rowfilter;
    private String nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut;
    protected static JCheckBox checkBoxVoirLesOctrois;
    
    public Technicien(Boolean verrouiller) {
        
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

        boutonRechercher = new JButton("Rechercher");
        if (!verrouiller) {
            boutonRechercher.addActionListener(new BoutonRechercher());
        }

        boutonVisualiser = new JButton();

        labelUserType = new JLabel("Technicien");
        labelInfo = new JLabel("*Cocher cette case si vous souhaitez voir les consultations dont les appareils médicaux ont été octroyer :");

        checkBoxVoirLesOctrois = new JCheckBox();
        if (!verrouiller) {
            checkBoxVoirLesOctrois.addActionListener(new CheckBoxVoirLesOctrois());
        }

        //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
        instanciationTableauConsultations();
        filtrerTableau();
        fenetrePrincipale();
        
    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetrePrincipale() {        
        boutonRetour.setBounds(10,10,25,25);  
        boutonRetour.setBackground(new Color(166, 58, 80, 255));      
        labelUserType.setBounds(394,10,150,25);
        labelUserType.setForeground(new Color(215, 175, 112, 255));
        boutonRechercher.setBounds(700,10,140,50);
        boutonRechercher.setBackground(new Color(215, 175, 112, 255));
        tableauConsultations.setPreferredScrollableViewportSize(new Dimension(775, 318));   
        checkBoxVoirLesOctrois.setBounds(640,45,20,20);
        checkBoxVoirLesOctrois.setBackground(new Color(51, 101, 138, 255));
        labelInfo.setBounds(45,30,600,50);
        labelInfo.setForeground(new Color(215, 175, 112, 255));
        panelPrincipal.setLayout(null);
        panelPrincipal.setBounds(0,0,850,445);
        panelPrincipal.setBackground(new Color(51, 101, 138, 255));
        panelPrincipal.add(boutonRetour);
        panelPrincipal.add(boutonRechercher);
        panelPrincipal.add(labelUserType);
        panelPrincipal.add(panelSecondaire);
        panelPrincipal.add(checkBoxVoirLesOctrois);
        panelPrincipal.add(labelInfo);
        panelSecondaire.setBounds(27,80,796,345);
        panelSecondaire.setBackground(new Color(51, 101, 138, 255));
        panelSecondaire.add(scrollPaneConsultations);
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
    private void instanciationTableauConsultations() {
        modelTableConsultations = new DefaultTableModel(new String[][] {}, new String[] { "Nom", "Prénom", "Numéro de Sécurité Sociale", "Date de Naissance", "Nom du Medecin", "Date de Consultation", "Pathologies", "Appareillage", "Statut", "Accéder à la consultation du patient" } ) {

            boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false, false, true };

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
        colonneTableConsultations.setMinWidth(192);
        colonneTableConsultations.setMaxWidth(192);
        colonneTableConsultations = tableauConsultations.getColumnModel().getColumn(1);
        colonneTableConsultations.setMinWidth(192);
        colonneTableConsultations.setMaxWidth(192);
        colonneTableConsultations = tableauConsultations.getColumnModel().getColumn(2);
        colonneTableConsultations.setMinWidth(0);
        colonneTableConsultations.setMaxWidth(0);
        colonneTableConsultations = tableauConsultations.getColumnModel().getColumn(3);
        colonneTableConsultations.setMinWidth(0);
        colonneTableConsultations.setMaxWidth(0);
        colonneTableConsultations = tableauConsultations.getColumnModel().getColumn(4);
        colonneTableConsultations.setMinWidth(0);
        colonneTableConsultations.setMaxWidth(0);
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
        centrerTextCelulle = new DefaultTableCellRenderer();
        centrerTextCelulle.setHorizontalAlignment(JLabel.CENTER);
        tableauConsultations.getColumnModel().getColumn(5).setCellRenderer(centrerTextCelulle);
        tableauConsultations.getTableHeader().setReorderingAllowed(false);
        tableauConsultations.getColumn("Accéder à la consultation du patient").setCellRenderer(new VisualiserButtonRenderer());
        tableauConsultations.getColumn("Accéder à la consultation du patient").setCellEditor(new VisualiserButtonEditor(new JCheckBox()));
        if (!verrouiller) {
            boutonVisualiser.addActionListener(new BoutonVisualiser());
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

    //Le tableau est filtré pour que les consultations affiché soient uniquement celles qui demandent un octroie ou celles qui sont déjà octroyer.
    private void filtrerTableau() {
        if (checkBoxVoirLesOctrois.isSelected()) {
            sorter = new TableRowSorter<TableModel>(tableauConsultations.getModel());
            tableauConsultations.setRowSorter(sorter);
            filtres = new ArrayList<RowFilter<Object,Object>>(1);
            rowfilter = RowFilter.andFilter(filtres);
            sorter.setRowFilter(rowfilter);
            filtres.add(RowFilter.regexFilter("Octroyer",8));
            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));
        } else {
            sorter = new TableRowSorter<TableModel>(tableauConsultations.getModel());
            tableauConsultations.setRowSorter(sorter);
            filtres = new ArrayList<RowFilter<Object,Object>>(1);
            rowfilter = RowFilter.andFilter(filtres);
            sorter.setRowFilter(rowfilter);
            filtres.add(RowFilter.regexFilter("Octroies en attente.",8));
            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));
        }        
    }

    //Mise en place de bouton personnalisés dans les cellules de tableauConsultations.
    private class VisualiserButtonRenderer extends JButton implements TableCellRenderer {
        
        public VisualiserButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(215, 175, 112, 255));
        }
        
        public VisualiserButtonRenderer getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Gérer cette Consultation" : value.toString());
            return this;
        }
    }
    
    private class VisualiserButtonEditor extends DefaultCellEditor {
        
        private String label;
    
        public VisualiserButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public JButton getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "Gérer cette Consultation" : value.toString();
            boutonVisualiser.setText(label);
            return boutonVisualiser;
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
    }

    //Définition des listeners de chaque élément ainsi que des informations à transmettre aux autres classes.
    protected class CheckBoxVoirLesOctrois implements ActionListener {
        public void actionPerformed(ActionEvent Event) {           
            filtrerTableau();
        }
    }

    protected class BoutonVisualiser implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            if (checkBoxVoirLesOctrois.isSelected()) {
                ligneModelTableConsultations = tableauConsultations.convertRowIndexToModel(tableauConsultations.getSelectedRow());
                stringInstanciationViaLigneTableau(ligneModelTableConsultations);
                new Fenetre_Visualiser(nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut, ligneModelTableConsultations, false, false);
                pagePrincipal.dispose();
            } else {
                ligneModelTableConsultations = tableauConsultations.convertRowIndexToModel(tableauConsultations.getSelectedRow());
                stringInstanciationViaLigneTableau(ligneModelTableConsultations);
                new Fenetre_Visualiser(nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut, ligneModelTableConsultations, false, true);
                pagePrincipal.dispose();
            }
        }
    }
    
    protected class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Menu();
            pagePrincipal.dispose();
        }
    }
    
    protected class BoutonRechercher implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            if (checkBoxVoirLesOctrois.isSelected()) {
                new Fenetre_Rechercher(true);
                pagePrincipal.dispose();
            } else {
                new Fenetre_Rechercher(false);
                pagePrincipal.dispose();
            }
        }
    }
}