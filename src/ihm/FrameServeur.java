package ihm;

import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSplitPane;

import serveur.Serveur;
import utilisateur.Groupe;
import utilisateur.Utilisateur;

@SuppressWarnings("serial")
public class FrameServeur extends JFrame {

	private JMenu fichierMenu = new JMenu("Fichier");
	private JMenuBar menuBar = new JMenuBar();
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
		menuBar.add(fichierMenu);
		setJMenuBar(menuBar);

		// Events
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {

				try {
					serveur.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					System.exit(0);
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
	
	public List<Utilisateur> getAllUsers(){
		return serveur.getAllUsers();
	}
	
	public List<Groupe> getAllGroups(){
		return serveur.getAllGroups();
	}
}
