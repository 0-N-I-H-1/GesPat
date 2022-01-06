package Agent_Administration;
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

public class Fenetre_Supprimer extends Agent_Administration {
    
    private JFrame pageSupprimer;
    private JPanel panelPrincipal;
    private JButton boutonRetour, boutonConfirmationSuppression, boutonAnnuler;
    private JCheckBox checkBoxConfirmer;
    private JLabel labelInfo;
    private int ligneModelTablePatient, mouseX, mouseY;
    private ImageIcon logo;
    private String nom, prenom, numeroSecuriteSociale, dateNaissance;

    public Fenetre_Supprimer(String nom, String prenom, String numeroSecuriteSociale, String dateNaissance, int ligneModelTablePatient) {

        //Cela permet de passer le Boolean 'verrouiller' en true pour limiter les interactions avec la fenêtre parente.
        super(true);

        pageSupprimer = new JFrame();

        panelPrincipal = new JPanel();

        logo = new ImageIcon("lib/logo.png");

        panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				pageSupprimer.setLocation(pageSupprimer.getX() + e.getX() - mouseX, pageSupprimer.getY() + e.getY() - mouseY);
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

        labelInfo = new JLabel("<html><div align=center>Attention, vous vous apprêtez à supprimer un.e patient.e<br>de la base de données !<br><br><br>Veuillez cocher pour pouvoir confirmer par la suite :</html>");

        checkBoxConfirmer = new JCheckBox();

        this.nom = nom;
        this.prenom = prenom;
        this.numeroSecuriteSociale = numeroSecuriteSociale;
        this.dateNaissance = dateNaissance;
        this.ligneModelTablePatient = ligneModelTablePatient;

        fenetreSupprimer();

    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetreSupprimer() {
        boutonRetour.setBounds(10,10,20,20);
        boutonRetour.setBackground(new Color(166, 58, 80, 255));
        labelInfo.setBounds(35,10,400,150);
        labelInfo.setForeground(new Color(215, 175, 112, 255));
        checkBoxConfirmer.setBounds(340,107,20,20);
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
        pageSupprimer.add(panelPrincipal);
        pageSupprimer.setSize(400,200);
        pageSupprimer.setTitle("GesPat : Gestion d'Appareillage Médical");
        pageSupprimer.setIconImage(logo.getImage());
        pageSupprimer.setLayout(null);
        pageSupprimer.setUndecorated(true);
        pageSupprimer.setResizable(false);
        pageSupprimer.setLocationRelativeTo(null);
        pageSupprimer.setAlwaysOnTop(true);
        pageSupprimer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pageSupprimer.setVisible(true);
    }

    /*Supprime les informations d'un patient de la base de données dans le fichier patient.txt
    ainsi que dans le fichier consultations.txt si nécessaire pour éviter de laisser des données non-consultables dans les fichiers.*/
    private void supprimerPatientTXT() {
        try {
            File inputFile = new File("lib/patient.txt");
            File tempFile = new File("lib/patientTemp.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile, true), "UTF-8"));

            String currentLine;
            int count = 0;

            //Copie et colle chaque ligne du fichier patient dans un nouveau fichier temporaire sauf la nouvelle ligne de donnée a supprimer.
            while ((currentLine = reader.readLine()) != null) {
                count++;
                if (count == (ligneModelTablePatient+1)) {
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            inputFile.delete();
            tempFile.renameTo(inputFile);            
        } catch (IOException ioe) {
            File fichierPatient = new File("lib/patient.txt");
            try {
                fichierPatient.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            File inputFile = new File("lib/consultations.txt");
            File tempFile = new File("lib/consultationsTemp.txt");
            
            BufferedReader lectureFichierConsultations = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile, true), "UTF-8"));

            Object[] tableLines = lectureFichierConsultations.lines().toArray();
            
            /*La boucle identifie et compare chaque catégorie de donnée pour trouver l'existence du patient dont on supprime ses informations.
            Si nous trouvons un patient possédant une identité exactement identique, on n'ajoute pas ses données dans le nouveau fichier de donnée
            sinon on continue le copié coller de toutes les données dans le nouveau fichier.*/
            for (int i = 0; i < tableLines.length; i++){
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split(";");
                if (nom.replaceAll("\\s", "").equals(dataRow[0].replaceAll("\\s", "")) && prenom.replaceAll("\\s", "").equals(dataRow[1].replaceAll("\\s", "")) && numeroSecuriteSociale.replaceAll("\\s", "").equals(dataRow[2].replaceAll("\\s", "")) && dateNaissance.replaceAll("\\s", "").equals(dataRow[3].replaceAll("\\s", ""))) {
                    continue;
                } else {
                    String ancienneLigneConsultation = dataRow[0] + ";" + dataRow[1] + ";" + dataRow[2] + ";" + dataRow[3] + ";" + dataRow[4] + ";" + dataRow[5] + ";" + dataRow[6] + ";" + dataRow[7] + ";" + dataRow[8] + ";";
                    writer.append(ancienneLigneConsultation + System.getProperty("line.separator"));
                }
            }
            writer.close();
            lectureFichierConsultations.close();
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

    //Définition des listeners de chaque bouton ainsi que des informations à transmettre aux autres classes.
    private class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {  
            boutonAttributionListener();          
            pageSupprimer.dispose();
        }
    }

    private class BoutonConfirmationSuppression implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            if (checkBoxConfirmer.isSelected()) {
                supprimerPatientTXT();   
                pagePrincipal.dispose();
                new Agent_Administration(false);
                pageSupprimer.dispose();
            }
        }
    }

    private class BoutonAnnuler implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            boutonAttributionListener();
            pageSupprimer.dispose();
        }
    }
    
    //Ici, sont réattribués chaque listener de la classe parente. Dès que la boite de dialogue sera fermée, les interactions avec la classe parente seront de nouveau possibles.
    private void boutonAttributionListener() {
        Agent_Administration.boutonRetour.addActionListener(new Agent_Administration.BoutonRetour());
        Agent_Administration.boutonAjouter.addActionListener(new Agent_Administration.BoutonAjouter());
        Agent_Administration.boutonRechercher.addActionListener(new Agent_Administration.BoutonRechercher());
        Agent_Administration.boutonVisualiser.addActionListener(new Agent_Administration.BoutonVisualiser());
        Agent_Administration.boutonModifier.addActionListener(new Agent_Administration.BoutonModifier());
        Agent_Administration.boutonSupprimer.addActionListener(new Agent_Administration.BoutonSupprimer());

        Agent_Administration.panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                pagePrincipal.setLocation(pagePrincipal.getX() + e.getX() - mouseX, pagePrincipal.getY() + e.getY() - mouseY);
            }
        });
    
        Agent_Administration.panelPrincipal.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }
}