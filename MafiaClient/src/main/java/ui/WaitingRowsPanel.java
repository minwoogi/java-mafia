package ui;

import java.awt.GridLayout;
import javax.swing.*;


public class WaitingRowsPanel extends JPanel {

	public WaitingRowsPanel() {
		setLayout(new GridLayout(0,1,0,0));
		setVisible(true);

	}

	public void addRoomPanel(WaitingRoomPanel roomPanel) {
		this.add(roomPanel);
		this.revalidate();
		this.repaint();
	}
	
	public void removeAllPanel() {
		this.removeAll();
		this.revalidate();
		this.repaint();
	}

}
