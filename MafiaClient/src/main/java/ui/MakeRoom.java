package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.WindowConstants;
import handling.netty.ClientHandler;
import handlinig.packet.RobyPacket;

public class MakeRoom extends JFrame { 

	private JButton cancelBtn;
	private JButton makeRoomBtn;
	private JTextField roomNameTf;
	private JComboBox<Integer> NumberOfPeople;

	public MakeRoom() {
		setTitle("방만들기");
		setSize(400, 300);
		// setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		newComponents();
		setComponents();
		addComponents();
		addActionBtn();

		setVisible(true);
	}

	public void newComponents() {
		cancelBtn = new JButton("취소");
		makeRoomBtn = new JButton("만들기");
		roomNameTf = new JTextField("방제목");
		Integer[] numberPeople = { 5, 6, 7, 8, 9 };
		NumberOfPeople = new JComboBox<Integer>(numberPeople);
		
	}

	public void setComponents() {
		setLayout(null);
		

		roomNameTf.setBounds(65, 30, 260, 40);
		NumberOfPeople.setBounds(130, 100,150, 40);
		makeRoomBtn.setBounds(90, 200, 90, 40);
		cancelBtn.setBounds(210, 200, 90, 40);
	}

	public void addComponents() {
		add(roomNameTf);
		add(cancelBtn);
		add(makeRoomBtn);
		add(NumberOfPeople);

	}

	public void addActionBtn() {
		makeRoomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientHandler.send(RobyPacket.makeRoomPacket(roomNameTf.getText(),NumberOfPeople.getSelectedIndex()+5));
				dispose();
			}
		});
	}

	public static void main(String[] args) {
		new MakeRoom();
	}
}
