package ui;

import java.awt.GridLayout;
import javax.swing.*;

import information.UserInf;


public class WaitingRowsPanel extends JPanel {

	public WaitingRowsPanel() {
		setLayout(new GridLayout(0,1,0,0));
		setVisible(true);
		UserInf test1 = new UserInf(1,"nickName",false,2,0);
		WaitingRoomPanel test = new WaitingRoomPanel(test1);
		test.setRoomInfTf(test1);
		addUserPanel(test);

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
