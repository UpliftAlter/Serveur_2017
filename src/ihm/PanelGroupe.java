package ihm;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import utilisateur.Etudiant;
import utilisateur.Groupe;


@SuppressWarnings("serial")
public class PanelGroupe extends JScrollPane {
	private FrameServeur frameServeur;
    private JButton ajouterGroupeButton = new JButton("Ajouter un groupe");
    private JPanel mainPanel = new JPanel();
    private JList<Groupe>  listeGroupe = new JList<>();
    private JScrollPane listeGroupeScrollPanel = new JScrollPane();
    private JTextField rechercheTextField = new JTextField("Tapez pour rechercher");
    private DefaultListModel<Groupe> lmRef = new DefaultListModel<>();
    private Font italic = new Font(rechercheTextField.getFont().getFontName(), Font.ITALIC, rechercheTextField.getFont().getSize());
    private Font original = rechercheTextField.getFont();
    
    public PanelGroupe(FrameServeur frameServeur) {
        this.frameServeur = frameServeur;
    	initComponents();
    }
                      
    private void initComponents() {
    	//Inits
    	test();
    	initModel();
        listeGroupe.setCellRenderer(new RenduGroupeCell());
        listeGroupeScrollPanel.setViewportView(listeGroupe);
        rechercheTextField.setFont(italic);

        
        //Events
        ajouterGroupeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajouterGroupeButtonActionPerformed(evt);
            }
        });
        rechercheTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rechercheTextFieldKeyPressed(evt);
            }
        });
        rechercheTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                messageTextFieldMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                messageTextFieldMouseExited(evt);
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
    
    private void messageTextFieldMouseClicked(java.awt.event.MouseEvent evt) {                                              
        if (rechercheTextField.getText().equals("Tapez pour rechercher")){
        	rechercheTextField.selectAll();
        	rechercheTextField.setFont(original);

        }
   }                                             

   private void messageTextFieldMouseExited(java.awt.event.MouseEvent evt) {                                             
   	  if (rechercheTextField.getText().equals("")){
   		rechercheTextField.setFont(italic);
   		rechercheTextField.setText("Tapez pour rechercher");
   	  }
   }  

    private void listeGroupeValueChanged(javax.swing.event.ListSelectionEvent evt) {                                         
        frameServeur.getPanelUtilisateur().initModel(listeGroupe.getSelectedValue());
    }   
    
    private void ajouterGroupeButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                    
    	String tempS = JOptionPane.showInputDialog("Nom du groupe: ");
    	if (tempS != null)
    		frameServeur.getServeur().getAllGroups().add(new Groupe(tempS));
    	initModel();
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
    
    @SuppressWarnings("unused")
	private void test(){
    	frameServeur.getAllUsers().add(new Etudiant("Jean", "Mouloud"));
    	frameServeur.getAllUsers().add(new Etudiant("Jean", "Castre"));
    	frameServeur.getAllUsers().add(new Etudiant("Jean", "Merde"));
    	frameServeur.getAllUsers().add(new Etudiant("Jean", "Cule"));
    	frameServeur.getAllUsers().add(new Etudiant("Jean", "Bonbeurre"));
    	frameServeur.getAllUsers().add(new Etudiant("Jean", "Neymar"));
    }
    
    public void initModel(){
    	DefaultListModel<Groupe> temp = new DefaultListModel<Groupe>();
    	Groupe groupeTemp = new Groupe("Tous les utilisateurs");
    	groupeTemp.getListeUtilisateur().addAll(frameServeur.getAllUsers());
    	temp.addElement(groupeTemp);
    	if (!frameServeur.getServeur().getAllGroups().isEmpty())
    		for (Groupe g : frameServeur.getServeur().getAllGroups())
    			temp.addElement(g);
    	lmRef = temp;
    	listeGroupe.setModel(lmRef);
    	listeGroupe.repaint();
    }
    
    public Groupe groupeSelected(){
    	return listeGroupe.getSelectedValue();
    }
                  
}
