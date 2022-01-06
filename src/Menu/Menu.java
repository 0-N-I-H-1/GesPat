package Menu;
import Agent_Administration.Agent_Administration;
import Medecin.Medecin;
import Statistiques.Statistiques;
import Technicien.Technicien;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Menu {

    private JFrame Menu;
    private JPanel panelMenu;
    private JButton boutonFermer, boutonAgentAdministration, boutonMedecin, boutonTechnicien, boutonStatistiques;
    private int mouseX, mouseY;
    private ImageIcon logo;

    public Menu() {

        Menu = new JFrame();
        panelMenu = new JPanel();

        logo = new ImageIcon("lib/logo.png");

        panelMenu.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Menu.setLocation(Menu.getX() + e.getX() - mouseX, Menu.getY() + e.getY() - mouseY);
			}
		});
        
		panelMenu.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});

        boutonFermer = new JButton();
        boutonFermer.addActionListener(new BoutonFermer());

        boutonAgentAdministration = new JButton();
        boutonAgentAdministration.addActionListener(new BoutonAgentAdministration());

        boutonMedecin = new JButton();
        boutonMedecin.addActionListener(new BoutonMedecin());

        boutonTechnicien = new JButton();
        boutonTechnicien.addActionListener(new BoutonTechnicien());

        boutonStatistiques = new JButton();
        boutonStatistiques.addActionListener(new BoutonStatistiques());

        fenetreMenu();
    
    }
    
    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetreMenu() {
        boutonFermer.setBounds(10,10,25,25);
        boutonFermer.setBackground(new Color(166, 58, 80, 255));
        boutonAgentAdministration.setText("Agent d'Administration");
        boutonAgentAdministration.setBounds(90,75,170,40);
        boutonAgentAdministration.setBackground(new Color(215, 175, 112, 255));
        boutonMedecin.setText("Medecin");
        boutonMedecin.setBounds(90,135,170,40);
        boutonMedecin.setBackground(new Color(215, 175, 112, 255));
        boutonTechnicien.setText("Technicien");
        boutonTechnicien.setBounds(90,195,170,40);
        boutonTechnicien.setBackground(new Color(215, 175, 112, 255));
        boutonStatistiques.setText("Statistiques");
        boutonStatistiques.setBounds(90,255,170,40);
        boutonStatistiques.setBackground(new Color(215, 175, 112, 255));
        panelMenu.setLayout(null);
        panelMenu.setBounds(0, 0, 350, 400);
        panelMenu.setBackground(new Color(51, 101, 138, 255));
        panelMenu.add(boutonFermer);
        panelMenu.add(boutonAgentAdministration);
        panelMenu.add(boutonMedecin);
        panelMenu.add(boutonTechnicien);
        panelMenu.add(boutonStatistiques);
        Menu.add(panelMenu);
        Menu.setSize(350,375);
        Menu.setTitle("GesPat : Gestion d'Appareillage Médical");
        Menu.setIconImage(logo.getImage());
        Menu.setLayout(null);
        Menu.setUndecorated(true);
        Menu.setResizable(false);
        Menu.setLocationRelativeTo(null);
        Menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Menu.setVisible(true);
    }

    //Définition des listeners de chaque bouton ainsi que des informations à transmettre aux autres classes.
    private class BoutonFermer implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            System.exit(0);
        }
    }

    private class BoutonAgentAdministration implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Agent_Administration(false);
            Menu.dispose();
        }
    }

    private class BoutonMedecin implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Medecin(false);
            Menu.dispose();
        }
    }

    private class BoutonTechnicien implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Technicien(false);
            Menu.dispose();
        }
    }

    private class BoutonStatistiques implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Statistiques(false);
            Menu.dispose();
        }
    }
}