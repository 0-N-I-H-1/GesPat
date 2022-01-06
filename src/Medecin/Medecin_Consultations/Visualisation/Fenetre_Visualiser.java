package Medecin.Medecin_Consultations.Visualisation;
import Medecin.Medecin_Consultations.Fenetre_Modifier;
import Medecin.Medecin_Consultations.Fenetre_Visualiser_Patient;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

public class Fenetre_Visualiser {
    
    protected JFrame pageVisualiser;
    protected static JPanel panelPrincipal, panelSecondaire, panelAppareillage, panelPathologies;
    protected static JButton boutonRetour, boutonModifier, boutonSupprimer, boutonExporter;
    private JLabel labelUserType, labelPatient, labelNom, labelPrenom, labelNumeroSecuriteSociale, labelDateNaissance, labelConsultation, labelNomMedecin, labelDateConsultation, labelAppareillage, labelStatut, labelPathologies;
    protected String nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut;
    private int ligneModelTableConsultations, mouseX, mouseY;
    private ImageIcon logo;
    private JScrollPane scrollPaneAppareillage, scrollPanePathologies;
    private JTextArea textAreaAppareillage, textAreaPathologies;

    public Fenetre_Visualiser(String nom, String prenom, String numeroSecuriteSociale, String dateNaissance, String nomMedecin, String dateConsultation, String pathologies, String appareillage, String statut, int ligneModelTableConsultations, Boolean verrouiller) {

        pageVisualiser = new JFrame();

        panelPrincipal = new JPanel();
        panelSecondaire = new JPanel();
        panelAppareillage = new JPanel();
        panelPathologies = new JPanel();

        logo = new ImageIcon("lib/logo.png");

        if (!verrouiller) {
            panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
			    public void mouseDragged(MouseEvent e) {
				    pageVisualiser.setLocation(pageVisualiser.getX() + e.getX() - mouseX, pageVisualiser.getY() + e.getY() - mouseY);
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

        boutonModifier = new JButton("Modifier");
        if (!verrouiller) {
            boutonModifier.addActionListener(new BoutonModifier());
        }

        boutonSupprimer = new JButton("Supprimer");
        if (!verrouiller) {
            boutonSupprimer.addActionListener(new BoutonSupprimer());
        }

        boutonExporter = new JButton("Exporter");
        if (!verrouiller) {
            boutonExporter.addActionListener(new BoutonExporter());
        }

        labelUserType = new JLabel("Medecin");
        labelPatient = new JLabel("Identité Patient.e :");
        labelNom = new JLabel("Nom : " + nom);
        labelPrenom = new JLabel("Prénom : " + prenom);
        labelNumeroSecuriteSociale = new JLabel("Numéro Sécurité Sociale : " + numeroSecuriteSociale);
        labelDateNaissance = new JLabel("Date de Naissance : " + dateNaissance);
        labelConsultation = new JLabel("Information sur la Consultation :");
        labelNomMedecin = new JLabel("Nom du Medecin : " + nomMedecin);
        labelDateConsultation = new JLabel("Date de Consultation : " + dateConsultation);
        labelAppareillage = new JLabel("Potentiel(s) Appareil(s) Requis :");
        labelStatut = new JLabel("Statut : " + statut);
        labelPathologies = new JLabel("Pathologies, symptômes et remarques observées :");
        
        textAreaAppareillage = new JTextArea(1,20);
        textAreaAppareillage.setEditable(false);
        textAreaAppareillage.setText(appareillage);
        scrollPaneAppareillage = new JScrollPane(textAreaAppareillage, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        textAreaPathologies = new JTextArea(4,38);
        textAreaPathologies.setEditable(false);
        textAreaPathologies.setText(pathologies);
        scrollPanePathologies = new JScrollPane(textAreaPathologies, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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

        fenetreVisualiser();

    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetreVisualiser() {        
        boutonRetour.setBounds(10,10,25,25);
        boutonRetour.setBackground(new Color(166, 58, 80, 255));
        labelUserType.setBounds(400,10,150,25);
        labelUserType.setForeground(new Color(215, 175, 112, 255));
        labelPatient.setBounds(25,5,200,25);
        labelPatient.setForeground(new Color(215, 175, 112, 255));
        labelConsultation.setBounds(360,5,200,25);
        labelConsultation.setForeground(new Color(215, 175, 112, 255));
        labelNom.setBounds(25,40,300,30);
        labelNom.setForeground(new Color(215, 175, 112, 255));
        labelPrenom.setBounds(25,80,300,30);
        labelPrenom.setForeground(new Color(215, 175, 112, 255));
        labelNumeroSecuriteSociale.setBounds(25,120,300,30);
        labelNumeroSecuriteSociale.setForeground(new Color(215, 175, 112, 255));
        labelDateNaissance.setBounds(25,160,300,30);
        labelDateNaissance.setForeground(new Color(215, 175, 112, 255));
        labelNomMedecin.setBounds(360,40,200,30);
        labelNomMedecin.setForeground(new Color(215, 175, 112, 255));
        labelDateConsultation.setBounds(585,40,300,30);
        labelDateConsultation.setForeground(new Color(215, 175, 112, 255));
        labelAppareillage.setBounds(360,80,200,30);
        labelAppareillage.setForeground(new Color(215, 175, 112, 255));
        labelStatut.setBounds(360,120,300,30);
        labelStatut.setForeground(new Color(215, 175, 112, 255));
        labelPathologies.setBounds(360,160,300,30);
        labelPathologies.setForeground(new Color(215, 175, 112, 255));
        boutonModifier.setBounds(25,275,150,50);
        boutonModifier.setBackground(new Color(215, 175, 112, 255));
        boutonSupprimer.setBounds(625,275,150,50);
        boutonSupprimer.setBackground(new Color(215, 175, 112, 255));
        boutonExporter.setBounds(200,275,400,50);
        boutonExporter.setBackground(new Color(215, 175, 112, 255));
        panelPrincipal.setLayout(null);
        panelPrincipal.setBounds(0,0,850,445);
        panelPrincipal.setBackground(new Color(51, 101, 138, 255));
        panelPrincipal.add(boutonRetour);
        panelPrincipal.add(labelUserType);
        panelPrincipal.add(panelSecondaire);
        panelSecondaire.setLayout(null);
        panelSecondaire.setBounds(27,80,796,345);
        panelSecondaire.setBackground(new Color(51, 101, 138, 255));
        panelSecondaire.add(labelPatient);
        panelSecondaire.add(labelConsultation);
        panelSecondaire.add(labelNom);
        panelSecondaire.add(labelPrenom);
        panelSecondaire.add(labelNumeroSecuriteSociale);
        panelSecondaire.add(labelDateNaissance);
        panelSecondaire.add(labelNomMedecin);
        panelSecondaire.add(labelDateConsultation);
        panelSecondaire.add(labelAppareillage);
        panelSecondaire.add(labelStatut);
        panelSecondaire.add(labelPathologies);
        panelSecondaire.add(boutonModifier);
        panelSecondaire.add(boutonSupprimer);
        panelSecondaire.add(boutonExporter);
        panelSecondaire.add(panelAppareillage);   
        panelSecondaire.add(panelPathologies);
        panelAppareillage.setBounds(550,82,225,42);
        panelAppareillage.setBackground(new Color(51, 101, 138, 255));
        panelAppareillage.add(scrollPaneAppareillage);
        panelPathologies.setBounds(347,185,438,88);
        panelPathologies.setBackground(new Color(51, 101, 138, 255));
        panelPathologies.add(scrollPanePathologies);
        pageVisualiser.add(panelPrincipal);
        pageVisualiser.setSize(850,445);
        pageVisualiser.setTitle("GesPat : Gestion d'Appareillage Médical");
        pageVisualiser.setIconImage(logo.getImage());
        pageVisualiser.setLayout(null);
        pageVisualiser.setUndecorated(true);
        pageVisualiser.setResizable(false);
        pageVisualiser.setLocationRelativeTo(null);
        pageVisualiser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pageVisualiser.setVisible(true);
    }

    //Définition des listeners de chaque bouton ainsi que des informations à transmettre aux autres classes.
    protected class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Visualiser_Patient(nom, prenom, numeroSecuriteSociale, dateNaissance, false);
            pageVisualiser.dispose();
        }
    }

    protected class BoutonModifier implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Modifier(nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut, ligneModelTableConsultations);
            pageVisualiser.dispose();
        }
    }

    protected class BoutonSupprimer implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Visualiser_Supprimer(nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut, ligneModelTableConsultations);
            pageVisualiser.dispose();
        }
    } 

    protected class BoutonExporter implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Exportation(nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut, ligneModelTableConsultations);
            pageVisualiser.dispose();
        }
    }
}