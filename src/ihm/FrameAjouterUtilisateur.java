package ihm;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utilisateur.Agent;
import utilisateur.DB;
import utilisateur.Enseignant;
import utilisateur.Etudiant;
import utilisateur.TypeUtilisateur;
import utilisateur.Utilisateur;

public class FrameAjouterUtilisateur extends javax.swing.JFrame {
	private JButton annulerButton = new JButton("Annuler");
	private JButton autoPasswordButton = new JButton("Auto");
	private JButton autoUsernameButton = new JButton("Auto");
	private JButton creerButton = new JButton("Creer");
	private JPanel mainPanel = new JPanel();
	private JLabel nomLabel = new JLabel("Nom:");
	private JTextField nomTextField = new JTextField();
	private JLabel passwordLabel = new JLabel("Password:");
	private JTextField passwordTextField = new JTextField();
	private JLabel prenomLabel = new JLabel("Prenom:");
	private JTextField prenomTextField = new JTextField();
	private JLabel usernameLabel = new JLabel("Username:");
	private JTextField usernameTextField = new JTextField();
	private JLabel statusLabel = new JLabel("Status:");
	private JComboBox<String> statusComboBox = new JComboBox<>();
	private FrameServeur frameServeur;

	public FrameAjouterUtilisateur(FrameServeur frameServeur) {
		this.frameServeur = frameServeur;
		initComponents();
	}

	private void initComponents() {
		// inits
		initComboBox();
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setResizable(false);

		// Events
		creerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				creerButtonActionPerformed(evt);
			}
		});

		annulerButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				annulerButtonActionPerformed(evt);
			}
		});

		autoPasswordButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						autoPasswordButtonActionPerformed(evt);
					}
				});
		autoUsernameButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						autoUsernameButtonActionPerformed(evt);
					}
				});

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				dispose();
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
										.addGap(41, 41, 41)
										.addComponent(
												creerButton,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												100,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												88, Short.MAX_VALUE)
										.addComponent(
												annulerButton,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												100,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(49, 49, 49))
						.addGroup(
								mainPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																statusLabel)
														.addGroup(
																mainPanelLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING)
																		.addComponent(
																				prenomLabel)
																		.addComponent(
																				usernameLabel,
																				javax.swing.GroupLayout.Alignment.LEADING))
														.addComponent(nomLabel)
														.addComponent(
																passwordLabel))
										.addGap(18, 18, 18)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																mainPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								false)
																						.addComponent(
																								passwordTextField,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								183,
																								Short.MAX_VALUE)
																						.addComponent(
																								usernameTextField,
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								prenomTextField,
																								javax.swing.GroupLayout.Alignment.LEADING))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								autoUsernameButton,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								autoPasswordButton,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)))
														.addGroup(
																mainPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				mainPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																						.addComponent(
																								nomTextField)
																						.addComponent(
																								statusComboBox,
																								0,
																								185,
																								Short.MAX_VALUE))
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		mainPanelLayout
				.setVerticalGroup(mainPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								mainPanelLayout
										.createSequentialGroup()
										.addGap(26, 26, 26)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																statusLabel)
														.addComponent(
																statusComboBox,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(18, 18, 18)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(nomLabel)
														.addComponent(
																nomTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(23, 23, 23)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																prenomLabel)
														.addComponent(
																prenomTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(18, 18, 18)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																usernameTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																usernameLabel)
														.addComponent(
																autoUsernameButton))
										.addGap(18, 18, 18)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																passwordLabel)
														.addComponent(
																passwordTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																autoPasswordButton))
										.addGap(37, 37, 37)
										.addGroup(
												mainPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																creerButton)
														.addComponent(
																annulerButton))
										.addContainerGap(37, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));

		pack();
	}

	private void autoUsernameButtonActionPerformed(
			java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void autoPasswordButtonActionPerformed(
			java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void creerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		Utilisateur u = null;
		if (checkFields()) {
			switch (getComboBoxValue()) {
			case ETUDIANT:
				u = new Etudiant(nomTextField.getText(),
						prenomTextField.getText(), usernameTextField.getText(),
						passwordTextField.getText());
				break;
			case ENSEIGNANT:
				u = new Enseignant(nomTextField.getText(),
						prenomTextField.getText(), usernameTextField.getText(),
						passwordTextField.getText());
				break;
			case ADMINISTRATIF:
				u = new Agent(nomTextField.getText(),
						prenomTextField.getText(), usernameTextField.getText(),
						passwordTextField.getText(),
						TypeUtilisateur.ADMINISTRATIF);
				break;
			case TECHNIQUE:
				u = new Agent(nomTextField.getText(),
						prenomTextField.getText(), usernameTextField.getText(),
						passwordTextField.getText(), TypeUtilisateur.TECHNIQUE);
				break;

			default:
				break;
			}

			frameServeur.getServeur().addUser(u);
			frameServeur.getPanelGroupe().getGroupeSelected().addMember(u);
			frameServeur.getPanelUtilisateur().initModel(
					frameServeur.getPanelGroupe().getGroupeSelected());
			dispose();

		} else {
			JOptionPane.showMessageDialog(frameServeur,
					"Remplissez tous les champs");
		}
	}

	private void annulerButtonActionPerformed(java.awt.event.ActionEvent evt) {
		dispose();
	}

	private boolean checkFields() {
		return !isEmptyField(nomTextField) && !isEmptyField(passwordTextField)
				&& !isEmptyField(prenomTextField)
				&& !isEmptyField(usernameTextField);
	}

	private boolean isEmptyField(JTextField j) {
		return j.getText().equals("");
	}

	private void initComboBox() {
		DefaultComboBoxModel<String> jcbm = new DefaultComboBoxModel<>(
				new String[] { "Etudiant", "Enseignant", "Agent administratif",
						"Agent technique" });
		statusComboBox.setModel(jcbm);
	}

	private TypeUtilisateur getComboBoxValue() {
		TypeUtilisateur temp = null;
		switch (statusComboBox.getSelectedItem().toString()) {
		case "Etudiant":
			temp = TypeUtilisateur.ETUDIANT;
			break;
		case "Enseignant":
			temp = TypeUtilisateur.ENSEIGNANT;
			break;
		case "Agent administratif":
			temp = TypeUtilisateur.ADMINISTRATIF;
			break;
		case "Agent technique":
			temp = TypeUtilisateur.TECHNIQUE;
			break;
		default:
			break;
		}
		return temp;
	}
}
