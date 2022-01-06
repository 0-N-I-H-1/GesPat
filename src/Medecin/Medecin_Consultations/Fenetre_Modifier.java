package Medecin.Medecin_Consultations;
import Medecin.Medecin_Consultations.Visualisation.Fenetre_Visualiser;
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Fenetre_Modifier {
    
    private JFrame pageModifier;
    private JPanel panelPrincipal, panelSecondaire, panelAppareillage, panelPathologies;
    private JButton boutonRetour, boutonValider, boutonAnnuler;
    private JLabel labelUserType, labelPatient, labelNom, labelPrenom, labelNumeroSecuriteSociale, labelDateNaissance, labelConsultation, labelNomMedecin, labelDateConsultation, labelAppareillage, labelPathologies, labelInfo, labelSlashs, labelInfo2;
    private String nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut;
    private int ligneModelTableConsultations, mouseX, mouseY;
    private ImageIcon logo;    
    private JTextField textFieldAppareillage, textFieldNomMedecin;
    private JComboBox<String> comboBoxJour, comboBoxMois, comboBoxAnnee;    
    private JCheckBox checkBoxConfirmerAppareillage, checkBoxConfirmerNomMedecin, checkBoxDateConsultation, checkBoxConfirmerPathologies, checkBoxRemoveAppareillage;
    private JScrollPane scrollPaneAppareillage, scrollPanePathologies;
    private JTextArea textAreaAppareillage, textAreaPathologies;

    public Fenetre_Modifier(String nom, String prenom, String numeroSecuriteSociale, String dateNaissance, String nomMedecin, String dateConsultation, String pathologies, String appareillage, String statut, int ligneModelTableConsultations) {

        pageModifier = new JFrame();

        panelPrincipal = new JPanel();
        panelSecondaire = new JPanel();
        panelAppareillage = new JPanel();
        panelPathologies = new JPanel();

        logo = new ImageIcon("lib/logo.png");

        panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				pageModifier.setLocation(pageModifier.getX() + e.getX() - mouseX, pageModifier.getY() + e.getY() - mouseY);
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
        labelPatient = new JLabel("Identité Patient.e :");
        labelNom = new JLabel("Nom : " + nom);
        labelPrenom = new JLabel("Prénom : " + prenom);
        labelNumeroSecuriteSociale = new JLabel("Numéro Sécurité Sociale : " + numeroSecuriteSociale);
        labelDateNaissance = new JLabel("Date de Naissance : " + dateNaissance);
        labelConsultation = new JLabel("Information sur la Consultation :");
        labelNomMedecin = new JLabel("Nom du Medecin : " + nomMedecin);
        labelDateConsultation = new JLabel("Date de Consultation : " + dateConsultation);
        labelAppareillage = new JLabel("Potentiel(s) Appareil(s) Requis :");
        labelPathologies = new JLabel("Pathologies, symptômes et remarques observées :");
        labelInfo = new JLabel("<html><div align=center>*Le remplissage d'une seule catégorie minimum est nécessaire pour pouvoir valider. Remplir plus de catégorie est facultatif. Pour modifier une catégorie cocher la case liée à celle-ci décocher annulera.</html>");
        labelInfo2 = new JLabel("<html><div align=center>*Attention ! Pour retirer toutes les attributions<br>d'Appareils médicaux cocher cette case :</html>");

        textFieldAppareillage = new JTextField(appareillage);
        textFieldNomMedecin = new JTextField("Nom du Medecin");
        
        textAreaAppareillage = new JTextArea(1,18);
        textAreaAppareillage.setEditable(false);
        textAreaAppareillage.setText(appareillage);
        scrollPaneAppareillage = new JScrollPane(textAreaAppareillage, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        textAreaPathologies = new JTextArea(4,38);
        textAreaPathologies.setEditable(false);
        textAreaPathologies.setText(pathologies);
        scrollPanePathologies = new JScrollPane(textAreaPathologies, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        String jour[] = { "..", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
        comboBoxJour = new JComboBox<>(jour);
        String mois[] = { "..", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
        comboBoxMois = new JComboBox<>(mois);
        String annee[] = { "....", "1900","1901","1902","1903","1904","1905","1906","1907","1908","1909","1910","1911","1912","1913","1914","1915","1916","1917","1918","1919","1920","1921","1922","1923","1924","1925","1926","1927","1928","1929","1930","1931","1932","1933","1934","1935","1936","1937","1938","1939","1940","1941","1942","1943","1944","1945","1946","1947","1948","1949","1950","1951","1952","1953","1954","1955","1956","1957","1958","1959","1960","1961","1962","1963","1964","1965","1966","1967","1968","1969","1970","1971","1972","1973","1974","1975","1976","1977","1978","1979","1980","1981","1982","1983","1984","1985","1986","1987","1988","1989","1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045","2046","2047","2048","2049","2050","2051","2052","2053","2054","2055","2056","2057","2058","2059","2060","2061","2062","2063","2064","2065","2066","2067","2068","2069","2070","2071","2072","2073","2074","2075","2076","2077","2078","2079","2080","2081","2082","2083","2084","2085","2086","2087","2088","2089","2090","2091","2092","2093","2094","2095","2096","2097","2098","2099","2100","2101","2102","2103","2104","2105","2106","2107","2108","2109","2110","2111","2112","2113","2114","2115","2116","2117","2118","2119","2120","2121","2122","2123","2124","2125","2126","2127","2128","2129","2130","2131","2132","2133","2134","2135","2136","2137","2138","2139","2140","2141","2142","2143","2144","2145","2146","2147","2148","2149","2150","2151","2152","2153","2154","2155","2156","2157","2158","2159","2160","2161","2162","2163","2164","2165","2166","2167","2168","2169","2170","2171","2172","2173","2174","2175","2176","2177","2178","2179","2180","2181","2182","2183","2184","2185","2186","2187","2188","2189","2190","2191","2192","2193","2194","2195","2196","2197","2198","2199","2200" };
        comboBoxAnnee = new JComboBox<>(annee);
        
        checkBoxDateConsultation = new JCheckBox();
        checkBoxDateConsultation.addActionListener(new CheckBoxDateConsultation());

        checkBoxConfirmerNomMedecin = new JCheckBox();
        checkBoxConfirmerNomMedecin.addActionListener(new CheckBoxNomMedecin());

        checkBoxConfirmerAppareillage = new JCheckBox();
        checkBoxConfirmerAppareillage.addActionListener(new CheckBoxAppareillage());

        checkBoxConfirmerPathologies = new JCheckBox();
        checkBoxConfirmerPathologies.addActionListener(new CheckBoxConfirmerPathologies());

        checkBoxRemoveAppareillage = new JCheckBox();
        checkBoxRemoveAppareillage.addActionListener(new CheckBoxRemoveAppareillage());

        labelSlashs = new JLabel("/                /");

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

        fenetreModifier();

    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetreModifier() {
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
        labelDateConsultation.setBounds(360,80,200,30);
        labelDateConsultation.setForeground(new Color(215, 175, 112, 255));
        labelAppareillage.setBounds(360,120,300,30);
        labelAppareillage.setForeground(new Color(215, 175, 112, 255));
        labelPathologies.setBounds(360,160,300,30);
        labelPathologies.setForeground(new Color(215, 175, 112, 255));
        labelInfo.setBounds(202,260,400,75);
        labelInfo.setForeground(new Color(215, 175, 112, 255));
        boutonValider.setBounds(25,275,150,50);
        boutonValider.setBackground(new Color(215, 175, 112, 255));
        boutonAnnuler.setBounds(625,275,150,50);
        boutonAnnuler.setBackground(new Color(215, 175, 112, 255));        
        textFieldAppareillage.setBounds(570,122,205,30);
        textFieldAppareillage.setVisible(false);
        textFieldNomMedecin.setBounds(619,42,155,30);
        textFieldNomMedecin.setHorizontalAlignment(JTextField.CENTER);
        textFieldNomMedecin.setEnabled(false);
        comboBoxJour.setBounds(619,80,40,30);
        comboBoxJour.setEnabled(false);
        comboBoxMois.setBounds(669,80,40,30);
        comboBoxMois.setEnabled(false);
        comboBoxAnnee.setBounds(720,80,55,30);        
        comboBoxAnnee.setEnabled(false);
        labelSlashs.setBounds(662,80,200,30); 
        labelSlashs.setForeground(new Color(215, 175, 112, 255));
        checkBoxDateConsultation.setBounds(598,84,20,20);
        checkBoxDateConsultation.setBackground(new Color(51, 101, 138, 255));
        checkBoxConfirmerAppareillage.setBounds(540,126,20,20);
        checkBoxConfirmerAppareillage.setBackground(new Color(51, 101, 138, 255));
        checkBoxConfirmerNomMedecin.setBounds(598,46,20,20);
        checkBoxConfirmerNomMedecin.setBackground(new Color(51, 101, 138, 255));
        checkBoxConfirmerPathologies.setBounds(650,161,300,28);
        checkBoxConfirmerPathologies.setBackground(new Color(51, 101, 138, 255)); 
        labelInfo2.setFont(new Font("Dialog",Font.BOLD,9));
        labelInfo2.setBounds(345,140,500,30);
        labelInfo2.setForeground(new Color(215, 175, 112, 255));
        checkBoxRemoveAppareillage.setBounds(540,155,18,16);
        checkBoxRemoveAppareillage.setBackground(new Color(51, 101, 138, 255));
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
        panelSecondaire.add(labelPathologies);
        panelSecondaire.add(labelInfo);
        panelSecondaire.add(boutonValider);
        panelSecondaire.add(boutonAnnuler);
        panelSecondaire.add(panelAppareillage);   
        panelSecondaire.add(panelPathologies);   
        panelSecondaire.add(comboBoxJour);
        panelSecondaire.add(comboBoxMois);
        panelSecondaire.add(comboBoxAnnee);
        panelSecondaire.add(labelSlashs);     
        panelSecondaire.add(checkBoxDateConsultation);
        panelSecondaire.add(checkBoxConfirmerAppareillage);
        panelSecondaire.add(checkBoxConfirmerNomMedecin);
        panelSecondaire.add(checkBoxConfirmerPathologies);
        panelSecondaire.add(textFieldAppareillage);
        panelSecondaire.add(textFieldNomMedecin);
        panelSecondaire.add(labelInfo2);
        panelSecondaire.add(checkBoxRemoveAppareillage);
        panelAppareillage.setBounds(570,122,205,42);
        panelAppareillage.setBackground(new Color(51, 101, 138, 255));
        panelAppareillage.add(scrollPaneAppareillage);
        panelPathologies.setBounds(347,185,438,88);
        panelPathologies.setBackground(new Color(51, 101, 138, 255));
        panelPathologies.add(scrollPanePathologies);
        pageModifier.add(panelPrincipal);
        pageModifier.setSize(850,445);
        pageModifier.setTitle("GesPat : Gestion d'Appareillage Médical");
        pageModifier.setIconImage(logo.getImage());
        pageModifier.setLayout(null);
        pageModifier.setUndecorated(true);
        pageModifier.setResizable(false);
        pageModifier.setLocationRelativeTo(null);
        pageModifier.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pageModifier.setVisible(true);
    }

    /*Ici, est défini comment le fichier sera modifié cela permet à l'utilisateur de ne modifier qu'une seule catégorie de donnée sans
    que dans le fichier les données inchangées se superposent.
    Toutes les possibilités ont été envisagé en binaire puis rédiger ici avec des if ou else (1 ou 0).*/
    private String stringInstanciationViaTextField() {
      
        String jour = this.comboBoxJour.getSelectedItem().toString();
        String mois = this.comboBoxMois.getSelectedItem().toString();
        String annee = this.comboBoxAnnee.getSelectedItem().toString();
        String NouvelleDateConsultations = jour +"/"+ mois +"/"+ annee;

        if (checkBoxConfirmerAppareillage.isSelected()) {
            statut = "Octroies en attente.";
        }

        if (checkBoxRemoveAppareillage.isSelected()) {
            textFieldAppareillage.setText("");
            statut = "";
        }

        Boolean N = checkBoxConfirmerNomMedecin.isSelected();
        Boolean D = checkBoxDateConsultation.isSelected();
        Boolean P = checkBoxConfirmerPathologies.isSelected(); 
        Boolean A = checkBoxConfirmerAppareillage.isSelected() || checkBoxRemoveAppareillage.isSelected();

        String nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + "; " + nomMedecin + " ; " + dateConsultation + " ; " + pathologies + " ; " + appareillage + " ; " + statut + " ;";
        try {   
            if (N) {
                if (D) {
                    if (P) {
                        if (A) {    
                            this.nomMedecin = textFieldNomMedecin.getText();
                            this.dateConsultation = NouvelleDateConsultations;
                            this.pathologies = textAreaPathologies.getText();
                            this.appareillage = textFieldAppareillage.getText();
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + "; " + nomMedecin + " ; " + dateConsultation + " ; " + pathologies + " ; " + appareillage + " ; " + statut + " ;";
                        } else {      
                            this.nomMedecin = textFieldNomMedecin.getText();
                            this.dateConsultation = NouvelleDateConsultations;
                            this.pathologies = textAreaPathologies.getText();
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + "; " + nomMedecin + " ; " + dateConsultation + " ; " + pathologies + " ;" + appareillage + ";" + statut + ";";
                        }
                    } else {
                        if (A) {   
                            this.nomMedecin = textFieldNomMedecin.getText();
                            this.dateConsultation = NouvelleDateConsultations;
                            this.appareillage = textFieldAppareillage.getText();
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + "; " + nomMedecin + " ; " + dateConsultation + " ;" + pathologies + ";" + appareillage + " ; " + statut + " ;";
                        } else {   
                            this.nomMedecin = textFieldNomMedecin.getText();
                            this.dateConsultation = NouvelleDateConsultations;
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + "; " + nomMedecin + " ; " + dateConsultation + " ;" + pathologies + ";" + appareillage + ";" + statut + ";";
                        }
                    }
                } else {
                    if (P) {
                        if (A) {    
                            this.nomMedecin = textFieldNomMedecin.getText();
                            this.pathologies = textAreaPathologies.getText();
                            this.appareillage = textFieldAppareillage.getText();
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + "; " + nomMedecin + " ;" + dateConsultation + "; " + pathologies + " ; " + appareillage + " ; " + statut + " ;";
                        } else { 
                            this.nomMedecin = textFieldNomMedecin.getText();
                            this.pathologies = textAreaPathologies.getText();
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + "; " + nomMedecin + " ;" + dateConsultation + "; " + pathologies + " ;" + appareillage + ";" + statut + ";";
                        }
                    } else {
                        if (A) {      
                            this.nomMedecin = textFieldNomMedecin.getText();
                            this.appareillage = textFieldAppareillage.getText();
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + "; " + nomMedecin + " ;" + dateConsultation + ";" + pathologies + "; " + appareillage + " ; " + statut + " ;";
                        } else {         
                            this.nomMedecin = textFieldNomMedecin.getText();
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + "; " + nomMedecin + " ;" + dateConsultation + ";" + pathologies + ";" + appareillage + ";" + statut + ";";
                        }
                    }
                }	
            } else {
                if (D) {
                    if (P) {
                        if (A) {                            
                            this.dateConsultation = NouvelleDateConsultations;
                            this.pathologies = textAreaPathologies.getText();
                            this.appareillage = textFieldAppareillage.getText();
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + ";" + nomMedecin + "; " + dateConsultation + " ; " + pathologies + " ; " + appareillage + " ; " + statut + " ;";
                        } else {           
                            this.dateConsultation = NouvelleDateConsultations;
                            this.pathologies = textAreaPathologies.getText();
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + "; " + nomMedecin + " ; " + dateConsultation + " ; " + pathologies + " ;" + appareillage + ";" + statut + ";";
                        }
                    } else {
                        if (A) {        
                            this.dateConsultation = NouvelleDateConsultations;
                            this.appareillage = textFieldAppareillage.getText();
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + ";" + nomMedecin + "; " + dateConsultation + " ;" + pathologies + "; " + appareillage + " ; " + statut + " ;";
                        } else {    
                            this.dateConsultation = NouvelleDateConsultations;
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + ";" + nomMedecin + "; " + dateConsultation + " ;" + pathologies + ";" + appareillage + ";" + statut + ";";
                        }
                    }
                } else {
                    if (P) {
                        if (A) {
                            this.pathologies = textAreaPathologies.getText();
                            this.appareillage = textFieldAppareillage.getText();
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + ";" + nomMedecin + ";" + dateConsultation + "; " + pathologies + " ; " + appareillage + " ; " + statut + " ;";
                        } else {
                            this.pathologies = textAreaPathologies.getText();
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + ";" + nomMedecin + ";" + dateConsultation + "; " + pathologies + " ;" + appareillage + ";" + statut + ";";
                        }
                    } else {
                        if (A) {
                            this.appareillage = textFieldAppareillage.getText();
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + ";" + nomMedecin + ";" + dateConsultation + ";" + pathologies + "; " + appareillage + " ; " + statut + " ;";
                        } else {
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + ";" + nomMedecin + ";" + dateConsultation + ";" + pathologies + ";" + appareillage + ";" + statut + ";";
                        }
                    }
                }
            }
        } catch (java.util.regex.PatternSyntaxException e) {
            return nouvelleLigne;
        }        
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

            /*Ici est remplacer chaque retour à la ligne pour pouvoir les restitué sur les interfaces utilisateur
            et évité que les données soient mal écrite dans le fichier de sauvegarde.*/
            nouvelleLigne = nouvelleLigne.replaceAll("\n", "<br>");

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
    private class CheckBoxDateConsultation implements ActionListener {
        public void actionPerformed(ActionEvent Event) {           
            if (checkBoxDateConsultation.isSelected()) {
                comboBoxJour.setEnabled(true);
                comboBoxMois.setEnabled(true);      
                comboBoxAnnee.setEnabled(true);
            } else {
                comboBoxJour.setEnabled(false);
                comboBoxMois.setEnabled(false);
                comboBoxAnnee.setEnabled(false);   
            }
        }
    }

    private class CheckBoxNomMedecin implements ActionListener {
        public void actionPerformed(ActionEvent Event) {           
            if (checkBoxConfirmerNomMedecin.isSelected()) {
                textFieldNomMedecin.setEnabled(true);
            } else {
                textFieldNomMedecin.setText("Nom du Medecin");
                textFieldNomMedecin.setEnabled(false);
            }
        }
    }

    private class CheckBoxAppareillage implements ActionListener {
        public void actionPerformed(ActionEvent Event) {           
            if (checkBoxConfirmerAppareillage.isSelected()) {
                panelAppareillage.setVisible(false);
                textFieldAppareillage.setVisible(true);
            } else {               
                textFieldAppareillage.setVisible(false);
                textFieldAppareillage.setText(appareillage);
                panelAppareillage.setVisible(true);
            }
        }
    }

    private class CheckBoxConfirmerPathologies implements ActionListener {
        public void actionPerformed(ActionEvent Event) {           
            if (checkBoxConfirmerPathologies.isSelected()) {
                textAreaPathologies.setEditable(true);
            } else {               
                textAreaPathologies.setEditable(false);                
                textAreaPathologies.setText(pathologies);
            }
        }
    }

    private class CheckBoxRemoveAppareillage implements ActionListener {
        public void actionPerformed(ActionEvent Event) {           
            if (checkBoxRemoveAppareillage.isSelected()) {
                checkBoxConfirmerAppareillage.setSelected(false);
                checkBoxConfirmerAppareillage.setVisible(false);
                panelAppareillage.setVisible(false);
                textFieldAppareillage.setVisible(false);
                textFieldAppareillage.setText(appareillage);
            } else {
                checkBoxConfirmerAppareillage.setVisible(true);                
                panelAppareillage.setVisible(true);
            }
        }
    }

    private class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Visualiser_Patient(nom, prenom, numeroSecuriteSociale, dateNaissance, false);
            pageModifier.dispose();
        }
    }

    private class BoutonValider implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            modifierConsultationsTXT(stringInstanciationViaTextField());
            new Fenetre_Visualiser(nom, prenom, numeroSecuriteSociale, dateNaissance, nomMedecin, dateConsultation, pathologies, appareillage, statut, ligneModelTableConsultations, false);
            pageModifier.dispose();
        }
    }

    private class BoutonAnnuler implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Fenetre_Visualiser_Patient(nom, prenom, numeroSecuriteSociale, dateNaissance, false);
            pageModifier.dispose();
        }
    } 
}