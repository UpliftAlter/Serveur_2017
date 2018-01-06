package ihm;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

@SuppressWarnings({ "serial" })
class RenduGroupeCell extends DefaultListCellRenderer {

	private JLabel label = new JLabel();
	private Color textSelectionColor = Color.BLACK;
	private Color backgroundSelectionColor = Color.BLUE;
	private Color textNonSelectionColor = Color.BLACK;
	private Color backgroundNonSelectionColor = Color.WHITE;
	private ImageIcon groupeIcon = new ImageIcon("groupIcon.png");

	public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list,
			Object value, int index, boolean selected, boolean expanded) {
		Image i = groupeIcon.getImage().getScaledInstance(35, -1,
				Image.SCALE_AREA_AVERAGING);
		if (value != null) {
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
