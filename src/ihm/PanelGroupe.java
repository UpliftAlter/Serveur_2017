package ihm;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;

import classes.Groupe;

@SuppressWarnings("serial")
public class PanelGroupe extends JScrollPane {
	private FrameServeur frameServeur;
	private JPopupMenu popupMenu = new JPopupMenu();
	private JMenuItem supprimer = new JMenuItem("Supprimer");
	private JMenuItem modifier = new JMenuItem("Modifier");
	private JButton ajouterGroupeButton = new JButton("Ajouter un groupe");
	private JPanel mainPanel = new JPanel();
	private JList<Groupe> listeGroupe = new JList<>();
	private JScrollPane listeGroupeScrollPanel = new JScrollPane();
	private JTextField rechercheTextField = new JTextField("Tapez pour rechercher");
	private DefaultListModel<Groupe> lmRef = new DefaultListModel<>();
	private Font italic = new Font(rechercheTextField.getFont().getFontName(), Font.ITALIC,
			rechercheTextField.getFont().getSize());
	private Font original = rechercheTextField.getFont();

	public PanelGroupe(FrameServeur frameServeur) {
		this.frameServeur = frameServeur;
		initComponents();
	}

	private void initComponents() {
		// Inits
		initModel();
		listeGroupe.setCellRenderer(new RenduGroupeCell());
		listeGroupeScrollPanel.setViewportView(listeGroupe);
		rechercheTextField.setFont(italic);
		popupMenu.add(modifier);
		popupMenu.add(supprimer);

		// Events
		modifier.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				modifierActionPerformed(evt);
			}
		});

		supprimer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				supprimerActionPerformed(evt);
			}
		});
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
		listeGroupe.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int index = listeGroupe.locationToIndex(evt.getPoint());
				listeGroupe.setSelectedIndex(index);
				if (SwingUtilities.isRightMouseButton(evt) && !isFirstGroup()) {

					doPop(evt);
				}
			}
		});
		// Layout
		javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout.setHorizontalGroup(mainPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(mainPanelLayout.createSequentialGroup()
						.addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(rechercheTextField, javax.swing.GroupLayout.Alignment.LEADING,
										javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
								.addComponent(listeGroupeScrollPanel, javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(ajouterGroupeButton, javax.swing.GroupLayout.Alignment.LEADING,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addGap(0, 0, 0)));
		mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(mainPanelLayout.createSequentialGroup()
						.addComponent(rechercheTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(listeGroupeScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 402,
								Short.MAX_VALUE)
						.addGap(16, 16, 16).addComponent(ajouterGroupeButton, javax.swing.GroupLayout.PREFERRED_SIZE,
								38, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		this.setViewportView(mainPanel);
	}

	// Events
	private void supprimerActionPerformed(java.awt.event.ActionEvent evt) {
		Groupe temp = listeGroupe.getSelectedValue();
		frameServeur.getServeur().removeGroup(temp);
		lmRef.removeElement(temp);
		listeGroupe.repaint();
	}

	private void modifierActionPerformed(java.awt.event.ActionEvent evt) {
		String temp = JOptionPane.showInputDialog("Entrez le nouveau nom");

		if (temp != null) {
			if (!temp.equals("") && !alreadyExist(temp)) {
				listeGroupe.getSelectedValue().setNomGroupe(temp);
			} else {
				JOptionPane.showMessageDialog(frameServeur, "Le nom est vide ou existe déjà !");
			}
		}

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

	private void listeGroupeValueChanged(javax.swing.event.ListSelectionEvent evt) {
		frameServeur.getPanelUtilisateur().initModel(listeGroupe.getSelectedValue());
		if (listeGroupe.getSelectedValue() != null)
			if (isFirstGroup()) {
				frameServeur.getPanelUtilisateur().setAddUsers();
			} else {
				frameServeur.getPanelUtilisateur().setAddMembers();
			}
	}

	public void ajouterGroupeButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String tempS = JOptionPane.showInputDialog("Nom du groupe: ");
		if (tempS != null) {
			if (!tempS.equals("") && !alreadyExist(tempS)) {
				frameServeur.getServeur().addGroup(new Groupe(tempS));
			} else {
				JOptionPane.showMessageDialog(frameServeur, "Le nom est vide ou existe déjà !");
			}
			initModel();
		}
		getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum());
	}

	// Other
	private boolean alreadyExist(String name) {
		boolean toReturn = false;
		for (int i = 0; i < lmRef.getSize(); i++) {
			Groupe g = lmRef.getElementAt(i);
			if (g.getNomGroupe().equals(name)) {
				toReturn = true;
				break;
			}

		}
		return toReturn;
	}

	public void doPop(MouseEvent e) {
		popupMenu.show(e.getComponent(), e.getX(), e.getY());
	}

	private void recherche() {
		DefaultListModel<Groupe> newModel = new DefaultListModel<>();
		CharSequence cs = rechercheTextField.getText().toLowerCase();
		for (int i = 0; i < lmRef.getSize(); i++) {
			String temp = lmRef.getElementAt(i).toString().toLowerCase();
			if (cs != null)
				if (temp.contains(cs))
					newModel.addElement(lmRef.getElementAt(i));
		}
		listeGroupe.setModel(newModel);
		listeGroupe.repaint();
	}

	public void initModel() {
		DefaultListModel<Groupe> temp = new DefaultListModel<Groupe>();
		if (!frameServeur.getServeur().getAllGroups().isEmpty())
			for (Groupe g : frameServeur.getServeur().getAllGroups())
				temp.addElement(g);
		lmRef = temp;
		listeGroupe.setModel(lmRef);
		listeGroupe.repaint();
	}

	public boolean isFirstGroup() {
		return listeGroupe.getSelectedValue().getNomGroupe().equals("Tous les utilisateurs");
	}

	public Groupe getGroupeSelected() {
		return listeGroupe.getSelectedValue();
	}

}
