package Agent_Administration;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Fenetre_Visualiser {
    
    private JFrame pageVisualiser;
    private JPanel panelPrincipal, panelSecondaire;
    private JButton boutonRetour, boutonModifier, boutonSupprimer;
    private JLabel labelUserType, labelPatient, labelNom, labelPrenom, labelNumeroSecuriteSociale, labelDateNaissance;
    private String nom, prenom, numeroSecuriteSociale, dateNaissance;
    private int ligneModelTablePatient, mouseX, mouseY;
    private ImageIcon logo;

    public Fenetre_Visualiser(String nom, String prenom, String numeroSecuriteSociale, String dateNaissance, int ligneModelTablePatient) {

        pageVisualiser = new JFrame();

        panelPrincipal = new JPanel();
        panelSecondaire = new JPanel();

        logo = new ImageIcon("lib/logo.png");

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

        boutonRetour = new JButton();
        boutonRetour.addActionListener(new BoutonRetour());

        boutonModifier = new JButton("Modifier");
        boutonModifier.addActionListener(new BoutonModifier());

        boutonSupprimer = new JButton("Supprimer");
        boutonSupprimer.addActionListener(new BoutonSupprimer());

        labelUserType = new JLabel("Agent d'Administration");
        labelPatient = new JLabel("Patient.e");
        labelNom = new JLabel("Nom :  " + nom);
        labelPrenom = new JLabel("Prénom :  " + prenom);
        labelNumeroSecuriteSociale = new JLabel("Numéro Sécurité Sociale :  " + numeroSecuriteSociale);
        labelDateNaissance = new JLabel("Date de Naissance :  " + dateNaissance);

        this.nom = nom;
        this.prenom = prenom;
        this.numeroSecuriteSociale = numeroSecuriteSociale;
        this.dateNaissance = dateNaissance;
        this.ligneModelTablePatient = ligneModelTablePatient;

        fenetreVisualiser();

    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetreVisualiser() {        
        boutonRetour.setBounds(10,10,25,25);
        boutonRetour.setBackground(new Color(166, 58, 80, 255));
        labelUserType.setBounds(360,10,150,25);
        labelUserType.setForeground(new Color(215, 175, 112, 255));
        labelPatient.setBounds(374,10,75,25);
        labelPatient.setForeground(new Color(215, 175, 112, 255));
        labelNom.setBounds(25,40,300,30);
        labelNom.setForeground(new Color(215, 175, 112, 255));
        labelPrenom.setBounds(25,80,300,30);
        labelPrenom.setForeground(new Color(215, 175, 112, 255));
        labelNumeroSecuriteSociale.setBounds(25,120,300,30);
        labelNumeroSecuriteSociale.setForeground(new Color(215, 175, 112, 255));
        labelDateNaissance.setBounds(25,160,300,30);
        labelDateNaissance.setForeground(new Color(215, 175, 112, 255));
        boutonModifier.setBounds(25,275,150,50);
        boutonModifier.setBackground(new Color(215, 175, 112, 255));
        boutonSupprimer.setBounds(625,275,150,50);
        boutonSupprimer.setBackground(new Color(215, 175, 112, 255));
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
        panelSecondaire.add(labelNom);
        panelSecondaire.add(labelPrenom);
        panelSecondaire.add(labelNumeroSecuriteSociale);
        panelSecondaire.add(labelDateNaissance);
        panelSecondaire.add(boutonModifier);
        panelSecondaire.add(boutonSupprimer);
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
    private class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Agent_Administration(false);
            pageVisualiser.dispose();
        }
    }

    private class BoutonModifier implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Modifier(nom, prenom, numeroSecuriteSociale, dateNaissance, ligneModelTablePatient);
            pageVisualiser.dispose();
        }
    }

    private class BoutonSupprimer implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Supprimer(nom, prenom, numeroSecuriteSociale, dateNaissance, ligneModelTablePatient);
            pageVisualiser.dispose();
        }
    } 
}