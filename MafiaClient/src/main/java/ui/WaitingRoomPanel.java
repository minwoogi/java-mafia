package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import handling.netty.ClientHandler;
import handlinig.packet.WaitingRoomPacket;
import information.RoomInf;
import information.UserInf;

public class WaitingRoomPanel extends JPanel {
	UserInf userInf;
	private JPanel personNumPanel;
	private JPanel roomNamePanel;
	private JPanel roomStatePanel;
	private JPanel joinRoomPanel;
	private JButton levelTf; // * ���� * //
	private JButton tierTf; // * Ƽ�� * //
	private JButton nickNameTf; // * �г��� *//
	private JButton stateBtn; // * �غ���� * //


	private Font tfFont;

	public WaitingRoomPanel(UserInf userInf) {
		this.userInf = userInf;
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

		levelTf = new JButton(new ImageIcon("btnImg/levelTf.png"));
		tierTf = new JButton(new ImageIcon("btnImg/tierTf.png"));
		nickNameTf = new JButton(new ImageIcon("btnImg/nickNameTf.png"));
		stateBtn = new JButton(new ImageIcon("btnImg/stateFalse.png"));

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
		stateBtn.setFont(tfFont);

		levelTf.setPreferredSize(new Dimension(90, 50));
		tierTf.setPreferredSize(new Dimension(90, 50));
		nickNameTf.setPreferredSize(new Dimension(200, 50));
		stateBtn.setPreferredSize(new Dimension(100, 50));
		
		levelTf.setHorizontalTextPosition(JButton.CENTER); // �ؽ�Ʈ ���  
		tierTf.setHorizontalTextPosition(JButton.CENTER);
		nickNameTf.setHorizontalTextPosition(JButton.CENTER);

		btnInvisible(levelTf);
		btnInvisible(tierTf);
		btnInvisible(nickNameTf);
		btnInvisible(stateBtn);
		
	}
	
	public void setRoomInfTf(UserInf userInf) {
		levelTf.setText("LV."+userInf.getLevel());
		tierTf.setText(userInf.getTier()+"");
		nickNameTf.setText(userInf.getUserNick());
		if (userInf.isReady() == false) {
			stateBtn.setIcon(new ImageIcon("btnImg/stateFalse.png"));
		} else {
			stateBtn.setIcon(new ImageIcon("btnImg/stateTrue.png"));
		}
	}
	
	public void setLeaderNick(WaitingRoomPanel waitingRoomPanel) {
	}

	public void addComponents() {
		personNumPanel.add(levelTf, BorderLayout.CENTER);
		roomNamePanel.add(tierTf, BorderLayout.CENTER);
		roomStatePanel.add(nickNameTf, BorderLayout.CENTER);
		joinRoomPanel.add(stateBtn, BorderLayout.CENTER);

		this.add(personNumPanel);
		this.add(roomNamePanel);
		this.add(roomStatePanel);
		this.add(joinRoomPanel);

	}
	
	

	public void btnInvisible(JButton btn) { // * ��ư ����ȭ(�̹��� ���̰�) * //
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
	}
	
	public JButton getStateBtn() {
		return stateBtn;
	}


}