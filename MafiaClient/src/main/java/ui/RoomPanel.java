package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;

import information.RoomInf;

public class RoomPanel extends JPanel {
	RoomInf roomInf;
	private JPanel personNumPanel;
	private JPanel roomNamePanel;
	private JPanel roomStatePanel;
	private JPanel joinRoomPanel;
	private JTextField personNumTf; // * 총원 현재원 * //
	private JTextField roomNameTf; // * 방이름 * //
	private JTextField roomStateTf; // * 방 상태 *//
	private JButton joinRoomBtn;
	private Font tfFont;

	public RoomPanel(RoomInf roomInf) {
		this.roomInf = roomInf;
		setLayout(new GridLayout(0, 4, 0, 0));
		this.setPreferredSize(new Dimension(300, 50));

		newComponents();
		setComponents();
		addComponents();

		setVisible(true);

	}

	public void newComponents() {
		personNumPanel = new JPanel();
		roomNamePanel = new JPanel();
		roomStatePanel = new JPanel();
		joinRoomPanel = new JPanel();
		
		personNumTf = new JTextField("0/0");
		roomNameTf = new JTextField("방이름");
		roomStateTf = new JTextField("대기중");
		
		joinRoomBtn = new JButton("입장");

		tfFont = new Font("", Font.PLAIN, 15);
	}

	public void setComponents() {
		personNumPanel.setLayout(new BorderLayout());
		roomNamePanel.setLayout(new BorderLayout());
		roomStatePanel.setLayout(new BorderLayout());
		joinRoomPanel.setLayout(new BorderLayout());

		personNumTf.setFont(tfFont);
		roomNamePanel.setFont(tfFont);
		roomStatePanel.setFont(tfFont);
	}

	public void addComponents() {
		personNumPanel.add(personNumTf, BorderLayout.CENTER);
		roomNamePanel.add(roomNameTf, BorderLayout.CENTER);
		roomStatePanel.add(roomStateTf, BorderLayout.CENTER);
		joinRoomPanel.add(joinRoomBtn,BorderLayout.CENTER);
		
		this.add(personNumPanel);
		this.add(roomNamePanel);
		this.add(roomStatePanel);
		this.add(joinRoomPanel);

	}

	public void setRoomInfTf(RoomInf roomInf) {
		personNumTf.setText(roomInf.getCurrentStaff() + "/" + roomInf.getHeadCount());
		roomNameTf.setText(roomInf.getRoomName());
		if (roomInf.isRoomState() == false) {
			roomStateTf.setText("대기중");
		} else {
			roomStateTf.setText("게임중");
		}

	}

}
