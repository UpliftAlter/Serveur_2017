package ihm;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import serveur.Groupe;
import serveur.Utilisateur;


@SuppressWarnings("serial")
public class PanelUtilisateur extends JScrollPane {

    private JButton ajouterGroupeButton = new JButton("Ajouter un membre");
    private JPanel mainPanel = new JPanel();
    private JList<Utilisateur>  listeUtilisateur = new JList<>();
    private JScrollPane listeUtilisateurScrollPanel = new JScrollPane();
    private JTextField rechercheTextField = new JTextField("Tapez pour rechercher");
    private DefaultListModel<Utilisateur> lmRef = new DefaultListModel<>();
	
    public PanelUtilisateur() {
        initComponents();
    }
                      
    private void initComponents() {
    	//Inits
    	
        listeUtilisateur.setModel(lmRef);
        listeUtilisateur.setCellRenderer(new RenduUtilisateurCell());
        listeUtilisateurScrollPanel.setViewportView(listeUtilisateur);


        
        //Events
        rechercheTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rechercheTextFieldKeyPressed(evt);
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
                    .addComponent(listeUtilisateurScrollPanel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ajouterGroupeButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(rechercheTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listeUtilisateurScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
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
    
    //Other
                   
    private void recherche(){
    	ListModel<Utilisateur> lm = lmRef;
    	Utilisateur[] newListTemp = new Utilisateur[lm.getSize()];
    	int cpt = 0;
    	CharSequence cs = rechercheTextField.getText().toLowerCase();
    	for (int i = 0; i < lm.getSize(); i++){
    		String temp = lm.getElementAt(i).toString().toLowerCase();
    		if (cs != null){
    			if (temp.contains(cs)){
    				newListTemp[cpt] = lm.getElementAt(i);
    				cpt++;
    			}
    		}
    	}
    	Utilisateur[] newList = new Utilisateur[cpt];
    	cpt = 0;
    	for (int i = 0; i < newListTemp.length; i++)
    		if(newListTemp[i] != null) {
    			newList[cpt] = newListTemp[i];
    			cpt++;
    		}
    		
    	listeUtilisateur.setListData(newList);
    	listeUtilisateur.repaint();
    }
    
    public void initModel(Groupe g){
    	for (Utilisateur u : g.getGroupeUser())
    		lmRef.addElement(u);
    }
                  
}
