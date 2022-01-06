package Technicien;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class Fenetre_Erreur extends Technicien {

    private JFrame pageErreur;
    private JPanel panelPrincipal;
    private JButton boutonRetour, boutonAnnuler;
    private JLabel labelInfo;
    private int mouseX, mouseY;
    private ImageIcon logo;
    private Boolean consultationsOctroyees;
    
    public Fenetre_Erreur(Boolean consultationsOctroyees) {

        //Cela permet de passer le Boolean 'verrouiller' en true pour limiter les interactions avec la fenêtre parente.
        super(true);
        
        this.consultationsOctroyees = consultationsOctroyees;

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

        labelInfo = new JLabel("<html><div align=center>Attention, une donnée que vous avez saisie n'est pas valide !<br>Nous vous invitons à fermer/Annuler pour réessayer.<br><br>Si vous êtes de nouveau sur cette page, la consultation recherchée<br>n'est potentiellement pas présente dans la base de données.<br><br>Le patient doit être redirigé vers un médecin.</html>");
        
        //Cela permet de determiner si uniquement les consultations traiter doivent être affiché.
        if (consultationsOctroyees) {
            technicienParametrage();
        }

        fenetreErreur();

    }

    //Filtre le tableau pour afficher les consultations traité ou non.
    private void technicienParametrage() {
        Technicien.sorter = new TableRowSorter<TableModel>(tableauConsultations.getModel());
        Technicien.tableauConsultations.setRowSorter(sorter);
        Technicien.filtres = new ArrayList<RowFilter<Object,Object>>(1);
        Technicien.rowfilter = RowFilter.andFilter(filtres);
        Technicien.sorter.setRowFilter(rowfilter);
        Technicien.filtres.add(RowFilter.regexFilter("Octroyer",8));
        Technicien.sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));
        Technicien.checkBoxVoirLesOctrois.setSelected(true);
    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetreErreur() {        
        boutonRetour.setBounds(10,10,20,20);
        boutonRetour.setBackground(new Color(166, 58, 80, 255));
        labelInfo.setBounds(10,5,390,200);
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

    //Définition des listeners de chaque bouton ainsi que des informations à transmettre aux autres classes.
    private class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Rechercher(consultationsOctroyees);
            pageErreur.dispose();
            pagePrincipal.dispose();
        }
    }
   
    private class BoutonAnnuler implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Rechercher(consultationsOctroyees);
            pageErreur.dispose();
            pagePrincipal.dispose();
        }
    }
}