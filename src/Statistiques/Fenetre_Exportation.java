package Statistiques;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Fenetre_Exportation extends Statistiques {

    private JFrame pageExportation;
    private JPanel panelPrincipal, panelRepertoire;
    private JButton boutonRetour, boutonExporter, boutonAnnuler, boutonRechercheLocalisation;
    private JLabel labelInfo;
    private int mouseX, mouseY, nbPatients, nbConsultations, nbAppareilsEnAttentes, nbAppareilsOctroyes, nbPathologie;
    private ImageIcon logo;
    private JTextArea textAreaRepertoire;
    private JScrollPane scrollPaneRepertoire;
    private String repertoireChoisi, textFieldPathologieText;
    
    public Fenetre_Exportation(int nbPatients, int nbConsultations, int nbAppareilsEnAttentes, int nbAppareilsOctroyes, int nbPathologie, String textFieldPathologieText, Boolean verrouiller) {

        //Cela permet de passer le Boolean 'verrouiller' en true pour limiter les interactions avec la fenêtre parente.
        super(true);

        pageExportation = new JFrame();

        panelPrincipal = new JPanel();
        panelRepertoire = new JPanel();
        
        logo = new ImageIcon("lib/logo.png");

        panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				pageExportation.setLocation(pageExportation.getX() + e.getX() - mouseX, pageExportation.getY() + e.getY() - mouseY);
			}
		});
        
		panelPrincipal.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});

        boutonRetour = new JButton();
        boutonRetour.addActionListener(new BoutonRetour());

        boutonExporter = new JButton("Exporter");
        boutonExporter.addActionListener(new BoutonExporter());

        boutonAnnuler = new JButton("Annuler");
        boutonAnnuler.addActionListener(new BoutonAnnuler());

        boutonRechercheLocalisation = new JButton("...");
        boutonRechercheLocalisation.addActionListener(new BoutonRechercheLocalisation());

        labelInfo = new JLabel("<html><div align=center>*Veillez à bien avoir défini un emplacement sur votre ordinateur pour pouvoir exporter les statistiques.</html>");

        textAreaRepertoire = new JTextArea(2,41);
        textAreaRepertoire.setEditable(false);
        textAreaRepertoire.setText("Ici, il sera affiché le dossier de destination pour l'exportation des statistiques.");
        scrollPaneRepertoire = new JScrollPane(textAreaRepertoire, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.nbPatients = nbPatients;
        this.nbConsultations = nbConsultations;
        this.nbAppareilsEnAttentes = nbAppareilsEnAttentes;
        this.nbAppareilsOctroyes = nbAppareilsOctroyes;
        this.textFieldPathologieText = textFieldPathologieText;
        this.nbPathologie = nbPathologie;

        fenetreExportation();

    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetreExportation() {        
        boutonRetour.setBounds(10,10,20,20);
        boutonRetour.setBackground(new Color(166, 58, 80, 255));
        labelInfo.setBounds(150,145,300,50);
        labelInfo.setForeground(new Color(215, 175, 112, 255));
        boutonExporter.setBounds(10,150,135,40);
        boutonExporter.setBackground(new Color(215, 175, 112, 255));
        boutonAnnuler.setBounds(455,150,135,40);   
        boutonAnnuler.setBackground(new Color(215, 175, 112, 255));
        boutonRechercheLocalisation.setBounds(508,64,30,20);   
        boutonRechercheLocalisation.setBackground(new Color(215, 175, 112, 255));
        panelPrincipal.setLayout(null);
        panelPrincipal.setBounds(0,0,600,200);
        panelPrincipal.setBackground(new Color(51, 101, 138, 255));
        panelPrincipal.add(boutonRetour);
        panelPrincipal.add(labelInfo);
        panelPrincipal.add(boutonExporter);
        panelPrincipal.add(boutonAnnuler);
        panelPrincipal.add(boutonRechercheLocalisation);
        panelPrincipal.add(panelRepertoire);
        panelRepertoire.setBounds(30,60,458,40);
        panelRepertoire.setBackground(new Color(51, 101, 138, 255));
        panelRepertoire.add(scrollPaneRepertoire);
        pageExportation.add(panelPrincipal);
        pageExportation.setSize(600,200);
        pageExportation.setTitle("GesPat : Gestion d'Appareillage Médical");
        pageExportation.setIconImage(logo.getImage());
        pageExportation.setLayout(null);
        pageExportation.setUndecorated(true);
        pageExportation.setResizable(false);
        pageExportation.setLocationRelativeTo(null);
        pageExportation.setAlwaysOnTop(true);
        pageExportation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pageExportation.setVisible(true);
    }

    //Exporte les informations définit comme statistique de la base de données dans un répertoire choisi.
    private void Exportation() {
        try {
            String nomFichierExporter = repertoireChoisi + "\\Statistiques Exportées.txt";

            System.out.println(nomFichierExporter);

            File tempFile = new File(nomFichierExporter);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile, true), "UTF-8"));

            writer.append("Nombre de Patients enregistrés : " + nbPatients + System.lineSeparator());
            writer.append("Nombre de Consultations enregistrées : " + nbConsultations + System.lineSeparator());
            writer.append("Nombre d'Appareils en attente d'octroi : " + nbAppareilsEnAttentes + System.lineSeparator());
            writer.append("Nombre d'Appareils octroyés : " + nbAppareilsOctroyes + System.lineSeparator());
            writer.append("Nombre de diagnostiques : '" + textFieldPathologieText + "' " + nbPathologie + System.lineSeparator());
                       
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Définition des listeners de chaque bouton.
    private class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            boutonAttributionListener();
            pageExportation.dispose();
        }
    }

    private class BoutonExporter implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            if (!textAreaRepertoire.getText().equals("Ici, il sera affiché le dossier de destination pour l'exportation des statistiques.")) {
                Exportation();
                boutonAttributionListener();
                pageExportation.dispose();
            }
        }
    }
    
    private class BoutonAnnuler implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            boutonAttributionListener();
            pageExportation.dispose();
        }
    }

    //Ce bouton permet à l'utilisateur de définir le répertoire ou exporter des données.
    private class BoutonRechercheLocalisation implements ActionListener {
        
        JFileChooser choisirLocalisation;
        String dossierBureau;

        private BoutonRechercheLocalisation() {

            dossierBureau = System.getProperty("user.home");
            choisirLocalisation = new JFileChooser(dossierBureau +"/Desktop");
            choisirLocalisation.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        }

        public void actionPerformed(ActionEvent Event) {
            if (choisirLocalisation.showOpenDialog(pageExportation) == JFileChooser.APPROVE_OPTION) {  
                repertoireChoisi = choisirLocalisation.getSelectedFile().toString();
                textAreaRepertoire.setText(repertoireChoisi);
            }
        }
    }

    //Ici, sont réattribués chaque listener de la classe parente. Dès que la boite de dialogue sera fermée, les interactions avec la classe parente seront de nouveau possibles.
    private void boutonAttributionListener() {
        Statistiques.boutonRetour.addActionListener(new Statistiques.BoutonRetour());
        Statistiques.boutonExporter.addActionListener(new Statistiques.BoutonExporter());
        Statistiques.textFieldPathologie.addActionListener(new Statistiques.TextFieldPathologie());
        Statistiques.textFieldPathologie.setEnabled(true);

        Statistiques.panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                pagePrincipal.setLocation(pagePrincipal.getX() + e.getX() - mouseX, pagePrincipal.getY() + e.getY() - mouseY);
            }
        });
    
        Statistiques.panelPrincipal.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }
}