package ihm;

import javax.swing.JFrame;

public class PanelAjouterMembre extends JFrame {
    private javax.swing.JButton ajouterButton;
    private javax.swing.JButton annulerButton;
    private javax.swing.JScrollPane checkBoxPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField rechercherTextField;
    private FrameServeur frameServeur;
    private CheckBoxList cbl = new CheckBoxList();
    
    public PanelAjouterMembre(FrameServeur frameServeur) {
    	this.frameServeur = frameServeur;
        initComponents();
    }
                       
    private void initComponents() {
    	
    	//Inits
    	cbl.initModel(frameServeur.getAllUsers());
        mainPanel = new javax.swing.JPanel();
        checkBoxPanel = new javax.swing.JScrollPane();
        rechercherTextField = new javax.swing.JTextField();
        annulerButton = new javax.swing.JButton("Annuler");
        ajouterButton = new javax.swing.JButton("Ajouter");
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        checkBoxPanel.setViewportView(cbl);
        
        //Events
        ajouterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajouterButtonActionPerformed(evt);
            }
        });
        
        
        //Layout
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(checkBoxPanel)
            .addComponent(rechercherTextField)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(ajouterButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                .addComponent(annulerButton)
                .addGap(70, 70, 70))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(rechercherTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(annulerButton)
                    .addComponent(ajouterButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void ajouterButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             
                 

                  
}
