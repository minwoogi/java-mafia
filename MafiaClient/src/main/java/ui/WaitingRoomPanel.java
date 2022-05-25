package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;

import information.RoomInf;

public class WaitingRoomPanel extends JPanel {
	RoomInf roomInf;
	private JPanel personNumPanel;
	private JPanel roomNamePanel;
	private JPanel roomStatePanel;
	private JPanel joinRoomPanel;
	private JButton levelTf; // * 레벨 * //
	private JButton tierTf; // * 티어 * //
	private JButton nickNameTf; // * 닉네임 *//
	private JButton stateTf; // * 준비상태 * //
	private Font tfFont;

	public WaitingRoomPanel(RoomInf roomInf) {
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

		levelTf = new JButton(new ImageIcon("btnImg/personCount.png"));
		tierTf = new JButton(new ImageIcon("btnImg/roomName.png"));
		nickNameTf = new JButton(new ImageIcon("btnImg/roomState.png"));
		stateTf = new JButton(new ImageIcon("btnImg/joinRoom.png"));

		tfFont = new Font("", Font.BOLD, 15);
	}

	public void setComponents() {
		personNumPanel.setLayout(new BorderLayout());
		roomNamePanel.setLayout(new BorderLayout());
		roomStatePanel.setLayout(new BorderLayout());
		joinRoomPanel.setLayout(new BorderLayout());

		levelTf.setFont(tfFont);
		tierTf.setFont(tfFont);
		nickNameTf.setFont(tfFont);
		stateTf.setFont(tfFont);

		levelTf.setPreferredSize(new Dimension(70, 50));
		tierTf.setPreferredSize(new Dimension(240, 50));
		nickNameTf.setPreferredSize(new Dimension(90, 50));
		stateTf.setPreferredSize(new Dimension(80, 50));
		stateTf.setPressedIcon(new ImageIcon("btnImg/personCount.png"));

		levelTf.setHorizontalTextPosition(JButton.CENTER); // 텍스트 가운데
		tierTf.setHorizontalTextPosition(JButton.CENTER);
		nickNameTf.setHorizontalTextPosition(JButton.CENTER);
		stateTf.setHorizontalAlignment(JButton.CENTER);

		btnInvisible(levelTf);
		btnInvisible(tierTf);
		btnInvisible(nickNameTf);
		btnInvisible(stateTf);

	}

	public void addComponents() {
		personNumPanel.add(levelTf, BorderLayout.CENTER);
		roomNamePanel.add(tierTf, BorderLayout.CENTER);
		roomStatePanel.add(nickNameTf, BorderLayout.CENTER);
		joinRoomPanel.add(stateTf, BorderLayout.CENTER);

		this.add(personNumPanel);
		this.add(roomNamePanel);
		this.add(roomStatePanel);
		this.add(joinRoomPanel);

	}
	
	

	public void btnInvisible(JButton btn) { // * 버튼 투명화(이미지 보이게) * //
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
	}

}