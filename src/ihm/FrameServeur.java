package ihm;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSplitPane;

@SuppressWarnings("serial")
public class FrameServeur extends JFrame {

    private JMenu fichierMenu = new JMenu("Fichier");
    private JMenuBar menuBar = new JMenuBar();
    private JSplitPane splitPane = new JSplitPane();
	
    public FrameServeur() {
        initComponents();
    }
                       
    private void initComponents() {
    	//Inits
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500,400));
        setPreferredSize(new Dimension(1000,700));
        splitPane.setDividerLocation(480);
        splitPane.setLeftComponent(new PanelGroupe());
        splitPane.setRightComponent(new PanelUtilisateur());
        menuBar.add(fichierMenu);
        setJMenuBar(menuBar);
        
        //Events
        
        //Layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        );

        pack();
    }                     
	//Events
	
	//Others
                      
}
