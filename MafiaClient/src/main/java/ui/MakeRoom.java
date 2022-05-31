package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import handling.netty.ClientHandler;
import handlinig.packet.LobbyPacket;

public class MakeRoom extends JFrame {

	private JButton cancelBtn;
	private JButton makeRoomBtn;
	private JTextField roomNameTf;
	private JComboBox<Integer> numberOfPeople;
	private Image background;
	private JPanel panel;
	private Point initialClick;

	public MakeRoom() {
		setSize(400, 250);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		newComponents();
		setComponents();
		addComponents();
		addActionBtn();
		setUndecorated(true);
		this.addMouseListener(new MoveWindows());
		this.addMouseMotionListener(new MoveWindows());

		setVisible(true);
	}

	public void newComponents() {
		panel = new BackGroundPanel();
		cancelBtn = new JButton(new ImageIcon("btnImg/makeRoomCancel.png"));
		makeRoomBtn = new JButton(new ImageIcon("btnImg/makeRoomBtn.png"));
		roomNameTf = new JTextField("방제목");
		Integer[] numberPeople = { 5, 6, 7, 8, 9 };
		numberOfPeople = new JComboBox<Integer>(numberPeople);
		background = new ImageIcon("backgroundImage/makeRoomBack.png").getImage();
	}

	public void setComponents() {

		panel.setLayout(null);
		roomNameTf.setBounds(75, 52, 260, 40);
		numberOfPeople.setBounds(125, 110, 150, 40);
		makeRoomBtn.setBounds(90, 170, 90, 40);
		cancelBtn.setBounds(210, 170, 90, 40);

		numberOfPeople.setBackground(Color.BLACK);
		numberOfPeople.setForeground(Color.WHITE);
		numberOfPeople.setOpaque(false);
		numberOfPeople.setFont(new Font("", Font.BOLD, 20));

		roomNameTf.setOpaque(false);
		roomNameTf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		roomNameTf.setFont(new Font("", Font.BOLD, 15));

		makeRoomBtn.setPressedIcon(new ImageIcon("btnImg/makeRoomBtnPush.png"));
		cancelBtn.setPressedIcon(new ImageIcon("btnImg/makeRoomCancelPush.png"));

		btnInvisible(makeRoomBtn);
		btnInvisible(cancelBtn);
	}

	public void addComponents() {
		add(panel);
		panel.add(roomNameTf);
		panel.add(cancelBtn);
		panel.add(makeRoomBtn);
		panel.add(numberOfPeople);

	}

	public void addActionBtn() {
		makeRoomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientHandler
						.send(LobbyPacket.makeRoomPacket(roomNameTf.getText(), numberOfPeople.getSelectedIndex() + 5));
				dispose();
			}
		});

		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public void btnInvisible(JButton btn) { // * 버튼 투명화(이미지 보이게) * //
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
	}

	class BackGroundPanel extends JPanel {
		public BackGroundPanel() {
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(background, 0, 0, this);
		}
	}

	class MoveWindows extends MouseAdapter { // * 프레임 이동 * //
		public void mousePressed(MouseEvent e) {
			initialClick = e.getPoint();
			getComponentAt(initialClick);
		}

		public void mouseDragged(MouseEvent e) {
			JFrame jf = (JFrame) e.getSource();

			int thisX = jf.getLocation().x;
			int thisY = jf.getLocation().y;

			int xMoved = e.getX() - initialClick.x;
			int yMoved = e.getY() - initialClick.y;

			int X = thisX + xMoved;
			int Y = thisY + yMoved;
			jf.setLocation(X, Y); 
		}
	}

	public static void main(String[] arsg) {
		new MakeRoom();
	}

}
