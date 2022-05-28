package ui;

import java.awt.GridLayout;
import javax.swing.*;

import information.UserInf;


public class WaitingRowsPanel extends JPanel {

	public WaitingRowsPanel() {
		setLayout(new GridLayout(0,1,0,0));
		setVisible(true);

	}

	public void addUserPanel(WaitingRoomPanel userPanel) {
		this.add(userPanel);
		this.revalidate();
		this.repaint();
	}
	
	public void removeUserPanel(WaitingRoomPanel userPanel) {
		this.remove(userPanel);
		this.revalidate();
		this.repaint();
	}
	
	public void removeAllPanel() {
		this.removeAll();
		this.revalidate();
		this.repaint();
	}
	
	

}
