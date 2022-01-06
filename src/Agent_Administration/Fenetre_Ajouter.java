package Agent_Administration;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Fenetre_Ajouter {
    
    private JFrame pageAjouter;
    private JPanel panelPrincipal, panelSecondaire;
    private JButton boutonRetour, boutonValider, boutonAnnuler;
    private JLabel labelUserType, labelPatient, labelNom, labelPrenom, labelNumeroSecuriteSociale, labelDateNaissance, labelInfo, labelCompleter1, labelCompleter2, labelCompleter3, labelCompleter4, labelSlashs;
    private JTextField textFieldNom, textFieldPrenom, tF1, tF2, tF3, tF4, tF5, tF6, tF7;
    private JComboBox<String> comboBoxJour, comboBoxMois, comboBoxAnnee;
    private int mouseX, mouseY;
    private ImageIcon logo;

    public Fenetre_Ajouter() {

        pageAjouter = new JFrame();

        panelPrincipal = new JPanel();
        panelSecondaire = new JPanel();

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

        labelUserType = new JLabel("Agent d'Administration");
        labelPatient = new JLabel("Patient.e");
        labelNom = new JLabel("Nom :");
        labelPrenom = new JLabel("Prénom :");
        labelNumeroSecuriteSociale = new JLabel("Numéro Sécurité Sociale :");
        labelDateNaissance = new JLabel("Date de Naissance :");
        labelInfo = new JLabel("<html><div align=center>*Il est obligatoire de compléter chaque case pour pouvoir valider.<br>Aucune case n'est facultative.</html>");

        labelCompleter1 = new JLabel("Compléter par :");
        labelCompleter2 = new JLabel("Compléter par :");
        labelCompleter3 = new JLabel("Compléter par :");
        labelCompleter4 = new JLabel("Compléter par :");

        textFieldNom = new JTextField("Nouveau Nom");
        textFieldPrenom = new JTextField("Nouveau Prénom");

        tF1 = new JTextField();
        tF1.setDocument(new LimitJTextField(1));
        tF1.setText("x");

        tF2 = new JTextField();
        tF2.setDocument(new LimitJTextField(2)); 
        tF2.setText("xx");       

        tF3 = new JTextField();
        tF3.setDocument(new LimitJTextField(2)); 
        tF3.setText("xx");

        tF4 = new JTextField();
        tF4.setDocument(new LimitJTextField(2)); 
        tF4.setText("xx");

        tF5 = new JTextField();
        tF5.setDocument(new LimitJTextField(3)); 
        tF5.setText("xxx");

        tF6 = new JTextField();
        tF6.setDocument(new LimitJTextField(3)); 
        tF6.setText("xxx");

        tF7 = new JTextField();
        tF7.setDocument(new LimitJTextField(2)); 
        tF7.setText("xx");

        String jour[] = { "..", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
        comboBoxJour = new JComboBox<>(jour);
        String mois[] = { "..", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
        comboBoxMois = new JComboBox<>(mois);
        String annee[] = { "....", "1900","1901","1902","1903","1904","1905","1906","1907","1908","1909","1910","1911","1912","1913","1914","1915","1916","1917","1918","1919","1920","1921","1922","1923","1924","1925","1926","1927","1928","1929","1930","1931","1932","1933","1934","1935","1936","1937","1938","1939","1940","1941","1942","1943","1944","1945","1946","1947","1948","1949","1950","1951","1952","1953","1954","1955","1956","1957","1958","1959","1960","1961","1962","1963","1964","1965","1966","1967","1968","1969","1970","1971","1972","1973","1974","1975","1976","1977","1978","1979","1980","1981","1982","1983","1984","1985","1986","1987","1988","1989","1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045","2046","2047","2048","2049","2050","2051","2052","2053","2054","2055","2056","2057","2058","2059","2060","2061","2062","2063","2064","2065","2066","2067","2068","2069","2070","2071","2072","2073","2074","2075","2076","2077","2078","2079","2080","2081","2082","2083","2084","2085","2086","2087","2088","2089","2090","2091","2092","2093","2094","2095","2096","2097","2098","2099","2100","2101","2102","2103","2104","2105","2106","2107","2108","2109","2110","2111","2112","2113","2114","2115","2116","2117","2118","2119","2120","2121","2122","2123","2124","2125","2126","2127","2128","2129","2130","2131","2132","2133","2134","2135","2136","2137","2138","2139","2140","2141","2142","2143","2144","2145","2146","2147","2148","2149","2150","2151","2152","2153","2154","2155","2156","2157","2158","2159","2160","2161","2162","2163","2164","2165","2166","2167","2168","2169","2170","2171","2172","2173","2174","2175","2176","2177","2178","2179","2180","2181","2182","2183","2184","2185","2186","2187","2188","2189","2190","2191","2192","2193","2194","2195","2196","2197","2198","2199","2200" };
        comboBoxAnnee = new JComboBox<>(annee);
        
        labelSlashs = new JLabel("/                           /");

        fenetreAjouter();

    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetreAjouter() {
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
        boutonValider.setBounds(25,275,150,50);
        boutonValider.setBackground(new Color(215, 175, 112, 255));
        boutonAnnuler.setBounds(625,275,150,50);
        boutonAnnuler.setBackground(new Color(215, 175, 112, 255));
        labelInfo.setBounds(216,260,400,75);
        labelInfo.setForeground(new Color(215, 175, 112, 255));
        labelCompleter1.setBounds(450,40,300,30);
        labelCompleter1.setForeground(new Color(215, 175, 112, 255));
        labelCompleter2.setBounds(450,80,300,30);
        labelCompleter2.setForeground(new Color(215, 175, 112, 255));
        labelCompleter3.setBounds(450,120,300,30);
        labelCompleter3.setForeground(new Color(215, 175, 112, 255));
        labelCompleter4.setBounds(450,160,300,30);
        labelCompleter4.setForeground(new Color(215, 175, 112, 255));
        textFieldNom.setBounds(550,40,227,30);
        textFieldNom.setHorizontalAlignment(JTextField.CENTER);
        textFieldPrenom.setBounds(550,80,227,30);
        textFieldPrenom.setHorizontalAlignment(JTextField.CENTER);        
        comboBoxJour.setBounds(550,160,40,30);
        comboBoxMois.setBounds(640,160,40,30);     
        comboBoxAnnee.setBounds(720,160,55,30);  
        labelSlashs.setBounds(615,160,200,30); 
        labelSlashs.setForeground(new Color(215, 175, 112, 255));
        tF1.setBounds(550,120,20,30);
        tF1.setHorizontalAlignment(JTextField.CENTER);
        tF2.setBounds(577,120,25,30);
        tF2.setHorizontalAlignment(JTextField.CENTER);
        tF3.setBounds(609,120,25,30);
        tF3.setHorizontalAlignment(JTextField.CENTER);
        tF4.setBounds(645,120,25,30);
        tF4.setHorizontalAlignment(JTextField.CENTER);
        tF5.setBounds(680,120,30,30);
        tF5.setHorizontalAlignment(JTextField.CENTER);
        tF6.setBounds(716,120,30,30);
        tF6.setHorizontalAlignment(JTextField.CENTER);
        tF7.setBounds(752,120,25,30);
        tF7.setHorizontalAlignment(JTextField.CENTER);
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
        panelSecondaire.add(boutonValider);
        panelSecondaire.add(boutonAnnuler);
        panelSecondaire.add(labelInfo);
        panelSecondaire.add(labelCompleter1);
        panelSecondaire.add(labelCompleter2);
        panelSecondaire.add(labelCompleter3);
        panelSecondaire.add(labelCompleter4);
        panelSecondaire.add(textFieldNom);
        panelSecondaire.add(textFieldPrenom);
        panelSecondaire.add(tF1);
        panelSecondaire.add(tF2);
        panelSecondaire.add(tF3);
        panelSecondaire.add(tF4);
        panelSecondaire.add(tF5);
        panelSecondaire.add(tF6);
        panelSecondaire.add(tF7);
        panelSecondaire.add(comboBoxJour);
        panelSecondaire.add(comboBoxMois);
        panelSecondaire.add(comboBoxAnnee);
        panelSecondaire.add(labelSlashs);
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
        String nom = textFieldNom.getText();
        String prenom = textFieldPrenom.getText();
        String numeroSecuriteSociale = tF1.getText() + "." + tF2.getText() + "." + tF3.getText() + "." + tF4.getText() + "." + tF5.getText() + "." + tF6.getText() + "." + tF7.getText();
        String jour = this.comboBoxJour.getSelectedItem().toString();
        String mois = this.comboBoxMois.getSelectedItem().toString();
        String annee = this.comboBoxAnnee.getSelectedItem().toString();
        String dateNaissance = jour +"/"+ mois +"/"+ annee;
        String nouvelleLigne = nom + " ; " + prenom + " ; " + numeroSecuriteSociale + " ; " + dateNaissance + " ;";
        return nouvelleLigne;
    }

    //Ajout de la variable instancié via la méthode 'stringInstanciationViaTextField()' le fichier patient.txt.
    private void ajouterPatientTXT(String nouvelleLigne) {
        try (BufferedWriter ecrireFichierPatient = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("lib/patient.txt", true), "UTF-8"))) {
            
            ecrireFichierPatient.append(nouvelleLigne);
            ecrireFichierPatient.append(System.lineSeparator());

            ecrireFichierPatient.close();

        } catch (IOException ioe) {
            File fichierPatient = new File("lib/patient.txt");
            try {
                fichierPatient.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Permet de paramétrer la limite de nombre de caractère dans un JTextField.
    private class LimitJTextField extends PlainDocument {
        
        private int max;
        
        public LimitJTextField(int max) {
            super();
            this.max = max;
        }

        public void insertString(int offset, String text, AttributeSet attr) throws BadLocationException {
            if (text == null)
                return;
            if ((getLength() + text.length()) <= max) {
                super.insertString(offset, text, attr);
            }
        }
    }

    //Définition des listeners de chaque bouton ainsi que des informations à transmettre aux autres classes.
    private class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Agent_Administration(false);
            pageAjouter.dispose();
        }
    }

    private class BoutonValider implements ActionListener {
        public void actionPerformed(ActionEvent Event) {           
            ajouterPatientTXT(stringInstanciationViaTextField());
            new Agent_Administration(false);
            pageAjouter.dispose();
        }
    }

    private class BoutonAnnuler implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Agent_Administration(false);
            pageAjouter.dispose();
        }
    } 
}