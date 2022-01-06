package Technicien;
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

public class Fenetre_Modification extends Fenetre_Visualiser {
    
    private JFrame pageModification;
    private JPanel panelPrincipal;
    private JButton boutonRetour, boutonConfirmationModification, boutonAnnuler;
    private JCheckBox checkBoxConfirmer;
    private JLabel labelInfo;
    private int ligneModelTableConsultations, mouseX, mouseY;
    private ImageIcon logo;
    private String nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut;

    public Fenetre_Modification(String nom, String prenom, String numeroSecuriteSociale, String dateNaissance, String nomMedecin, String dateConsultation, String pathologies, String appareillage, String statut, int ligneModelTableConsultations) {

        /*Cela permet de passer le Boolean 'verrouiller' en true pour limiter les interactions avec la fenêtre parente.
        Ainsi que de passer le Boolean 'nonModifiable' en true ce qui permet de ne pas modifié une consultations déjà traité.
        La transmission des données de l'utilisateur est aussi effectuée pour pouvoir rester sur un seul profil.*/
        super(nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut, ligneModelTableConsultations, true, true);

        pageModification = new JFrame();

        panelPrincipal = new JPanel();

        logo = new ImageIcon("lib/logo.png");

        panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				pageModification.setLocation(pageModification.getX() + e.getX() - mouseX, pageModification.getY() + e.getY() - mouseY);
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

        boutonConfirmationModification = new JButton("<html><div align=center>Confirmer la<br>modification</html>");
        boutonConfirmationModification.addActionListener(new BoutonConfirmationModification());

        boutonAnnuler = new JButton("Annuler");
        boutonAnnuler.addActionListener(new BoutonAnnuler());

        labelInfo = new JLabel("<html><div align=center>Attention, vous vous apprêtez à modifier une consultation<br>de la base de données !<br><br><br>Veuillez cocher pour pouvoir confirmer par la suite :</html>");
        
        checkBoxConfirmer = new JCheckBox();

        this.nom = nom;
        this.prenom = prenom;
        this.numeroSecuriteSociale = numeroSecuriteSociale;
        this.dateNaissance = dateNaissance;
        this.nomMedecin = nomMedecin;
        this.dateConsultation = dateConsultation;
        this.pathologies = pathologies;
        this.appareillage = appareillage;
        this.statut = statut;
        this.ligneModelTableConsultations = ligneModelTableConsultations;

        fenetreModification();

    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetreModification() {
        boutonRetour.setBounds(10,10,20,20);
        boutonRetour.setBackground(new Color(166, 58, 80, 255));
        labelInfo.setBounds(35,10,400,150);
        labelInfo.setForeground(new Color(215, 175, 112, 255));
        checkBoxConfirmer.setBounds(350,107,20,20);
        checkBoxConfirmer.setBackground(new Color(51, 101, 138, 255));
        boutonConfirmationModification.setBounds(35,145,100,40);
        boutonConfirmationModification.setBackground(new Color(215, 175, 112, 255));
        boutonAnnuler.setBounds(257,145,100,40); 
        boutonAnnuler.setBackground(new Color(215, 175, 112, 255));
        panelPrincipal.setLayout(null);
        panelPrincipal.setBounds(0,0,400,200);   
        panelPrincipal.setBackground(new Color(51, 101, 138, 255));
        panelPrincipal.add(boutonRetour);
        panelPrincipal.add(labelInfo);
        panelPrincipal.add(checkBoxConfirmer);
        panelPrincipal.add(boutonConfirmationModification);
        panelPrincipal.add(boutonAnnuler);
        pageModification.add(panelPrincipal);
        pageModification.setSize(400,200);
        pageModification.setTitle("GesPat : Gestion d'Appareillage Médical");
        pageModification.setIconImage(logo.getImage());
        pageModification.setLayout(null);
        pageModification.setUndecorated(true);
        pageModification.setResizable(false);
        pageModification.setLocationRelativeTo(null);
        pageModification.setAlwaysOnTop(true);
        pageModification.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pageModification.setVisible(true);
    }

    //Ici, le statut d'une consultation peut passer de 'Octroie en Attente' à 'Octroyer' tout en conservant les données de la consultation.
    private String stringInstanciationViaTextField() {     
        statut = "Octroyer";
        String nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + ";" + nomMedecin + ";" + dateConsultation + ";" + pathologies + ";" + appareillage + "; " + statut + " ;";     

        return nouvelleLigne;
    }

    //Modifie les informations d'une consultation de la base de données dans le fichier consultations.txt
    private void modifierConsultationsTXT(String nouvelleLigne) {
        try {
            File inputFile = new File("lib/consultations.txt");
            File tempFile = new File("lib/consultationsTemp.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile, true), "UTF-8"));

            String currentLine;
            int count = 0;

            /*La boucle va lire chaque ligne et ajouter 1 à une variable.
            Si la variable et similaire au numéro de ligne du tableau sélectionner par l'utilisateur celle ci est modifié avec les nouvelles données.*/
            while ((currentLine = reader.readLine()) != null) {
                count++;
                if (count == (ligneModelTableConsultations+1)) {
                    writer.append(nouvelleLigne + System.getProperty("line.separator"));
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
            pageModification.dispose();
        }
    }

    private class BoutonConfirmationModification implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            if (checkBoxConfirmer.isSelected()) {
                modifierConsultationsTXT(stringInstanciationViaTextField());
                pageVisualiser.dispose();
                new Fenetre_Visualiser(nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut, ligneModelTableConsultations, false, false);
                pageModification.dispose();
            }
        }
    }

    private class BoutonAnnuler implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            boutonAttributionListener();
            pageModification.dispose();
        }
    }
    
    //Ici, sont réattribués chaque listener de la classe parente. Dès que la boite de dialogue sera fermée, les interactions avec la classe parente seront de nouveau possibles.
    private void boutonAttributionListener() {
        Fenetre_Visualiser.boutonRetour.addActionListener(new Fenetre_Visualiser.BoutonRetour());
        Fenetre_Visualiser.boutonModifier.addActionListener(new Fenetre_Visualiser.BoutonModifier());
        
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