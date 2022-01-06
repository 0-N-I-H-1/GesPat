package Medecin.Medecin_Consultations;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Fenetre_Ajouter {
    
    private JFrame pageAjouter;
    private JPanel panelPrincipal, panelSecondaire, panelTextArea;
    private JButton boutonRetour, boutonValider, boutonAnnuler;
    private JLabel labelUserType, labelConsultation, labelNomMedecin, labelDateConsultation, labelPathologies, labelAppareillage, labelInfo, labelInfo2, labelInfo3, labelSlashs;
    private String nom, prenom, numeroSecuriteSociale, dateNaissance;
    private JTextField textFieldNomMedecin, textFieldAppareillage;
    private JTextArea textAreaPathologies;
    private JComboBox<String> comboBoxJour, comboBoxMois, comboBoxAnnee;
    private int mouseX, mouseY;
    private JCheckBox checkBoxConfirmerAppareillage;
    private ImageIcon logo;
    private JScrollPane scrollPane;
    
    public Fenetre_Ajouter(String nom, String prenom, String numeroSecuriteSociale, String dateNaissance) {

        pageAjouter = new JFrame();

        panelPrincipal = new JPanel();
        panelSecondaire = new JPanel();
        panelTextArea = new JPanel();

        logo = new ImageIcon("lib/logo.png");

        panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				pageAjouter.setLocation(pageAjouter.getX() + e.getX() - mouseX, pageAjouter.getY() + e.getY() - mouseY);
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

        boutonValider = new JButton("Valider");
        boutonValider.addActionListener(new BoutonValider());

        boutonAnnuler = new JButton("Annuler");
        boutonAnnuler.addActionListener(new BoutonAnnuler());

        labelUserType = new JLabel("Medecin");
        labelConsultation = new JLabel("Consultation");
        labelNomMedecin = new JLabel("Nom du Medecin :");
        labelDateConsultation = new JLabel("Date de Consultation :");
        labelPathologies = new JLabel("<html><div align=left>Insérer ici toutes les informations sur la consultation passée avec ce patient.<br>Pathologies, symptômes et remarques observées :</html>");
        labelAppareillage = new JLabel("Appareillage nécessaire :");
        labelInfo2 = new JLabel("<html><div align=center>*Cocher si vous souhaitez<br>attribuer un appareil médical :</html>");
        labelInfo3 = new JLabel("<html><div align=center>*Vous pouvez inscrire un ou<br>plusieurs appareils médicaux.</html>");
        labelInfo = new JLabel("<html><div align=center>*Il est obligatoire de compléter chaque case pour pouvoir valider.<br>Aucune case n'est facultative.</html>");

        textFieldNomMedecin = new JTextField("Nom du Medecin");
        textFieldAppareillage = new JTextField();

        textAreaPathologies = new JTextArea(11,43);
        scrollPane = new JScrollPane(textAreaPathologies, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        String jour[] = { "..", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
        comboBoxJour = new JComboBox<>(jour);
        String mois[] = { "..", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
        comboBoxMois = new JComboBox<>(mois);
        String annee[] = { "....", "1900","1901","1902","1903","1904","1905","1906","1907","1908","1909","1910","1911","1912","1913","1914","1915","1916","1917","1918","1919","1920","1921","1922","1923","1924","1925","1926","1927","1928","1929","1930","1931","1932","1933","1934","1935","1936","1937","1938","1939","1940","1941","1942","1943","1944","1945","1946","1947","1948","1949","1950","1951","1952","1953","1954","1955","1956","1957","1958","1959","1960","1961","1962","1963","1964","1965","1966","1967","1968","1969","1970","1971","1972","1973","1974","1975","1976","1977","1978","1979","1980","1981","1982","1983","1984","1985","1986","1987","1988","1989","1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045","2046","2047","2048","2049","2050","2051","2052","2053","2054","2055","2056","2057","2058","2059","2060","2061","2062","2063","2064","2065","2066","2067","2068","2069","2070","2071","2072","2073","2074","2075","2076","2077","2078","2079","2080","2081","2082","2083","2084","2085","2086","2087","2088","2089","2090","2091","2092","2093","2094","2095","2096","2097","2098","2099","2100","2101","2102","2103","2104","2105","2106","2107","2108","2109","2110","2111","2112","2113","2114","2115","2116","2117","2118","2119","2120","2121","2122","2123","2124","2125","2126","2127","2128","2129","2130","2131","2132","2133","2134","2135","2136","2137","2138","2139","2140","2141","2142","2143","2144","2145","2146","2147","2148","2149","2150","2151","2152","2153","2154","2155","2156","2157","2158","2159","2160","2161","2162","2163","2164","2165","2166","2167","2168","2169","2170","2171","2172","2173","2174","2175","2176","2177","2178","2179","2180","2181","2182","2183","2184","2185","2186","2187","2188","2189","2190","2191","2192","2193","2194","2195","2196","2197","2198","2199","2200" };
        comboBoxAnnee = new JComboBox<>(annee);
        
        checkBoxConfirmerAppareillage = new JCheckBox();
        checkBoxConfirmerAppareillage.addActionListener(new CheckBoxAppareillage());

        labelSlashs = new JLabel("/                /");
        
        this.nom = nom;
        this.prenom = prenom;
        this.numeroSecuriteSociale = numeroSecuriteSociale;
        this.dateNaissance = dateNaissance;

        fenetreAjouter();

    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetreAjouter() {
        boutonRetour.setBounds(10,10,25,25);
        boutonRetour.setBackground(new Color(166, 58, 80, 255));
        labelUserType.setBounds(400,10,150,25);
        labelUserType.setForeground(new Color(215, 175, 112, 255));
        labelConsultation.setBounds(361,10,75,25);
        labelConsultation.setForeground(new Color(215, 175, 112, 255));
        labelNomMedecin.setBounds(25,40,300,30);
        labelNomMedecin.setForeground(new Color(215, 175, 112, 255));
        labelDateConsultation.setBounds(25,100,300,30);
        labelDateConsultation.setForeground(new Color(215, 175, 112, 255));
        labelAppareillage.setBounds(25,160,200,30);
        labelAppareillage.setForeground(new Color(215, 175, 112, 255));
        labelInfo2.setFont(new Font("Dialog",Font.BOLD,9));
        labelInfo2.setBounds(25,170,200,50);
        labelInfo2.setForeground(new Color(215, 175, 112, 255));
        labelInfo3.setFont(new Font("Dialog",Font.BOLD,9));
        labelInfo3.setBounds(25,235,200,50);
        labelInfo3.setForeground(new Color(215, 175, 112, 255));        
        labelInfo3.setVisible(false);
        labelPathologies.setBounds(300,48,450,30);
        labelPathologies.setForeground(new Color(215, 175, 112, 255));
        boutonValider.setBounds(25,275,150,50);
        boutonValider.setBackground(new Color(215, 175, 112, 255));
        boutonAnnuler.setBounds(625,275,150,50);
        boutonAnnuler.setBackground(new Color(215, 175, 112, 255));
        labelInfo.setBounds(216,260,400,75);
        labelInfo.setForeground(new Color(215, 175, 112, 255));
        textFieldNomMedecin.setBounds(25,70,156,30);
        textFieldNomMedecin.setHorizontalAlignment(JTextField.CENTER);
        textFieldAppareillage.setBounds(25,215,155,30);
        textFieldAppareillage.setHorizontalAlignment(JTextField.CENTER);      
        textFieldAppareillage.setVisible(false);   
        comboBoxJour.setBounds(25,130,40,30);
        comboBoxMois.setBounds(75,130,40,30);
        comboBoxAnnee.setBounds(126,130,55,30);
        labelSlashs.setBounds(68,130,200,30); 
        labelSlashs.setForeground(new Color(215, 175, 112, 255));
        checkBoxConfirmerAppareillage.setBounds(163,190,20,20);
        checkBoxConfirmerAppareillage.setBackground(new Color(51, 101, 138, 255));
        panelPrincipal.setLayout(null);
        panelPrincipal.setBounds(0,0,850,445);
        panelPrincipal.setBackground(new Color(51, 101, 138, 255));
        panelPrincipal.add(boutonRetour);
        panelPrincipal.add(labelUserType);
        panelPrincipal.add(panelSecondaire);
        panelSecondaire.setLayout(null);
        panelSecondaire.setBounds(27,80,796,345);
        panelSecondaire.setBackground(new Color(51, 101, 138, 255));
        panelSecondaire.add(labelConsultation);
        panelSecondaire.add(labelNomMedecin);
        panelSecondaire.add(labelDateConsultation);
        panelSecondaire.add(labelAppareillage);
        panelSecondaire.add(boutonValider);
        panelSecondaire.add(boutonAnnuler);
        panelSecondaire.add(textFieldNomMedecin);
        panelSecondaire.add(textFieldAppareillage);
        panelSecondaire.add(comboBoxJour);
        panelSecondaire.add(comboBoxMois);
        panelSecondaire.add(comboBoxAnnee);
        panelSecondaire.add(labelSlashs);
        panelSecondaire.add(labelInfo);
        panelSecondaire.add(labelInfo2);
        panelSecondaire.add(labelInfo3);
        panelSecondaire.add(checkBoxConfirmerAppareillage);
        panelSecondaire.add(labelPathologies); 
        panelSecondaire.add(panelTextArea);   
        panelTextArea.setBounds(300,80,475,183);
        panelTextArea.setBackground(new Color(51, 101, 138, 255));
        panelTextArea.add(scrollPane);  
        pageAjouter.add(panelPrincipal);
        pageAjouter.setSize(850,445);
        pageAjouter.setTitle("GesPat : Gestion d'Appareillage Médical");
        pageAjouter.setIconImage(logo.getImage());
        pageAjouter.setLayout(null);
        pageAjouter.setUndecorated(true);
        pageAjouter.setResizable(false);
        pageAjouter.setLocationRelativeTo(null);
        pageAjouter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pageAjouter.setVisible(true);
    }

    //Réception des données entrées par l'utilisateur et instanciation d'une variable.
    private String stringInstanciationViaTextField() {
        String nomMedecin = textFieldNomMedecin.getText();
        String jour = this.comboBoxJour.getSelectedItem().toString();
        String mois = this.comboBoxMois.getSelectedItem().toString();
        String annee = this.comboBoxAnnee.getSelectedItem().toString();
        String dateConsultation = jour +"/"+ mois +"/"+ annee;
        String pathologies = textAreaPathologies.getText();
        String appareillage, statut;
        if (textFieldAppareillage.getText().equals("")) {
            appareillage = "";
            statut = "";
        } else {
            appareillage = textFieldAppareillage.getText();
            statut = "Octroies en attente.";
        }
        
        String nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + "; " + nomMedecin + " ; " + dateConsultation + " ; " + pathologies + " ; " + appareillage + " ; " + statut + " ;";
        return nouvelleLigne;
    }

    //Ajout de la variable instancié via la méthode 'stringInstanciationViaTextField()' le fichier consultations.txt.
    private void ajouterPatientTXT(String nouvelleLigne) {
        try (BufferedWriter ecrireFichierConsultations = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("lib/consultations.txt", true), "UTF-8"))) {
            
            /*Ici est remplacer chaque retour à la ligne pour pouvoir les restitué sur les interfaces utilisateur
            et évité que les données soient mal écrite dans le fichier de sauvegarde.*/
            nouvelleLigne = nouvelleLigne.replaceAll("\n", "<br>");

            ecrireFichierConsultations.append(nouvelleLigne);
            ecrireFichierConsultations.append(System.lineSeparator());

            ecrireFichierConsultations.close();

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
    private class CheckBoxAppareillage implements ActionListener {
        public void actionPerformed(ActionEvent Event) {           
            if (checkBoxConfirmerAppareillage.isSelected()) {
                textFieldAppareillage.setVisible(true);
                labelInfo3.setVisible(true);
            } else {
                textFieldAppareillage.setVisible(false);
                labelInfo3.setVisible(false);
                textFieldAppareillage.setText(null);
            }
        }
    }

    private class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Visualiser_Patient(nom, prenom, numeroSecuriteSociale, dateNaissance, false);
            pageAjouter.dispose();
        }
    }

    private class BoutonValider implements ActionListener {
        public void actionPerformed(ActionEvent Event) {           
            ajouterPatientTXT(stringInstanciationViaTextField());
            new Fenetre_Visualiser_Patient(nom, prenom, numeroSecuriteSociale, dateNaissance, false);
            pageAjouter.dispose();
        }
    }

    private class BoutonAnnuler implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Visualiser_Patient(nom, prenom, numeroSecuriteSociale, dateNaissance, false);
            pageAjouter.dispose();
        }
    } 
}