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

public class Fenetre_Modifier {
    
    private JFrame pageModifier;
    private JPanel panelPrincipal, panelSecondaire;
    private JButton boutonRetour, boutonValider, boutonAnnuler;
    private JLabel labelUserType, labelPatient, labelNom, labelPrenom, labelNumeroSecuriteSociale, labelDateNaissance, labelInfo, labelRemplacer1, labelRemplacer2, labelRemplacer3, labelRemplacer4, labelSlashs;
    private JTextField textFieldNom, textFieldPrenom, tF1, tF2, tF3, tF4, tF5, tF6, tF7;
    private JComboBox<String> comboBoxJour, comboBoxMois, comboBoxAnnee;
    private String nom, prenom, numeroSecuriteSociale, dateNaissance, exNom, exPrenom, exNumeroSecuriteSociale, exDateNaissance;
    private int ligneModelTablePatient, mouseX, mouseY;
    private ImageIcon logo;

    public Fenetre_Modifier(String nom, String prenom, String numeroSecuriteSociale, String dateNaissance, int ligneModelTablePatient) {

        pageModifier = new JFrame();

        panelPrincipal = new JPanel();
        panelSecondaire = new JPanel();

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

        labelUserType = new JLabel("Agent d'Administration");
        labelPatient = new JLabel("Patient.e");
        labelNom = new JLabel("Nom :  " + nom);
        labelPrenom = new JLabel("Pr??nom :  " + prenom);
        labelNumeroSecuriteSociale = new JLabel("Num??ro S??curit?? Sociale :  " + numeroSecuriteSociale);
        labelDateNaissance = new JLabel("Date de Naissance :  " + dateNaissance);
        labelInfo = new JLabel("<html><div align=center>*Le remplissage d'une seule cat??gorie minimum est n??cessaire pour pouvoir valider. Remplir plus de cat??gorie est facultatif.</html>");

        labelRemplacer1 = new JLabel("Remplacer par :");
        labelRemplacer2 = new JLabel("Remplacer par :");
        labelRemplacer3 = new JLabel("Remplacer par :");
        labelRemplacer4 = new JLabel("Remplacer par :");

        textFieldNom = new JTextField("Nouveau Nom");
        textFieldPrenom = new JTextField("Nouveau Pr??nom");
        
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

        this.nom = nom;
        this.exNom = nom;
        this.prenom = prenom;
        this.exPrenom = prenom;
        this.numeroSecuriteSociale = numeroSecuriteSociale;
        this.exNumeroSecuriteSociale = numeroSecuriteSociale;
        this.dateNaissance = dateNaissance;
        this.exDateNaissance = dateNaissance;
        this.ligneModelTablePatient = ligneModelTablePatient;

        fenetreModifier();

    }

    //Instanciation graphique de la fen??tre (organisation et disposition des divers ??l??ments).
    private void fenetreModifier() {
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
        labelInfo.setBounds(202,260,400,75);
        labelInfo.setForeground(new Color(215, 175, 112, 255));
        labelRemplacer1.setBounds(450,40,300,30);
        labelRemplacer1.setForeground(new Color(215, 175, 112, 255));
        labelRemplacer2.setBounds(450,80,300,30);
        labelRemplacer2.setForeground(new Color(215, 175, 112, 255));
        labelRemplacer3.setBounds(450,120,300,30);
        labelRemplacer3.setForeground(new Color(215, 175, 112, 255));
        labelRemplacer4.setBounds(450,160,300,30);
        labelRemplacer4.setForeground(new Color(215, 175, 112, 255));
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
        panelSecondaire.add(labelRemplacer1);
        panelSecondaire.add(labelRemplacer2);
        panelSecondaire.add(labelRemplacer3);
        panelSecondaire.add(labelRemplacer4);
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
        pageModifier.add(panelPrincipal);
        pageModifier.setSize(850,445);
        pageModifier.setTitle("GesPat : Gestion d'Appareillage M??dical");
        pageModifier.setIconImage(logo.getImage());
        pageModifier.setLayout(null);
        pageModifier.setUndecorated(true);
        pageModifier.setResizable(false);
        pageModifier.setLocationRelativeTo(null);
        pageModifier.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pageModifier.setVisible(true);
    }

    /*Ici, est d??fini comment le fichier sera modifi?? cela permet ?? l'utilisateur de ne modifier qu'une seule cat??gorie de donn??e sans
    que dans le fichier les donn??es inchang??es se superposent.
    Toutes les possibilit??s ont ??t?? envisag?? en binaire puis r??diger ici avec des if ou else (1 ou 0).*/
    private String stringInstanciationViaTextField() {
      
        String jour = this.comboBoxJour.getSelectedItem().toString();
        String mois = this.comboBoxMois.getSelectedItem().toString();
        String annee = this.comboBoxAnnee.getSelectedItem().toString();
        String NouvelleDateNaissance = jour +"/"+ mois +"/"+ annee;

        String NouveauNumeroSecuriteSociale = tF1.getText() + "." + tF2.getText() + "." + tF3.getText() + "." + tF4.getText() + "." + tF5.getText() + "." + tF6.getText() + "." + tF7.getText();
        
        Boolean N = !textFieldNom.getText().equals("Nouveau Nom");
        Boolean P = !textFieldPrenom.getText().equals("Nouveau Pr??nom");
        Boolean NSS = !NouveauNumeroSecuriteSociale.equals("x.xx.xx.xx.xxx.xxx.xx");
        Boolean D = !NouvelleDateNaissance.equals("../../....");

        String nouvelleLigne = nom + " ; " + prenom + " ; " + numeroSecuriteSociale + " ; " + dateNaissance + " ;";
        try {   
            if (N) {
                if (P) {
                    if (NSS) {
                        if (D) {    
                            this.nom = textFieldNom.getText();
                            this.prenom = textFieldPrenom.getText();
                            this.numeroSecuriteSociale = tF1.getText() + "." + tF2.getText() + "." + tF3.getText() + "." + tF4.getText() + "." + tF5.getText() + "." + tF6.getText() + "." + tF7.getText();
                            this.dateNaissance = NouvelleDateNaissance;
                            nouvelleLigne = nom + " ; " + prenom + " ; " + numeroSecuriteSociale + " ; " + dateNaissance + " ;";
                        } else {      
                            this.nom = textFieldNom.getText();
                            this.prenom = textFieldPrenom.getText();
                            this.numeroSecuriteSociale = tF1.getText() + "." + tF2.getText() + "." + tF3.getText() + "." + tF4.getText() + "." + tF5.getText() + "." + tF6.getText() + "." + tF7.getText();
                            nouvelleLigne = nom + " ; " + prenom + " ; " + numeroSecuriteSociale + " ;" + dateNaissance + ";";
                        }
                    } else {
                        if (D) {   
                            this.nom = textFieldNom.getText();
                            this.prenom = textFieldPrenom.getText();
                            this.dateNaissance = NouvelleDateNaissance;
                            nouvelleLigne = nom + " ; " + prenom + " ;" + numeroSecuriteSociale + "; " + dateNaissance + " ;";
                        } else {   
                            this.nom = textFieldNom.getText();
                            this.prenom = textFieldPrenom.getText(); 
                            nouvelleLigne = nom + " ; " + prenom + " ;" + numeroSecuriteSociale + ";" + dateNaissance + ";";
                        }
                    }
                } else {
                    if (NSS) {
                        if (D) {    
                            this.nom = textFieldNom.getText();
                            this.numeroSecuriteSociale = tF1.getText() + "." + tF2.getText() + "." + tF3.getText() + "." + tF4.getText() + "." + tF5.getText() + "." + tF6.getText() + "." + tF7.getText();
                            this.dateNaissance = NouvelleDateNaissance;
                            nouvelleLigne = nom + " ;" + prenom + "; " + numeroSecuriteSociale + " ; " + dateNaissance + " ;";
                        } else {   
                            this.nom = textFieldNom.getText();
                            this.numeroSecuriteSociale = tF1.getText() + "." + tF2.getText() + "." + tF3.getText() + "." + tF4.getText() + "." + tF5.getText() + "." + tF6.getText() + "." + tF7.getText();
                            nouvelleLigne = nom + " ;" + prenom + "; " + numeroSecuriteSociale + " ;" + dateNaissance + ";";
                        }
                    } else {
                        if (D) {      
                            this.nom = textFieldNom.getText();
                            this.dateNaissance = NouvelleDateNaissance;  
                            nouvelleLigne = nom + " ;" + prenom + ";" + numeroSecuriteSociale + "; " + dateNaissance + " ;";    
                        } else {         
                            this.nom = textFieldNom.getText();
                            nouvelleLigne = nom + " ;" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + ";";
                        }
                    }
                }	
            } else {
                if (P) {
                    if (NSS) {
                        if (D) {     
                            this.prenom = textFieldPrenom.getText();
                            this.numeroSecuriteSociale = tF1.getText() + "." + tF2.getText() + "." + tF3.getText() + "." + tF4.getText() + "." + tF5.getText() + "." + tF6.getText() + "." + tF7.getText();
                            this.dateNaissance = NouvelleDateNaissance; 
                            nouvelleLigne = nom + "; " + prenom + " ; " + numeroSecuriteSociale + " ; " + dateNaissance + " ;";                           
                        } else {   
                            this.prenom = textFieldPrenom.getText();
                            this.numeroSecuriteSociale = tF1.getText() + "." + tF2.getText() + "." + tF3.getText() + "." + tF4.getText() + "." + tF5.getText() + "." + tF6.getText() + "." + tF7.getText();
                            nouvelleLigne = nom + "; " + prenom + " ; " + numeroSecuriteSociale + " ;" + dateNaissance + ";";
                        }
                    } else {
                        if (D) {        
                            this.prenom = textFieldPrenom.getText();
                            this.dateNaissance = NouvelleDateNaissance;
                            nouvelleLigne = nom + "; " + prenom + " ;" + numeroSecuriteSociale + "; " + dateNaissance + " ;";
                        } else {    
                            this.prenom = textFieldPrenom.getText();
                            nouvelleLigne = nom + "; " + prenom + " ;" + numeroSecuriteSociale + ";" + dateNaissance + ";";
                        }
                    }
                } else {
                    if (NSS) {
                        if (D) {     
                            this.numeroSecuriteSociale = tF1.getText() + "." + tF2.getText() + "." + tF3.getText() + "." + tF4.getText() + "." + tF5.getText() + "." + tF6.getText() + "." + tF7.getText();
                            this.dateNaissance = NouvelleDateNaissance;
                            nouvelleLigne = nom + ";" + prenom + "; " + numeroSecuriteSociale + " ; " + dateNaissance + " ;";
                        } else {   
                            this.numeroSecuriteSociale = tF1.getText() + "." + tF2.getText() + "." + tF3.getText() + "." + tF4.getText() + "." + tF5.getText() + "." + tF6.getText() + "." + tF7.getText();
                            nouvelleLigne = nom + ";" + prenom + "; " + numeroSecuriteSociale + " ;" + dateNaissance + ";";
                        }
                    } else {
                        if (D) {         
                            this.dateNaissance = NouvelleDateNaissance;
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + "; " + dateNaissance + " ;";
                        } else {
                            nouvelleLigne = nom + ";" + prenom + ";" + numeroSecuriteSociale + ";" + dateNaissance + ";";
                        }
                    }
                }
            }
        } catch (java.util.regex.PatternSyntaxException e) {
            return nouvelleLigne;
        }        
        return nouvelleLigne;
    }

    /*Modifie les informations d'un patient de la base de donn??es dans le fichier patient.txt ainsi que dans le fichier consultations.txt si n??cessaire ;
    pour ??viter de perdre toutes les donn??es assign??es ?? son ancienne identit?? dont ses potentiels ancienne consultations.*/
    private void modifierPatientTXT(String nouvelleLigne) {
        try {
            File inputFile = new File("lib/patient.txt");
            File tempFile = new File("lib/patientTemp.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile, true), "UTF-8"));

            String currentLine;
            int count = 0;

            //Copie et colle chaque ligne du fichier patient dans un nouveau fichier temporaire et ajoute la nouvelle ligne de donn??e a la place de l'ancienne.
            while ((currentLine = reader.readLine()) != null) {
                count++;
                if (count == (ligneModelTablePatient+1)) {
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
            
            /*La boucle identifie et compare chaque cat??gorie de donn??e pour trouver l'existence du patient dont on change l'identit??.
            Si nous trouvons un patient poss??dant une identit?? exactement identique, on applique les changements demand??s sinon on restaure la ligne sans modification.*/
            for (int i = 0; i < tableLines.length; i++){
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split(";");
                if (exNom.replaceAll("\\s", "").equals(dataRow[0].replaceAll("\\s", "")) && exPrenom.replaceAll("\\s", "").equals(dataRow[1].replaceAll("\\s", "")) && exNumeroSecuriteSociale.replaceAll("\\s", "").equals(dataRow[2].replaceAll("\\s", "")) && exDateNaissance.replaceAll("\\s", "").equals(dataRow[3].replaceAll("\\s", ""))) {
                    String nouvelleLigneConsultation = nouvelleLigne + dataRow[4] + ";" + dataRow[5] + ";" + dataRow[6] + ";" + dataRow[7] + ";" + dataRow[8] + ";";
                    writer.append(nouvelleLigneConsultation + System.getProperty("line.separator"));
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

    //Permet de param??trer la limite de nombre de caract??re dans un JTextField.
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

    //D??finition des listeners de chaque bouton ainsi que des informations ?? transmettre aux autres classes.
    private class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Agent_Administration(false);
            pageModifier.dispose();
        }
    }

    private class BoutonValider implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            modifierPatientTXT(stringInstanciationViaTextField());
            new Fenetre_Visualiser(nom, prenom, numeroSecuriteSociale, dateNaissance, ligneModelTablePatient);
            pageModifier.dispose();
        }
    }

    private class BoutonAnnuler implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            new Agent_Administration(false);
            pageModifier.dispose();
        }
    } 
}