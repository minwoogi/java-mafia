package ui;

import java.awt.GridLayout;
import javax.swing.*;

import information.RoomInf;

/**
 *   RoomPanel을 RowsPanel에 추가
 **/

public class LobbyRowsPanel extends JPanel {

	public LobbyRowsPanel() {
		setLayout(new GridLayout(0,1,0,0));
		setVisible(true);

	}

	public void addRoomPanel(LobbyRoomPanel roomPanel) {
		this.add(roomPanel);
		this.revalidate();
		this.repaint();
	}
	
	public void removeRoomPanel(LobbyRoomPanel roomPanel) {
		this.remove(roomPanel);
		this.revalidate();
		this.repaint();
	}
	
	public void removeAllPanel() {
		this.removeAll();
		this.revalidate();
		this.repaint();
	}

}
