package ihm;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;

import serveur.Serveur;
import utilisateur.Groupe;
import utilisateur.Utilisateur;

@SuppressWarnings("serial")
public class FrameServeur extends JFrame {
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fichierMenu = new JMenu("Fichier");
	private JMenuItem creerGroupe = new JMenuItem("Nouveau groupe");
	private JMenuItem creerUtilisateur = new JMenuItem("Nouveau utilisateur");
	private JMenuItem ajouterMembre = new JMenuItem("Ajouter membres");
	private JMenuItem quitter = new JMenuItem("Quitter");
	private JSplitPane splitPane = new JSplitPane();
	private PanelGroupe panelGroupe;
	private PanelUtilisateur panelUtilisateur;
	private Serveur serveur;

	public FrameServeur(Serveur serveur) {
		this.serveur = serveur;
		initComponents();
	}

	private void initComponents() {
		// Inits
		panelGroupe = new PanelGroupe(this);
		panelUtilisateur = new PanelUtilisateur(this);
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(500, 400));
		setPreferredSize(new Dimension(1000, 700));
		splitPane.setDividerLocation(480);
		splitPane.setLeftComponent(panelGroupe);
		splitPane.setRightComponent(panelUtilisateur);
		creerGroupe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_G,
				java.awt.event.InputEvent.CTRL_MASK));
		fichierMenu.add(creerGroupe);
		creerUtilisateur.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_U,
				java.awt.event.InputEvent.CTRL_MASK));
		fichierMenu.add(creerUtilisateur);
		ajouterMembre.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_M,
				java.awt.event.InputEvent.CTRL_MASK));
		fichierMenu.add(ajouterMembre);
		quitter.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_Q,
				java.awt.event.InputEvent.CTRL_MASK));
		fichierMenu.add(quitter);
		creerUtilisateur.setEnabled(false);
		ajouterMembre.setEnabled(false);
		menuBar.add(fichierMenu);
		setJMenuBar(menuBar);
		

		// Events

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {

				try {
					serveur.disconnect();
				} catch (IOException e) {
					System.out.println("Erreur deconnexion serveur");
				} finally {
					System.exit(0);
				}

			}
		});

        creerUtilisateur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creerUtilisateurActionPerformed(evt);
            }
        });
        
        creerGroupe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creerGroupeActionPerformed(evt);
            }
        });
        ajouterMembre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajouterMembreActionPerformed(evt);
            }
        });   
        quitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					quitterActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

		// Layout
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				splitPane, javax.swing.GroupLayout.Alignment.TRAILING,
				javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 430,
				Short.MAX_VALUE));

		pack();
	}

	// Events
    private void creerUtilisateurActionPerformed(java.awt.event.ActionEvent evt) {                                                 
    	panelUtilisateur.ajouterMembreButtonActionPerformed(evt);
    }                                                

    private void creerGroupeActionPerformed(java.awt.event.ActionEvent evt) {                                           
        panelGroupe.ajouterGroupeButtonActionPerformed(evt);
    }                                          

    private void ajouterMembreActionPerformed(java.awt.event.ActionEvent evt) {                                              
        panelUtilisateur.ajouterMembreButtonActionPerformed(evt);
    }                                             

    private void quitterActionPerformed(java.awt.event.ActionEvent evt) throws IOException { 
    	serveur.disconnect();
        System.exit(0);
    }  
    
	// Others
	public PanelGroupe getPanelGroupe() {
		return panelGroupe;
	}

	public PanelUtilisateur getPanelUtilisateur() {
		return panelUtilisateur;
	}

	public Serveur getServeur() {
		return serveur;
	}

	public List<Utilisateur> getAllUsers() {
		return serveur.getAllUsers();
	}

	public List<Groupe> getAllGroups() {
		return serveur.getAllGroups();
	}

	public void setUserButtonEnable() {
		creerUtilisateur.setEnabled(true);
		ajouterMembre.setEnabled(false);
	}

	public void setMemberButtonEnable() {
		ajouterMembre.setEnabled(true);
		creerUtilisateur.setEnabled(false);
	}
	
	
}
