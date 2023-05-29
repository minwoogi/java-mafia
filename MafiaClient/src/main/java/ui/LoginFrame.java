package ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import handling.netty.ClientHandler;
import handlinig.packet.LoginPacket;

/**
 * frame for login
 * 
 * Initialize() 초기화 newComponents() 새로운 컴포넌트 생성 setComponents() 컴포넌트 셋팅
 * addComponents() 컴포넌트 추가 addActionBtn(); 버튼에 액션리스너 추가
 */

public class LoginFrame {

	public JFrame frame;
	private Image background;
	private JTextField idTf;
	private JPasswordField pwdTf;
	private JButton signUpBtn;
	private JButton loginBtn;
	private Font tfFont;
	public JPanel panel;

	public LoginFrame() {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initialize() {
		FrameHandler.setLoginFrame(this);
		background = new ImageIcon("backgroundImage/mafiaBackground.png").getImage();
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		panel = new MyPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		newComponents();
		setComponents();
		addComponents();
		addActionBtn();

	}

	public void newComponents() {

		loginBtn = new JButton(new ImageIcon("btnImg/loginButton.png"));
		signUpBtn = new JButton(new ImageIcon("btnImg/signUpButton.png"));
		idTf = new JTextField();
		pwdTf = new JPasswordField();
		tfFont = new Font("", Font.BOLD, 20);
	}

	public void setComponents() {
		panel.setLayout(null);

		idTf.setFont(tfFont);
		idTf.setOpaque(false);
		idTf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		idTf.setBounds(133, 255, 295, 55);

		pwdTf.setBounds(133, 325, 295, 55);
		pwdTf.setOpaque(false);
		pwdTf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		pwdTf.setFont(tfFont);

		signUpBtn.setPressedIcon(new ImageIcon("btnImg/signUpPush.png"));
		signUpBtn.setBounds(50, 520, 389, 58);
		signUpBtn.setFocusPainted(false);
		signUpBtn.setContentAreaFilled(false);
		signUpBtn.setBorderPainted(false);
		

		loginBtn.setPressedIcon(new ImageIcon("btnImg/loginPush.png"));
		loginBtn.setBounds(50, 440, 389, 58);
		loginBtn.setFocusPainted(false);
		loginBtn.setContentAreaFilled(false);
		loginBtn.setBorderPainted(false);
	}

	public void addComponents() {
		panel.add(idTf);
		panel.add(pwdTf);
		panel.add(signUpBtn);
		panel.add(loginBtn);
	}

	public void addActionBtn() {
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("["+idTf.getText()+"]");
				ClientHandler.send(LoginPacket.makeLoginPacket(idTf.getText(), pwdTf.getText()));
			}
		});

		signUpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new SignUpFrame();
			}
		});
	}

	class MyPanel extends JPanel {
		public MyPanel() {
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(background, 0, 0, this);
		}
	}
	
	public static void main(String[] args) {
		new LoginFrame();
	}
}
