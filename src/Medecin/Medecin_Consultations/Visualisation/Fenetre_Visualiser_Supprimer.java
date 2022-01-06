package Medecin.Medecin_Consultations.Visualisation;
import Medecin.Medecin_Consultations.Fenetre_Visualiser_Patient;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Fenetre_Visualiser_Supprimer extends Fenetre_Visualiser {
    
    private JFrame pageVisualiserSupprimer;
    private JPanel panelPrincipal;
    private JButton boutonRetour, boutonConfirmationSuppression, boutonAnnuler;
    private JCheckBox checkBoxConfirmer;
    private JLabel labelInfo;
    private int ligneModelTableConsultations, mouseX, mouseY;
    private ImageIcon logo;

    public Fenetre_Visualiser_Supprimer(String nom, String prenom, String numeroSecuriteSociale, String dateNaissance, String nomMedecin, String dateConsultation, String pathologies, String appareillage, String statut, int ligneModelTableConsultations) {

        /*Cela permet de passer le Boolean 'verrouiller' en true pour limiter les interactions avec la fenêtre parente.
        La transmission des données de l'utilisateur est aussi effectuée pour pouvoir rester sur un seul profil.*/
        super(nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut, ligneModelTableConsultations, true);

        pageVisualiserSupprimer = new JFrame();

        panelPrincipal = new JPanel();

        logo = new ImageIcon("lib/logo.png");

        panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				pageVisualiserSupprimer.setLocation(pageVisualiserSupprimer.getX() + e.getX() - mouseX, pageVisualiserSupprimer.getY() + e.getY() - mouseY);
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

        boutonConfirmationSuppression = new JButton("<html><div align=center>Confirmer la<br>suppression</html>");
        boutonConfirmationSuppression.addActionListener(new BoutonConfirmationSuppression());

        boutonAnnuler = new JButton("Annuler");
        boutonAnnuler.addActionListener(new BoutonAnnuler());

        labelInfo = new JLabel("<html><div align=center>Attention, vous vous apprêtez à supprimer une consultation<br>de la base de données !<br><br><br>Veuillez cocher pour pouvoir confirmer par la suite :</html>");

        checkBoxConfirmer = new JCheckBox();

        this.ligneModelTableConsultations = ligneModelTableConsultations;

        fenetreSupprimer();

    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetreSupprimer() {
        boutonRetour.setBounds(10,10,20,20);
        boutonRetour.setBackground(new Color(166, 58, 80, 255));
        labelInfo.setBounds(35,10,400,150);
        labelInfo.setForeground(new Color(215, 175, 112, 255));
        checkBoxConfirmer.setBounds(350,107,20,20);
        checkBoxConfirmer.setBackground(new Color(51, 101, 138, 255));
        boutonConfirmationSuppression.setBounds(35,145,100,40);
        boutonConfirmationSuppression.setBackground(new Color(215, 175, 112, 255));
        boutonAnnuler.setBounds(257,145,100,40); 
        boutonAnnuler.setBackground(new Color(215, 175, 112, 255));
        panelPrincipal.setLayout(null);
        panelPrincipal.setBounds(0,0,400,200);   
        panelPrincipal.setBackground(new Color(51, 101, 138, 255));
        panelPrincipal.add(boutonRetour);
        panelPrincipal.add(labelInfo);
        panelPrincipal.add(checkBoxConfirmer);
        panelPrincipal.add(boutonConfirmationSuppression);
        panelPrincipal.add(boutonAnnuler);
        pageVisualiserSupprimer.add(panelPrincipal);
        pageVisualiserSupprimer.setSize(400,200);
        pageVisualiserSupprimer.setTitle("GesPat : Gestion d'Appareillage Médical");
        pageVisualiserSupprimer.setIconImage(logo.getImage());
        pageVisualiserSupprimer.setLayout(null);
        pageVisualiserSupprimer.setUndecorated(true);
        pageVisualiserSupprimer.setResizable(false);
        pageVisualiserSupprimer.setLocationRelativeTo(null);
        pageVisualiserSupprimer.setAlwaysOnTop(true);
        pageVisualiserSupprimer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pageVisualiserSupprimer.setVisible(true);
    }

    //Supprime les informations d'une consultation de la base de données dans le fichier consultations.txt
    private void supprimerConsultationsTXT() {
        try {
            File inputFile = new File("lib/consultations.txt");
            File tempFile = new File("lib/consultationsTemp.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile, true), "UTF-8"));

            String currentLine;
            int count = 0;

            while ((currentLine = reader.readLine()) != null) {
                count++;
                if (count == (ligneModelTableConsultations+1)) {
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);            
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
    private class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {  
            boutonAttributionListener();          
            pageVisualiserSupprimer.dispose();
        }
    }

    private class BoutonConfirmationSuppression implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            if (checkBoxConfirmer.isSelected()) {
                supprimerConsultationsTXT();   
                pageVisualiser.dispose();
                new Fenetre_Visualiser_Patient(nom, prenom, numeroSecuriteSociale, dateNaissance, false);
                pageVisualiserSupprimer.dispose();
            }
        }
    }

    private class BoutonAnnuler implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            boutonAttributionListener();
            pageVisualiserSupprimer.dispose();
        }
    }
    
    //Ici, sont réattribués chaque listener de la classe parente. Dès que la boite de dialogue sera fermée, les interactions avec la classe parente seront de nouveau possibles.
    private void boutonAttributionListener() {
        Fenetre_Visualiser.boutonRetour.addActionListener(new Fenetre_Visualiser.BoutonRetour());
        Fenetre_Visualiser.boutonModifier.addActionListener(new Fenetre_Visualiser.BoutonModifier());
        Fenetre_Visualiser.boutonSupprimer.addActionListener(new Fenetre_Visualiser.BoutonSupprimer());
        Fenetre_Visualiser.boutonExporter.addActionListener(new Fenetre_Visualiser.BoutonExporter());

        Fenetre_Visualiser.panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                pageVisualiser.setLocation(pageVisualiser.getX() + e.getX() - mouseX, pageVisualiser.getY() + e.getY() - mouseY);
            }
        });
    
        Fenetre_Visualiser.panelPrincipal.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }
}