package Statistiques;
import Menu.Menu;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Statistiques {

    protected JFrame pagePrincipal;
    protected static JPanel panelPrincipal, panelSecondaire;
    protected static JButton boutonRetour, boutonExporter;
    private JLabel labelUserType, labelPatients, labelConsultations, labelAppareilsEnAttentes, labelAppareilsOctroyers, labelPathologies;
    private int mouseX, mouseY, nbPatients, nbConsultations, nbAppareilsEnAttentes, nbAppareilsOctroyes;
    private static int nbPathologie;
    private ImageIcon logo;
    protected static JTextField textFieldPathologie;
    private static String textFieldPathologieText;
    
    public Statistiques(Boolean verrouiller) {
        
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

        boutonExporter = new JButton("Exporter");
        if (!verrouiller) {
            boutonExporter.addActionListener(new BoutonExporter());
        }

        compteurStatistiques();

        labelUserType = new JLabel("Statistiques");
        labelPatients = new JLabel("Nombre de Patients enregistrés : " + nbPatients);
        labelConsultations = new JLabel("Nombre de Consultations enregistrées : " + nbConsultations);
        labelAppareilsEnAttentes = new JLabel("Nombre d'Appareils en attente d'octroi : " + nbAppareilsEnAttentes);
        labelAppareilsOctroyers = new JLabel("Nombre d'Appareils octroyés : " + nbAppareilsOctroyes);
        labelPathologies = new JLabel("*Appuyer sur entrée après insertion d'une pathologie.");

        textFieldPathologie = new JTextField("Inserez la pathologie recherchée.");
        if (!verrouiller) {
            textFieldPathologie.addActionListener(new TextFieldPathologie());
        } else if (verrouiller) {
            textFieldPathologie.setEnabled(false);
        }

        fenetrePrincipale();
        
    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetrePrincipale() {        
        boutonRetour.setBounds(10,10,25,25);  
        boutonRetour.setBackground(new Color(166, 58, 80, 255));      
        labelUserType.setBounds(290,10,150,25);
        labelUserType.setForeground(new Color(215, 175, 112, 255));
        boutonExporter.setBounds(100,105,396,30);
        boutonExporter.setBackground(new Color(215, 175, 112, 255));
        labelPatients.setBounds(25,0,250,20);
        labelPatients.setForeground(new Color(215, 175, 112, 255));
        labelConsultations.setBounds(25,30,250,20);
        labelConsultations.setForeground(new Color(215, 175, 112, 255));
        labelAppareilsEnAttentes.setBounds(330,30,250,20);
        labelAppareilsEnAttentes.setForeground(new Color(215, 175, 112, 255));
        labelAppareilsOctroyers.setBounds(330,0,250,20);
        labelAppareilsOctroyers.setForeground(new Color(215, 175, 112, 255));
        labelPathologies.setBounds(280,60,320,20);
        labelPathologies.setForeground(new Color(215, 175, 112, 255));
        textFieldPathologie.setBounds(25,60,250,30);
        textFieldPathologie.setHorizontalAlignment(JTextField.CENTER);
        panelPrincipal.setLayout(null);
        panelPrincipal.setBounds(0,0,650,245);
        panelPrincipal.setBackground(new Color(51, 101, 138, 255));
        panelPrincipal.add(boutonRetour);
        panelPrincipal.add(labelUserType);
        panelPrincipal.add(panelSecondaire);
        panelSecondaire.setLayout(null);
        panelSecondaire.setBounds(27,80,596,145);
        panelSecondaire.setBackground(new Color(51, 101, 138, 255));
        panelSecondaire.add(boutonExporter);
        panelSecondaire.add(labelPatients);
        panelSecondaire.add(labelConsultations);
        panelSecondaire.add(labelAppareilsEnAttentes);
        panelSecondaire.add(labelAppareilsOctroyers);
        panelSecondaire.add(labelPathologies);
        panelSecondaire.add(textFieldPathologie);
        pagePrincipal.add(panelPrincipal);
        pagePrincipal.setSize(650,245);
        pagePrincipal.setTitle("GesPat : Gestion d'Appareillage Médical");
        pagePrincipal.setIconImage(logo.getImage());
        pagePrincipal.setLayout(null);
        pagePrincipal.setUndecorated(true);
        pagePrincipal.setResizable(false);
        pagePrincipal.setLocationRelativeTo(null);
        pagePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pagePrincipal.setVisible(true);
    }

    //Les fichiers patient.txt et consultations.txt sont lu ligne par ligne et mot par mot pour pouvoir compter certaines données et les répertorier.
    private void compteurStatistiques() {
        try (BufferedReader lectureFichierPatient = new BufferedReader(new InputStreamReader(new FileInputStream("lib/patient.txt"), "UTF-8"))) {
            
            Object[] tableLines = lectureFichierPatient.lines().toArray();
            
            nbPatients = tableLines.length;

            lectureFichierPatient.close();

        } catch (IOException ioe) {
            File fichierPatient = new File("lib/patient.txt");
            try {
                fichierPatient.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader lectureFichierConsultations = new BufferedReader(new InputStreamReader(new FileInputStream("lib/consultations.txt"), "UTF-8"))) {
            
            Object[] tableLines = lectureFichierConsultations.lines().toArray();
            
            nbConsultations = tableLines.length;

            for (int i = 0; i < tableLines.length; i++){
                String line = tableLines[i].toString().trim();
                Boolean contientEnAttente = line.contains("Octroies en attente.");
                Boolean contientOctroyer = line.contains("Octroyer");
                if (contientEnAttente) {
                    nbAppareilsEnAttentes++;
                } else if (contientOctroyer) {
                    nbAppareilsOctroyes++;
                }
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

    //Définition des listeners de chaque élément ainsi que des informations à transmettre aux autres classes.
    protected class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Menu();
            pagePrincipal.dispose();
        }
    }
    
    //Recherche dans le fichier consultations.txt la saisie de l'utilisateur puis l'affiche.
    protected class TextFieldPathologie implements ActionListener {

        public void actionPerformed(ActionEvent Event) {
            Statistiques.textFieldPathologieText = textFieldPathologie.getText();
            try (BufferedReader lectureFichierConsultations = new BufferedReader(new InputStreamReader(new FileInputStream("lib/consultations.txt"), "UTF-8"))) {
            
                Object[] tableLines = lectureFichierConsultations.lines().toArray();
    
                nbPathologie = 0;

                for (int i = 0; i < tableLines.length; i++){
                    String line = tableLines[i].toString().trim();
                    Boolean contient = line.contains(textFieldPathologie.getText());
                    if (contient) {
                        nbPathologie++;
                    }
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
            labelPathologies.setBounds(330,60,250,40);
            labelPathologies.setText("<html><div align=left>Nombre de diagnostiques : '" + textFieldPathologie.getText() + "' " + nbPathologie + "</html>");
        }
    }

    protected class BoutonExporter implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Exportation(nbPatients, nbConsultations, nbAppareilsEnAttentes, nbAppareilsOctroyes, nbPathologie, textFieldPathologieText, true);
            pagePrincipal.dispose();
        }
    }
}