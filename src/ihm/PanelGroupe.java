package ihm;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import serveur.Etudiant;
import serveur.Groupe;
import serveur.Utilisateur;


@SuppressWarnings("serial")
public class PanelGroupe extends JScrollPane {
	private FrameServeur frameServeur;
    private JButton ajouterGroupeButton = new JButton("Ajouter un groupe");
    private JPanel mainPanel = new JPanel();
    private JList<Groupe>  listeGroupe = new JList<>();
    private JScrollPane listeGroupeScrollPanel = new JScrollPane();
    private JTextField rechercheTextField = new JTextField("Tapez pour rechercher");
    private DefaultListModel<Groupe> lmRef = new DefaultListModel<>();
	
    public PanelGroupe(FrameServeur frameServeur) {
        this.frameServeur = frameServeur;
    	initComponents();
    }
                      
    private void initComponents() {
    	//Inits
    	initModel();
        listeGroupe.setModel(lmRef);
        listeGroupe.setCellRenderer(new RenduGroupeCell());
        listeGroupeScrollPanel.setViewportView(listeGroupe);

        
        //Events
        rechercheTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rechercheTextFieldKeyPressed(evt);
            }
        });
        listeGroupe.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listeGroupeValueChanged(evt);
            }
        });
        

        //Layout
        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rechercheTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                    .addComponent(listeGroupeScrollPanel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ajouterGroupeButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(rechercheTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listeGroupeScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addGap(16, 16, 16)
                .addComponent(ajouterGroupeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        this.setViewportView(mainPanel);
    }                     
    //Events
    private void rechercheTextFieldKeyPressed(java.awt.event.KeyEvent evt) {   
    	recherche();
    }          

    private void listeGroupeValueChanged(javax.swing.event.ListSelectionEvent evt) {                                         
        frameServeur.getPanelUtilisateur().initModel(listeGroupe.getSelectedValue());
    }   
    
    //Other
    private void recherche(){
    	DefaultListModel<Groupe> newModel = new DefaultListModel<>();
    	CharSequence cs = rechercheTextField.getText().toLowerCase();
    	for (int i = 0; i < lmRef.getSize(); i++){
    		String temp = lmRef.getElementAt(i).toString().toLowerCase();
    		if (cs != null)
    			if (temp.contains(cs))
    				newModel.addElement(lmRef.getElementAt(i));
    	}
    	listeGroupe.setModel(newModel);
    	listeGroupe.repaint();
    }
    
    public void initModel(){
    	Groupe g31 = new Groupe("TAD 3.1");
    	Groupe g32 = new Groupe("TAD 3.2");
    	Groupe g41 = new Groupe("TAD 4.1");
    	Groupe g42 = new Groupe("TAD 4.2");
    	Groupe g51 = new Groupe("TAD 5.1");
    	
    	g31.addMember(new Etudiant("Ruben", "Le connard"));
    	g32.addMember(new Etudiant("Mael", "le suceur"));
    	g32.addMember(new Etudiant("Agathe", "the whore attention"));
    	g41.addMember(new Etudiant("Fablyat", "Mofolyat"));
    	g41.addMember(new Etudiant("Qiu", "Jr"));
    	g41.addMember(new Etudiant("Sydney", "quoi"));
    	
    	lmRef.addElement(g31);
    	lmRef.addElement(g32);
    	lmRef.addElement(g41);
    	lmRef.addElement(g42);
    	lmRef.addElement(g51);
    }
    
    public Groupe groupeSelected(){
    	return listeGroupe.getSelectedValue();
    }

                  
}
