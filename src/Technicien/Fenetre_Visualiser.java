package Technicien;
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
    protected static JPanel panelPrincipal, panelSecondaire, panelAppareillage;
    protected static JButton boutonRetour, boutonModifier;
    private JLabel labelUserType, labelPatient, labelNom, labelPrenom, labelConsultation, labelDateConsultation, labelAppareillage, labelStatut;
    private String nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut;
    private int ligneModelTableConsultations, mouseX, mouseY;
    private ImageIcon logo;
    private JScrollPane scrollPaneAppareillage;
    private JTextArea textAreaAppareillage, textAreaPathologies;
    private Boolean nonModifiable;

    public Fenetre_Visualiser(String nom, String prenom, String numeroSecuriteSociale, String dateNaissance, String nomMedecin, String dateConsultation, String pathologies, String appareillage, String statut, int ligneModelTableConsultations, Boolean verrouiller, Boolean nonModifiable) {

        //La variable 'nonModifiable' permet de ne pas modifié une consultations déjà traité.
        this.nonModifiable = nonModifiable;

        pageVisualiser = new JFrame();

        panelPrincipal = new JPanel();
        panelSecondaire = new JPanel();
        panelAppareillage = new JPanel();

        logo = new ImageIcon("lib/logo.png");

        //La variable 'verrouiller' permet de désactiver les listeners lorsqu'une fenêtre de dialogue est ouverte par-dessus.
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

        labelUserType = new JLabel("Technicien");
        labelPatient = new JLabel("Identité Patient.e :");
        labelNom = new JLabel("Nom : " + nom);
        labelPrenom = new JLabel("Prénom : " + prenom);
        labelConsultation = new JLabel("Information sur la Consultation :");
        labelDateConsultation = new JLabel("Date de Consultation : " + dateConsultation);
        labelAppareillage = new JLabel("Potentiel(s) Appareil(s) Requis :");
        labelStatut = new JLabel("Statut : " + statut);

        textAreaAppareillage = new JTextArea(1,20);
        textAreaAppareillage.setEditable(false);
        textAreaAppareillage.setText(appareillage);
        scrollPaneAppareillage = new JScrollPane(textAreaAppareillage, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        textAreaPathologies = new JTextArea(4,38);
        textAreaPathologies.setEditable(false);
        textAreaPathologies.setText(pathologies);

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
        labelUserType.setBounds(394,10,150,25);
        labelUserType.setForeground(new Color(215, 175, 112, 255));
        labelPatient.setBounds(25,5,200,25);
        labelPatient.setForeground(new Color(215, 175, 112, 255));
        labelConsultation.setBounds(360,5,200,25);
        labelConsultation.setForeground(new Color(215, 175, 112, 255));
        labelNom.setBounds(25,40,300,30);
        labelNom.setForeground(new Color(215, 175, 112, 255));
        labelPrenom.setBounds(25,80,300,30);
        labelPrenom.setForeground(new Color(215, 175, 112, 255));
        labelDateConsultation.setBounds(360,40,300,30);
        labelDateConsultation.setForeground(new Color(215, 175, 112, 255));
        labelAppareillage.setBounds(360,80,200,30);
        labelAppareillage.setForeground(new Color(215, 175, 112, 255));
        labelStatut.setBounds(360,120,300,30);
        labelStatut.setForeground(new Color(215, 175, 112, 255));        
        if (nonModifiable) {
            boutonModifier.setBounds(150,300,450,40);
            boutonModifier.setBackground(new Color(215, 175, 112, 255));
        }
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
        panelSecondaire.add(labelDateConsultation);
        panelSecondaire.add(labelAppareillage);
        panelSecondaire.add(labelStatut);
        if (nonModifiable) {
            panelSecondaire.add(boutonModifier);
        }
        panelSecondaire.add(panelAppareillage);   
        panelAppareillage.setBounds(550,82,225,42);
        panelAppareillage.setBackground(new Color(51, 101, 138, 255));
        panelAppareillage.add(scrollPaneAppareillage);
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

    //Définition des listeners de chaque bouton.
    protected class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Technicien(false);
            pageVisualiser.dispose();
        }
    }

    protected class BoutonModifier implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Modification(nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut, ligneModelTableConsultations);
            pageVisualiser.dispose();
        }
    }
}