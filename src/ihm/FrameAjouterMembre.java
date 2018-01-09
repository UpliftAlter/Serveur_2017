package ihm;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class FrameAjouterMembre extends JFrame {
	private JButton ajouterButton = new JButton("Ajouter");
	private JButton annulerButton = new JButton("Annuler");
	private JScrollPane checkBoxPanel = new JScrollPane();
	private JPanel mainPanel = new JPanel();
	private JTextField rechercheTextField = new JTextField("Tapez pour rechercher");
	private FrameServeur frameServeur;
	private CheckBoxList cbl = new CheckBoxList();

	private DefaultListModel<JCheckBox> lmRef = new DefaultListModel<JCheckBox>();
	private Font italic = new Font(rechercheTextField.getFont().getFontName(),
			Font.ITALIC, rechercheTextField.getFont().getSize());
	private Font original = rechercheTextField.getFont();

	public FrameAjouterMembre(FrameServeur frameServeur) {
		this.frameServeur = frameServeur;
		initComponents();
	}

	private void initComponents() {
		// Inits
		cbl.initModel(frameServeur.getAllUsers());
		lmRef = cbl.getLmRef();
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		checkBoxPanel.setViewportView(cbl);
		setResizable(false);

		// Events
		ajouterButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ajouterButtonActionPerformed(evt);
			}
		});
		annulerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				annulerButtonActionPerformed(evt);
			}
		});
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				dispose();
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

		// Layout
		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				mainPanel);
		mainPanel.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(checkBoxPanel)
						.addComponent(rechercheTextField)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel1Layout
										.createSequentialGroup()
										.addGap(54, 54, 54)
										.addComponent(ajouterButton)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												133, Short.MAX_VALUE)
										.addComponent(annulerButton)
										.addGap(70, 70, 70)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addComponent(
												rechercheTextField,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												checkBoxPanel,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												370, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																annulerButton)
														.addComponent(
																ajouterButton))
										.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
	}

	private void ajouterButtonActionPerformed(java.awt.event.ActionEvent evt) {
		if (!cbl.getElementsSelected(frameServeur.getAllUsers()).isEmpty()) {
			frameServeur
					.getPanelGroupe()
					.getGroupeSelected()
					.addMembers(
							cbl.getElementsSelected(frameServeur.getAllUsers()));
			frameServeur.getPanelUtilisateur().initModel(
					frameServeur.getPanelGroupe().getGroupeSelected());
			frameServeur
					.getPanelUtilisateur()
					.getVerticalScrollBar()
					.setValue(
							frameServeur.getPanelUtilisateur()
									.getVerticalScrollBar().getMaximum());
			dispose();
		} else {
			JOptionPane.showMessageDialog(frameServeur,
					"Selectionnez au moins un utilisateur");
		}
	}

	private void annulerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		dispose();
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

	private void rechercheTextFieldKeyPressed(java.awt.event.KeyEvent evt) {
		recherche();
	}

	private void recherche() {
		DefaultListModel<JCheckBox> newModel = new DefaultListModel<>();
		CharSequence cs = rechercheTextField.getText().toLowerCase();
		for (int i = 0; i < lmRef.getSize(); i++) {
			String temp = lmRef.getElementAt(i).getText().toLowerCase();
			if (cs != null)
				if (temp.contains(cs))
					newModel.addElement(lmRef.getElementAt(i));
		}
		cbl.setModel(newModel);
		cbl.repaint();
	}
	public CheckBoxList getCbl() {
		return cbl;
	}


}
