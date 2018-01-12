package ihm;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import classes.Enseignant;
import classes.Etudiant;
import classes.Utilisateur;

@SuppressWarnings({ "serial" })
class RenduUtilisateurCell extends DefaultListCellRenderer {
	private JLabel label = new JLabel();
	private ImageIcon userRouge = new ImageIcon("userRouge.png");
	private ImageIcon userBleu = new ImageIcon("userBleu.png");
	private ImageIcon userVert = new ImageIcon("userVert.png");
	private ImageIcon[] tabIcon = { userBleu, userRouge, userVert };
	private Color textSelectionColor = Color.BLACK;
	private Color backgroundSelectionColor = new Color(204, 230, 255);
	private Color textNonSelectionColor = Color.BLACK;
	private Color backgroundNonSelectionColor = Color.WHITE;

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected,
			boolean expanded) {

		if (value != null) {
			Image i = null;
			Utilisateur u = (Utilisateur) value;
			if (u instanceof Etudiant) {
				i = tabIcon[0].getImage().getScaledInstance(30, -1, Image.SCALE_AREA_AVERAGING);
			} else if (u instanceof Enseignant) {
				i = tabIcon[1].getImage().getScaledInstance(30, -1, Image.SCALE_AREA_AVERAGING);
			} else {
				i = tabIcon[2].getImage().getScaledInstance(30, -1, Image.SCALE_AREA_AVERAGING);
			}

			label.setOpaque(true);
			label.setIcon(new ImageIcon(i));
			label.setText(value.toString());
			if (selected) {
				label.setBackground(backgroundSelectionColor);
				label.setForeground(textSelectionColor);
			} else {
				label.setBackground(backgroundNonSelectionColor);
				label.setForeground(textNonSelectionColor);
			}
		}
		return label;
	}
}
