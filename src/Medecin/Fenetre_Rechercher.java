package Medecin;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Fenetre_Rechercher extends Medecin {
    
    private JFrame pageRechercher;
    private JPanel panelPrincipal;
    private JButton boutonRetour, boutonRechercher;
    private JLabel labelInfo, labelNom, labelPrenom, labelNumeroSecuriteSociale, labelDateNaissance;
    private JTextField textFieldNom, textFieldPrenom, tF1, tF2, tF3, tF4, tF5, tF6, tF7;
    private JComboBox<String> comboBoxJour, comboBoxMois, comboBoxAnnee;
    private int mouseX, mouseY;
    private ImageIcon logo;

    public Fenetre_Rechercher() {

        //Cela permet de passer le Boolean 'verrouiller' en true pour limiter les interactions avec la fenêtre parente.
        super(true);

        pageRechercher = new JFrame();

        panelPrincipal = new JPanel();

        logo = new ImageIcon("lib/logo.png");

        panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				pageRechercher.setLocation(pageRechercher.getX() + e.getX() - mouseX, pageRechercher.getY() + e.getY() - mouseY);
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

        boutonRechercher = new JButton("Rechercher");
        boutonRechercher.addActionListener(new BoutonRechercher());
        
        labelInfo = new JLabel("<html><div align=center>*Le remplissage d'une seule catégorie minimum est nécessaire.<br>Remplir plus d'une catégorie est facultatif mais plus précis.</html>");
        labelNom = new JLabel("Nom :");
        labelPrenom = new JLabel("Prénom :");
        labelNumeroSecuriteSociale = new JLabel("<html><div align=left>Numéro de<br>Sécurité Sociale :</html>");
        labelDateNaissance = new JLabel("Date de Naissance :");

        textFieldNom = new JTextField("Nom");
        textFieldPrenom = new JTextField("Prénom");

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
        
        fenetreRechercher();
                
    }

    //Instanciation graphique de la fenêtre (organisation et disposition des divers éléments).
    private void fenetreRechercher() {
        boutonRetour.setBounds(10,10,20,20);
        boutonRetour.setBackground(new Color(166, 58, 80, 255));
        labelNom.setBounds(10,30,100,30);
        labelNom.setForeground(new Color(215, 175, 112, 255));
        labelPrenom.setBounds(10,90,100,30);
        labelPrenom.setForeground(new Color(215, 175, 112, 255));
        labelNumeroSecuriteSociale.setBounds(242,22,300,30);
        labelNumeroSecuriteSociale.setForeground(new Color(215, 175, 112, 255));
        labelDateNaissance.setBounds(242,90,300,30);
        labelDateNaissance.setForeground(new Color(215, 175, 112, 255));
        textFieldNom.setBounds(10,55,150,30);
        textFieldNom.setHorizontalAlignment(JTextField.CENTER);
        textFieldPrenom.setBounds(10,115,150,30);
        textFieldPrenom.setHorizontalAlignment(JTextField.CENTER);
        comboBoxJour.setBounds(240,115,40,30);
        comboBoxMois.setBounds(288,115,40,30);     
        comboBoxAnnee.setBounds(336,115,55,30);  
        tF1.setBounds(200,55,20,30);
        tF1.setHorizontalAlignment(JTextField.CENTER);
        tF2.setBounds(222,55,25,30);
        tF2.setHorizontalAlignment(JTextField.CENTER);
        tF3.setBounds(249,55,25,30);
        tF3.setHorizontalAlignment(JTextField.CENTER);
        tF4.setBounds(276,55,25,30);
        tF4.setHorizontalAlignment(JTextField.CENTER);
        tF5.setBounds(303,55,30,30);
        tF5.setHorizontalAlignment(JTextField.CENTER);
        tF6.setBounds(335,55,30,30);
        tF6.setHorizontalAlignment(JTextField.CENTER);
        tF7.setBounds(367,55,25,30);
        tF7.setHorizontalAlignment(JTextField.CENTER);
        labelInfo.setFont(new Font("Dialog",Font.BOLD,9));
        labelInfo.setBounds(66,146,400,30);
        labelInfo.setForeground(new Color(215, 175, 112, 255));
        boutonRechercher.setBounds(100,180,200,40);
        boutonRechercher.setBackground(new Color(215, 175, 112, 255));
        panelPrincipal.setLayout(null);
        panelPrincipal.setBounds(0,0,400,230);
        panelPrincipal.setBackground(new Color(51, 101, 138, 255));
        panelPrincipal.add(boutonRetour);
        panelPrincipal.add(labelNom);
        panelPrincipal.add(labelPrenom);
        panelPrincipal.add(labelNumeroSecuriteSociale);
        panelPrincipal.add(labelDateNaissance);
        panelPrincipal.add(textFieldNom);
        panelPrincipal.add(textFieldPrenom);
        panelPrincipal.add(tF1);
        panelPrincipal.add(tF2);
        panelPrincipal.add(tF3);
        panelPrincipal.add(tF4);
        panelPrincipal.add(tF5);
        panelPrincipal.add(tF6);
        panelPrincipal.add(tF7);
        panelPrincipal.add(comboBoxJour);
        panelPrincipal.add(comboBoxMois);
        panelPrincipal.add(comboBoxAnnee);
        panelPrincipal.add(labelInfo);
        panelPrincipal.add(boutonRechercher);
        pageRechercher.add(panelPrincipal);
        pageRechercher.setSize(400,230);
        pageRechercher.setTitle("GesPat : Gestion d'Appareillage Médical");
        pageRechercher.setIconImage(logo.getImage());
        pageRechercher.setLayout(null);
        pageRechercher.setUndecorated(true);
        pageRechercher.setResizable(false);
        pageRechercher.setLocationRelativeTo(null);
        pageRechercher.setAlwaysOnTop(true);
        pageRechercher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pageRechercher.setVisible(true);
    }

    /*Ici, sont définis les filtres à appliquer au tableauPatient cela permet à l'utilisateur de ne rechercher qu'une seule catégorie de donnée
    et donc plus l'utilisateur complétera les options de recherche plus celle-ci sera précise. Plus de données apporté feront procéder à un plus grand trie.
    Toutes les possibilités on été envisager en binaire puis rédiger ici avec des if ou else (1 ou 0).*/
    private void rechercherPatient() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableauPatient.getModel());

        tableauPatient.setRowSorter(sorter);
    
        List<RowFilter<Object,Object>> filtres = new ArrayList<RowFilter<Object,Object>>(4);

        RowFilter<TableModel, Object> rowfilter = RowFilter.andFilter(filtres);

        sorter.setRowFilter(rowfilter);

        Boolean N = !textFieldNom.getText().equals("Nom");
        Boolean P = !textFieldPrenom.getText().equals("Prénom");

        String numeroSecuriteSociale = tF1.getText() + "." + tF2.getText() + "." + tF3.getText() + "." + tF4.getText() + "." + tF5.getText() + "." + tF6.getText() + "." + tF7.getText();
        Boolean NSS = !numeroSecuriteSociale.equals("x.xx.xx.xx.xxx.xxx.xx");

        String jour = this.comboBoxJour.getSelectedItem().toString();
        String mois = this.comboBoxMois.getSelectedItem().toString();
        String annee = this.comboBoxAnnee.getSelectedItem().toString();
        String dateNaissance = jour +"/"+ mois +"/"+ annee;
        Boolean D = !dateNaissance.equals("../../....");
        
        try {   
            if (N) {
                if (P) {
                    if (NSS) {
                        if (D) {    
                            filtres.add(RowFilter.regexFilter(textFieldNom.getText(),0));
                            filtres.add(RowFilter.regexFilter(textFieldPrenom.getText(),1));
                            filtres.add(RowFilter.regexFilter(numeroSecuriteSociale,2));
                            filtres.add(RowFilter.regexFilter(dateNaissance,3));
                            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));                            
                        } else {      
                            filtres.add(RowFilter.regexFilter(textFieldNom.getText(),0));
                            filtres.add(RowFilter.regexFilter(textFieldPrenom.getText(),1));
                            filtres.add(RowFilter.regexFilter(numeroSecuriteSociale,2));
                            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));   
                        }
                    } else {
                        if (D) {   
                            filtres.add(RowFilter.regexFilter(textFieldNom.getText(),0));
                            filtres.add(RowFilter.regexFilter(textFieldPrenom.getText(),1));
                            filtres.add(RowFilter.regexFilter(dateNaissance,3));
                            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));   
                        } else {   
                            filtres.add(RowFilter.regexFilter(textFieldNom.getText(),0));
                            filtres.add(RowFilter.regexFilter(textFieldPrenom.getText(),1));  
                            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));     
                        }
                    }
                } else {
                    if (NSS) {
                        if (D) {    
                            filtres.add(RowFilter.regexFilter(textFieldNom.getText(),0));
                            filtres.add(RowFilter.regexFilter(numeroSecuriteSociale,2));
                            filtres.add(RowFilter.regexFilter(dateNaissance,3));
                            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));        
                        } else {   
                            filtres.add(RowFilter.regexFilter(textFieldNom.getText(),0));
                            filtres.add(RowFilter.regexFilter(numeroSecuriteSociale,2)); 
                            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));              
                        }
                    } else {
                        if (D) {      
                            filtres.add(RowFilter.regexFilter(textFieldNom.getText(),0));
                            filtres.add(RowFilter.regexFilter(dateNaissance,3));
                            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));         
                        } else {         
                            filtres.add(RowFilter.regexFilter(textFieldNom.getText(),0));
                            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));  
                        }
                    }
                }	
            } else {
                if (P) {
                    if (NSS) {
                        if (D) {     
                            filtres.add(RowFilter.regexFilter(textFieldPrenom.getText(),1));
                            filtres.add(RowFilter.regexFilter(numeroSecuriteSociale,2));
                            filtres.add(RowFilter.regexFilter(dateNaissance,3));
                            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));                               
                        } else {   
                            filtres.add(RowFilter.regexFilter(textFieldPrenom.getText(),1));
                            filtres.add(RowFilter.regexFilter(numeroSecuriteSociale,2));
                            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));        
                        }
                    } else {
                        if (D) {        
                            filtres.add(RowFilter.regexFilter(textFieldPrenom.getText(),1));
                            filtres.add(RowFilter.regexFilter(dateNaissance,3));
                            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));  
                        } else {    
                            filtres.add(RowFilter.regexFilter(textFieldPrenom.getText(),1));
                            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));  
                        }
                    }
                } else {
                    if (NSS) {
                        if (D) {     
                            filtres.add(RowFilter.regexFilter(numeroSecuriteSociale,2));
                            filtres.add(RowFilter.regexFilter(dateNaissance,3));
                            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));    
                        } else {   
                            filtres.add(RowFilter.regexFilter(numeroSecuriteSociale,2));
                            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));    
                        }
                    } else {
                        if (D) {         
                            filtres.add(RowFilter.regexFilter(dateNaissance,3));
                            sorter.setRowFilter(rowfilter = RowFilter.andFilter(filtres));    
                        } else {
                            new Fenetre_Erreur();
                            pageRechercher.dispose();
                            pagePrincipal.dispose();
                        }
                    }
                }
            }
            if (tableauPatient.getRowCount() <= 0) {
                new Fenetre_Erreur();
                pageRechercher.dispose();
                pagePrincipal.dispose();
            }
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
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

    //Définition des listeners de chaque bouton.
    private class BoutonRetour implements ActionListener {
        public void actionPerformed(ActionEvent Event) {
            boutonAttributionListener();
            pageRechercher.dispose();
        }
    }

    private class BoutonRechercher implements ActionListener {
        public void actionPerformed(ActionEvent Event) {  
            boutonAttributionListener();
            rechercherPatient();
            pageRechercher.dispose();
        }
    }

    //Ici, sont réattribués chaque listener de la classe parente. Dès que la boite de dialogue sera fermée, les interactions avec la classe parente seront de nouveau possibles.
    private void boutonAttributionListener() {
        Medecin.boutonRetour.addActionListener(new Medecin.BoutonRetour());
        Medecin.boutonRechercher.addActionListener(new Medecin.BoutonRechercher());
        Medecin.boutonVisualiser.addActionListener(new Medecin.BoutonVisualiser());

        Medecin.panelPrincipal.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                pagePrincipal.setLocation(pagePrincipal.getX() + e.getX() - mouseX, pagePrincipal.getY() + e.getY() - mouseY);
            }
        });
    
        Medecin.panelPrincipal.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }
}