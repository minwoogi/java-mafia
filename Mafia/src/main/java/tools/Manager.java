package tools;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;

import client.MafiaClient;
import handling.lobby.Lobby;
import handling.lobby.WaitingRoom;
import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Manager {
	public static Manager manager;
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JList lobby_client;
	private JList room_list;
	private JList room_client;
	private DefaultListModel lby_cl_model = new DefaultListModel();
	private DefaultListModel room_lst_model = new DefaultListModel();
	private DefaultListModel room_cl_model = new DefaultListModel();

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manager window = new Manager();
					window.frame.setVisible(true);
					manager = window;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Manager() {
		initialize();
	}

	public void addRoomClient(WaitingRoom room) {
		int selected = room_list.getSelectedIndex();
		if (selected != -1) {
			String str = room_lst_model.getElementAt(selected).toString();
			if (!str.equals("[" + room.getId() + "] " + room.getName()))
				return;
		}
		room_cl_model.clear();
		for (MafiaClient c : room.getClients()) {
			if (c.getWaitingRoom().getLeader().getAccId() == c.getAccId())
				room_cl_model.addElement("[L] " + c.getCharName());
			else
				room_cl_model.addElement(c.getCharName());
		}
	}

	public void removeRoom(WaitingRoom room) {
		for (int i = 0; i < room_lst_model.getSize(); i++) {
			if (room_lst_model.getElementAt(i).toString().equals("[" + room.getId() + "] " + room.getName()))
				room_lst_model.remove(i);
		}
	}

	public void addRoom(WaitingRoom room) {
		room_lst_model.addElement("[" + room.getId() + "] " + room.getName());
	}

	public void removeLobbyClient(MafiaClient c) {
		for (int i = 0; i < lby_cl_model.getSize(); i++) {
			if (lby_cl_model.getElementAt(i).toString().equals(c.getCharName()))
				lby_cl_model.remove(i);
		}
	}

	public void addLobbyClient(MafiaClient c) {
		lby_cl_model.addElement(c.getCharName());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 949, 617);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Lobby", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(289, 10, 203, 558);
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		lobby_client = new JList(lby_cl_model);
		lobby_client.setFont(new Font("돋움체", Font.PLAIN, 16));
		panel.add(lobby_client, BorderLayout.CENTER);
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"WaitingRoom", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(504, 10, 203, 558);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		room_list = new JList(room_lst_model);
		room_list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selected = room_list.getSelectedIndex();
				String str = room_lst_model.getElementAt(selected).toString();
				for (WaitingRoom room : Lobby.getRooms()) {
					if (str.equals("[" + room.getId() + "] " + room.getName())) {
						manager.addRoomClient(room);
						break;
					}
				}
			}
		});
		room_list.setFont(new Font("돋움체", Font.PLAIN, 15));
		panel_1.add(room_list, BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Packet Send", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(12, 10, 265, 558);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel = new JLabel("Header :");
		lblNewLabel.setBounds(12, 28, 57, 15);
		panel_2.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(67, 25, 186, 21);
		panel_2.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Content : ");
		lblNewLabel_1.setBounds(12, 53, 57, 15);
		panel_2.add(lblNewLabel_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_3.setBounds(12, 71, 241, 260);
		panel_2.add(panel_3);
		panel_3.setLayout(null);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(
				new DefaultComboBoxModel(new String[] { "none", "byte", "boolean", "short", "int", "long", "String" }));
		comboBox.setBounds(12, 10, 69, 23);
		panel_3.add(comboBox);

		textField_1 = new JTextField();
		textField_1.setBounds(93, 11, 136, 21);
		panel_3.add(textField_1);
		textField_1.setColumns(10);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(
				new DefaultComboBoxModel(new String[] { "none", "byte", "boolean", "short", "int", "long", "String" }));
		comboBox_1.setBounds(12, 42, 69, 23);
		panel_3.add(comboBox_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(93, 43, 136, 21);
		panel_3.add(textField_2);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(
				new DefaultComboBoxModel(new String[] { "none", "byte", "boolean", "short", "int", "long", "String" }));
		comboBox_2.setBounds(12, 75, 69, 23);
		panel_3.add(comboBox_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(93, 76, 136, 21);
		panel_3.add(textField_3);

		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setModel(
				new DefaultComboBoxModel(new String[] { "none", "byte", "boolean", "short", "int", "long", "String" }));
		comboBox_3.setBounds(12, 108, 69, 23);
		panel_3.add(comboBox_3);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(93, 109, 136, 21);
		panel_3.add(textField_4);

		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setModel(
				new DefaultComboBoxModel(new String[] { "none", "byte", "boolean", "short", "int", "long", "String" }));
		comboBox_4.setBounds(12, 141, 69, 23);
		panel_3.add(comboBox_4);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(93, 142, 136, 21);
		panel_3.add(textField_5);

		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setModel(
				new DefaultComboBoxModel(new String[] { "none", "byte", "boolean", "short", "int", "long", "String" }));
		comboBox_5.setBounds(12, 174, 69, 23);
		panel_3.add(comboBox_5);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(93, 175, 136, 21);
		panel_3.add(textField_6);

		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setModel(
				new DefaultComboBoxModel(new String[] { "none", "byte", "boolean", "short", "int", "long", "String" }));
		comboBox_6.setBounds(12, 206, 69, 23);
		panel_3.add(comboBox_6);

		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(93, 207, 136, 21);
		panel_3.add(textField_7);

		JButton btnNewButton = new JButton("SEND");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String items[][] = {{comboBox.getSelectedItem().toString(), textField_1.getText()}, {comboBox_1.getSelectedItem().toString(), textField_2.getText()}, {comboBox_2.getSelectedItem().toString(), textField_3.getText()}
								, {comboBox_3.getSelectedItem().toString(), textField_4.getText()}, {comboBox_4.getSelectedItem().toString(), textField_5.getText()}, {comboBox_5.getSelectedItem().toString(), textField_6.getText()}
								, {comboBox_6.getSelectedItem().toString(), textField_7.getText()}};
				MafiaPacketWriter packet = new MafiaPacketWriter(Integer.parseInt(textField.getText().toString()));
				for(String item[] : items) {
					String type = item[0];
					Object value = item[1];
					switch(type) {
					case "none":
						break;
					case "byte":
						packet.write((byte) value);
						break;
					case "boolean":
						if(value.toString().equals("true")) {
							packet.writeBoolean(true);
						} else if(value.toString().equals("false")) {
							packet.writeBoolean(false);
						} else {
							System.out.println("[관리기] 잘 못 된 값을 입력했습니다.");
							return;
						}
						break;
					case "int":
						packet.writeInt((int) value);
						break;
					case "long":
						packet.writeLong((long) value);
						break;
					case "String":
						packet.writeString(value.toString());
						break;
					}
				}
				
			}
		});
		btnNewButton.setBounds(73, 232, 97, 23);
		panel_3.add(btnNewButton);

		JLabel lblNewLabel_2 = new JLabel("Result :");
		lblNewLabel_2.setBounds(12, 341, 57, 15);
		panel_2.add(lblNewLabel_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 366, 241, 182);
		panel_2.add(scrollPane);

		JTextPane txtpnDddd = new JTextPane();
		txtpnDddd.setEditable(false);
		scrollPane.setViewportView(txtpnDddd);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"WaitingRoom", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1_1.setBounds(719, 10, 203, 558);
		frame.getContentPane().add(panel_1_1);
		panel_1_1.setLayout(new BorderLayout(0, 0));

		room_client = new JList(room_cl_model);
		room_client.setFont(new Font("돋움체", Font.PLAIN, 15));
		room_client.setValueIsAdjusting(true);
		panel_1_1.add(room_client, BorderLayout.CENTER);
	}
}
