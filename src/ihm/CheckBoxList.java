package ihm;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import utilisateur.Utilisateur;

@SuppressWarnings("serial")
public class CheckBoxList extends JList<JCheckBox> {
	DefaultListModel<JCheckBox> lmRef =  new DefaultListModel<>();
	
	public CheckBoxList() {
		setCellRenderer(new CellRenderer());
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int index = locationToIndex(e.getPoint());
				if (index != -1) {
					JCheckBox checkbox = getModel().getElementAt(index);
					checkbox.setSelected(!checkbox.isSelected());
					repaint();
				}
			}
		});
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	protected class CellRenderer implements ListCellRenderer<JCheckBox> {
		@Override
		public Component getListCellRendererComponent(
				JList<? extends JCheckBox> list, JCheckBox value, int index,
				boolean isSelected, boolean cellHasFocus) {
			return value;
		}
	}

	public void initModel(List<Utilisateur> list){
		DefaultListModel<JCheckBox> temp = new DefaultListModel<JCheckBox>();
		for(Utilisateur u : list)
			temp.addElement(new JCheckBox(u.toString()));
		this.setModel(temp);
		lmRef = temp;
	}
	
	public void addCheckbox(JCheckBox checkBox) {
		lmRef.addElement(checkBox);
		this.setModel(lmRef);
		this.repaint();
	}
	
	public List<Utilisateur> getElementsSelected(List<Utilisateur> listRef){
		List<Utilisateur> listToReturn = new ArrayList<>();
		for (int i = 0; i < this.getModel().getSize(); i++)
			if (this.getModel().getElementAt(i).isSelected())
				listToReturn.add(listRef.get(i));
		return listToReturn;
	}

	public DefaultListModel<JCheckBox> getLmRef() {
		return lmRef;
	}	
	
}
