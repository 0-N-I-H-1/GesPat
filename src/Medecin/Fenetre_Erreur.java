package Medecin;
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

public class Fenetre_Erreur extends Medecin {

    private JFrame pageErreur;
    private JPanel panelPrincipal;
    private JButton boutonRetour, boutonAnnuler;
    private JLabel labelInfo;
    private int mouseX, mouseY;
    private ImageIcon logo;
    
    public Fenetre_Erreur() {

        //Cela permet de passer le Boolean 'verrouiller' en true pour limiter les interactions avec la fenêtre parente.
        super(true);
        
        pageErreur = new JFrame();

        panelPrincipal = new JPanel();
        
        logo = new ImageIcon("lib/logo.png");

        panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				pageErreur.setLocation(pageErreur.getX() + e.getX() - mouseX, pageErreur.getY() + e.getY() - mouseY);
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
        
        boutonAnnuler = new JButton("Annuler");
        boutonAnnuler.addActionListener(new BoutonAnnuler());

        labelInfo = new JLabel("<html><div align=center>Attention, une donnée que vous avez saisie n'est pas valide !<br>Nous vous invitons à fermer/Annuler pour réessayer.<br><br>Si vous êtes de nouveau sur cette page, le patient recherché<br>n'est potentiellement pas présent dans la base de données.<br><br>Le patient doit être redirigé vers un agent d'administration.</html>");
        
        fenetreErreur();

    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetreErreur() {        
        boutonRetour.setBounds(10,10,20,20);
        boutonRetour.setBackground(new Color(166, 58, 80, 255));
        labelInfo.setBounds(28,5,400,200);
        labelInfo.setForeground(new Color(215, 175, 112, 255));
        boutonAnnuler.setBounds(100,180,200,40);
        boutonAnnuler.setBackground(new Color(215, 175, 112, 255));   
        panelPrincipal.setLayout(null);
        panelPrincipal.setBounds(0,0,400,230);
        panelPrincipal.setBackground(new Color(51, 101, 138, 255));
        panelPrincipal.add(boutonRetour);
        panelPrincipal.add(labelInfo);
        panelPrincipal.add(boutonAnnuler);
        pageErreur.add(panelPrincipal);
        pageErreur.setSize(400,230);
        pageErreur.setTitle("GesPat : Gestion d'Appareillage Médical");
        pageErreur.setIconImage(logo.getImage());
        pageErreur.setLayout(null);
        pageErreur.setUndecorated(true);
        pageErreur.setResizable(false);
        pageErreur.setLocationRelativeTo(null);
        pageErreur.setAlwaysOnTop(true);
        pageErreur.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pageErreur.setVisible(true);
    }

    //Définition des listeners de chaque bouton.
    private class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Rechercher();
            pageErreur.dispose();
            pagePrincipal.dispose();
        }
    }
    
    private class BoutonAnnuler implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Rechercher();
            pageErreur.dispose();
            pagePrincipal.dispose();
        }
    }
}