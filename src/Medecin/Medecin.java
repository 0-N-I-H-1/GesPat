package Medecin;
import Menu.Menu;
import Medecin.Medecin_Consultations.Fenetre_Visualiser_Patient;
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

public class Medecin {

    protected JFrame pagePrincipal;
    protected static JPanel panelPrincipal, panelSecondaire;
    protected static JButton boutonRetour, boutonRechercher, boutonVisualiser;
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
    
    public Medecin(Boolean verrouiller) {
        
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

        labelUserType = new JLabel("Medecin");

        //Le tableau pour afficher les patients est instancié avant d'être affiché.
        instanciationTableauPatient();
        fenetrePrincipale();
        
    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetrePrincipale() {        
        boutonRetour.setBounds(10,10,25,25);  
        boutonRetour.setBackground(new Color(166, 58, 80, 255));      
        labelUserType.setBounds(400,10,150,25);
        labelUserType.setForeground(new Color(215, 175, 112, 255));
        boutonRechercher.setBounds(700,10,140,50);
        boutonRechercher.setBackground(new Color(215, 175, 112, 255));
        tableauPatient.setPreferredScrollableViewportSize(new Dimension(775, 318));
        panelPrincipal.setLayout(null);
        panelPrincipal.setBounds(0,0,850,445);
        panelPrincipal.setBackground(new Color(51, 101, 138, 255));
        panelPrincipal.add(boutonRetour);
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
        modelTablePatient = new DefaultTableModel(new String[][] {}, new String[] { "Nom", "Prénom", "Numéro de Sécurité Sociale", "Date de Naissance", "Accéder au dossier du patient" } ) {

            boolean[] canEdit = new boolean[] { false, false, false, false, true };

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
        centrerTextCelulle = new DefaultTableCellRenderer();
        centrerTextCelulle.setHorizontalAlignment(JLabel.CENTER);
        tableauPatient.getColumnModel().getColumn(2).setCellRenderer(centrerTextCelulle);
        tableauPatient.getColumnModel().getColumn(3).setCellRenderer(centrerTextCelulle);
        tableauPatient.getTableHeader().setReorderingAllowed(false);
        tableauPatient.getColumn("Accéder au dossier du patient").setCellRenderer(new VisualiserButtonRenderer());
        tableauPatient.getColumn("Accéder au dossier du patient").setCellEditor(new VisualiserButtonEditor(new JCheckBox()));
        if (!verrouiller) {
            boutonVisualiser.addActionListener(new BoutonVisualiser());
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

    //Mise en place d'un bouton personnalisés dans les cellules de tableauPatient.
    private class VisualiserButtonRenderer extends JButton implements TableCellRenderer {
        
        public VisualiserButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(215, 175, 112, 255));
        }
        
        public VisualiserButtonRenderer getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Gérer les Consultations" : value.toString());
            return this;
        }
    }
    
    private class VisualiserButtonEditor extends DefaultCellEditor {
        
        private String label;
    
        public VisualiserButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public JButton getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "Gérer les Consultations" : value.toString();
            boutonVisualiser.setText(label);
            return boutonVisualiser;
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
            new Fenetre_Visualiser_Patient(nom, prenom, numeroSecuriteSociale, dateNaissance, false);
            pagePrincipal.dispose();
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
            new Fenetre_Rechercher();
            pagePrincipal.dispose();
        }
    }
}