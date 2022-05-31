package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import handling.netty.ClientHandler;
import handlinig.packet.LobbyPacket;
import information.FrameLocation;
import information.RoomInf;

public class LobbyRoomPanel extends JPanel {
	RoomInf roomInf;
	private JPanel personNumPanel;
	private JPanel roomNamePanel;
	private JPanel roomStatePanel;
	private JPanel joinRoomPanel;
	private JButton personNumTf; // * 총원 현재원 * //
	private JButton roomNameTf; // * 방이름 * //
	private JButton roomStateTf; // * 방 상태 *//
	private JButton joinRoomBtn; // * 입장 * //
	private Font tfFont;

	public LobbyRoomPanel(RoomInf roomInf) {
		this.roomInf = roomInf;
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.setPreferredSize(new Dimension(480, 50));
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

		personNumTf = new JButton(new ImageIcon("btnImg/personCount.png"));
		roomNameTf = new JButton(new ImageIcon("btnImg/roomName.png"));
		roomStateTf = new JButton(new ImageIcon("btnImg/roomState.png"));
		joinRoomBtn = new JButton(new ImageIcon("btnImg/joinRoom.png"));

		tfFont = new Font("", Font.BOLD, 15);
	}

	public void setComponents() {
		personNumPanel.setLayout(new BorderLayout());
		roomNamePanel.setLayout(new BorderLayout());
		roomStatePanel.setLayout(new BorderLayout());
		joinRoomPanel.setLayout(new BorderLayout());

		personNumTf.setFont(tfFont);
		roomNameTf.setFont(tfFont);
		roomStateTf.setFont(tfFont);
		joinRoomBtn.setFont(tfFont);

		personNumTf.setPreferredSize(new Dimension(70, 50));
		roomNameTf.setPreferredSize(new Dimension(240, 50));
		roomStateTf.setPreferredSize(new Dimension(90, 50));
		joinRoomBtn.setPreferredSize(new Dimension(80, 50));
		joinRoomBtn.setPressedIcon(new ImageIcon("btnImg/joinRoomPush.png"));

		personNumTf.setHorizontalTextPosition(JButton.CENTER); // 텍스트 가운데
		roomNameTf.setHorizontalTextPosition(JButton.CENTER);
		roomStateTf.setHorizontalTextPosition(JButton.CENTER);
		joinRoomBtn.setHorizontalAlignment(JButton.CENTER);

		btnInvisible(personNumTf);
		btnInvisible(roomNameTf);
		btnInvisible(roomStateTf);
		btnInvisible(joinRoomBtn);

		joinRoomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientHandler.send(LobbyPacket.makeEnterRoomPacket(roomInf.getRoomId()));
			}
		});

	}

	public void addComponents() {
		personNumPanel.add(personNumTf, BorderLayout.CENTER);
		roomNamePanel.add(roomNameTf, BorderLayout.CENTER);
		roomStatePanel.add(roomStateTf, BorderLayout.CENTER);
		joinRoomPanel.add(joinRoomBtn, BorderLayout.CENTER);

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
	
	

	public void btnInvisible(JButton btn) { // * 버튼 투명화(이미지 보이게) * //
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
	}

}