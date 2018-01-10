package ihm;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import utilisateur.Groupe;
import utilisateur.Utilisateur;

@SuppressWarnings("serial")
public class PanelUtilisateur extends JScrollPane {
	@SuppressWarnings("unused")
	private FrameServeur frameServeur;
	private JPopupMenu popupMenu = new JPopupMenu();
	private JMenuItem supprimer = new JMenuItem("Supprimer");
	private JButton ajouterMembreButton = new JButton(
			"Creer un nouveau utilisateur");
	private JPanel mainPanel = new JPanel();
	private JList<Utilisateur> listeUtilisateur = new JList<>();
	private JScrollPane listeUtilisateurScrollPanel = new JScrollPane();
	private JTextField rechercheTextField = new JTextField(
			"Tapez pour rechercher");
	private DefaultListModel<Utilisateur> lmRef = new DefaultListModel<>();
	private Font italic = new Font(rechercheTextField.getFont().getFontName(),
			Font.ITALIC, rechercheTextField.getFont().getSize());
	private Font original = rechercheTextField.getFont();
	private FrameAjouterMembre panelAjouterMembre;
	private FrameAjouterUtilisateur panelAjouterUtilisateur;
	private boolean addMemberOrAddUser = false;

	public PanelUtilisateur(FrameServeur frameServeur) {
		this.frameServeur = frameServeur;
		initComponents();
	}

	private void initComponents() {
		// Inits
		ajouterMembreButton.setEnabled(false);
		panelAjouterUtilisateur = new FrameAjouterUtilisateur(frameServeur);
		panelAjouterMembre = new FrameAjouterMembre(frameServeur);
		listeUtilisateur.setModel(lmRef);
		listeUtilisateur.setCellRenderer(new RenduUtilisateurCell());
		listeUtilisateurScrollPanel.setViewportView(listeUtilisateur);
		rechercheTextField.setFont(italic);
		popupMenu.add(supprimer);

		// Events
		supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimerActionPerformed(evt);
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
		ajouterMembreButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						ajouterMembreButtonActionPerformed(evt);
					}
				});
		listeUtilisateur.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int index = listeUtilisateur.locationToIndex(evt.getPoint());
				listeUtilisateur.setSelectedIndex(index);
				if(SwingUtilities.isRightMouseButton(evt) && listeUtilisateur.getSelectedValue() != null)
					doPop(evt);
			}
		});

		// Layout
		javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(
				mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout
				.setHorizontalGroup(mainPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								mainPanelLayout
										.createSequentialGroup()
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																rechercheTextField,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																450,
																Short.MAX_VALUE)
														.addComponent(
																listeUtilisateurScrollPanel,
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																ajouterMembreButton,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addGap(0, 0, 0)));
		mainPanelLayout
				.setVerticalGroup(mainPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								mainPanelLayout
										.createSequentialGroup()
										.addComponent(
												rechercheTextField,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												listeUtilisateurScrollPanel,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												402, Short.MAX_VALUE)
										.addGap(16, 16, 16)
										.addComponent(
												ajouterMembreButton,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												38,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));

		this.setViewportView(mainPanel);
	}

	// Events
    private void supprimerActionPerformed(java.awt.event.ActionEvent evt) { 
    	Utilisateur temp = listeUtilisateur.getSelectedValue();
		if (!addMemberOrAddUser) {
			frameServeur.getServeur().removeUser(temp);
		} else {
			frameServeur.getServeur().removeUserFromGroup(frameServeur.getPanelGroupe().getGroupeSelected(), temp);
		}
    	
    	lmRef.removeElement(temp);
    	listeUtilisateur.repaint();
    }
    
	private void rechercheTextFieldKeyPressed(java.awt.event.KeyEvent evt) {
		recherche();
	}

	private void messageTextFieldMouseClicked(java.awt.event.MouseEvent evt) {
		if (rechercheTextField.getText().equals("Tapez pour rechercher")) {
			rechercheTextField.selectAll();
			rechercheTextField.setFont(original);

		}
	}

	private void messageTextFieldMouseExited(java.awt.event.MouseEvent evt) {
		if (rechercheTextField.getText().equals("")) {
			rechercheTextField.setFont(italic);
			rechercheTextField.setText("Tapez pour rechercher");
		}
	}

	public void ajouterMembreButtonActionPerformed(
			java.awt.event.ActionEvent evt) {
		if (!addMemberOrAddUser) {
			panelAjouterUtilisateur.setVisible(true);
			panelAjouterUtilisateur.resettextFields();
		} else {
			panelAjouterMembre.setVisible(true);
			panelAjouterMembre.getCbl().initModel(frameServeur.getAllUsers());
		}
	}

	// Other
    public void doPop (MouseEvent e){
        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }
    
	private void recherche() {
		DefaultListModel<Utilisateur> newModel = new DefaultListModel<>();
		CharSequence cs = rechercheTextField.getText().toLowerCase();
		for (int i = 0; i < lmRef.getSize(); i++) {
			String temp = lmRef.getElementAt(i).toString().toLowerCase();
			if (cs != null)
				if (temp.contains(cs))
					newModel.addElement(lmRef.getElementAt(i));
		}
		
		listeUtilisateur.setModel(newModel);
		listeUtilisateur.repaint();
	}

	public void initModel(Groupe g) {
		if (g != null) {
			lmRef.removeAllElements();
			for (Utilisateur u : g.getListeUtilisateur())
				lmRef.addElement(u);
			listeUtilisateur.setModel(lmRef);
		}
	}

	public void setAddUsers() {
		setAddMemberOrAddUser(false);
		ajouterMembreButton.setEnabled(true);
		ajouterMembreButton.setText("Creer un nouveau utilisateur");
		frameServeur.setUserButtonEnable();
	}

	public void setAddMembers() {
		setAddMemberOrAddUser(true);
		ajouterMembreButton.setEnabled(true);
		frameServeur.setMemberButtonEnable();
		ajouterMembreButton.setText("Ajouter un membre");
	}

	public boolean isAddMemberOrAddUser() {
		return addMemberOrAddUser;
	}

	private void setAddMemberOrAddUser(boolean addMemberOrAddUser) {
		this.addMemberOrAddUser = addMemberOrAddUser;
	}

}
