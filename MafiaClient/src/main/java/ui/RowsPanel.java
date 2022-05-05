package ui;

import java.awt.GridLayout;

import javax.swing.*;

public class RowsPanel extends JPanel {

	public RowsPanel() {
		setLayout(new GridLayout(0,1,0,0));
		setVisible(true);

	}

	public void addRoomPanel(RoomPanel roomPanel) {
		this.add(roomPanel);
		this.revalidate();
		this.repaint();
	}

}
