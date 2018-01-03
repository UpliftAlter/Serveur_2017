package ihm;

import java.awt.Component;
import java.awt.Image;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import serveur.Utilisateur;

@SuppressWarnings({ "serial" })
class RenduUtilisateurCell extends DefaultListCellRenderer {
	private JLabel label = new JLabel();
	private ImageIcon userRouge = new ImageIcon("userRouge.png");
	private ImageIcon userBleu = new ImageIcon("userBleu.png");
	private ImageIcon userVert = new ImageIcon("userVert.png");
	private ImageIcon[] tabIcon = { userBleu, userRouge, userVert };

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean selected, boolean expanded) {
		Image i = tabIcon[index % 3].getImage().getScaledInstance(30, -1,
				Image.SCALE_AREA_AVERAGING);
		if (value != null) {
			label.setIcon(new ImageIcon(i));
			label.setText(value.toString());
		}
		return label;
	}
}
