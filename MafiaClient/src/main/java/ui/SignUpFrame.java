package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import handling.netty.ClientHandler;
import handlinig.packet.SignUpPacket;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *  btnInvisible() - 버튼 투명화
 *  isSamePassword() - 비밀번호 똑같이 입력했는지 확인
 *  checkBtnEnabled() 중복확인 , 이메일 인증 완료됐는지 확인 
 * 
 **/

public class SignUpFrame {
	public JFrame frame;
	private JTextField idTf;
	private JTextField pwdTf;
	private JTextField pwdCheckTf;
	private JTextField nickNameTf;
	private JTextField emailTf;
	private JTextField certificationTf;
	private JButton nickNameCheckBtn;
	private JButton certificationBtn;
	private JButton backBtn;
	private JButton emailBtn;
	private Image background;
	private JButton idDoubleCheckBtn;
	private JButton signUpBtn;
	private Font tfFont;
	public JPanel panel;

	public JTextField getidTf() {
		return idTf;
	}

	public JTextField getnickNameTf() {
		return nickNameTf;
	}

	public JButton getidDoubleCheckBtn() {
		return idDoubleCheckBtn;
	}

	public JButton getnickNameCheckBtn() {
		return nickNameCheckBtn;
	}

	public JButton getcertificationBtn() {
		return certificationBtn;
	}

	public JButton getemailBtn() {
		return emailBtn;
	}

	public JTextField getemailTf() {
		return emailTf;
	}

	public SignUpFrame() {
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
		FrameHandler.setSignUpFrame(this);
		panel = new MyPanel();
		background = new ImageIcon("backgroundImage/signUpBackground.png").getImage();
		frame = new JFrame();
		frame.setBounds(100, 100, 490, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setResizable(false);
		panel.setLayout(null);
		makeComponents();
		moveComponents();
		addComponents();
		setComponents();
		setTfFont();
		setTfBorder();
		addActionComponents();

	}

	public void makeComponents() {
		idTf = new JTextField("Id");
		pwdTf = new JTextField("Password");
		pwdCheckTf = new JTextField("Check Password");
		nickNameTf = new JTextField("NickName");
		emailTf = new JTextField("Email");
		certificationTf = new JTextField("Code");
		signUpBtn = new JButton(new ImageIcon("btnImg/signUpButton.png"));
		certificationBtn = new JButton(new ImageIcon("btnImg/cerBtn.png"));
		nickNameCheckBtn = new JButton(new ImageIcon("btnImg/doubleCheckBtn.png"));
		idDoubleCheckBtn = new JButton(new ImageIcon("btnImg/doubleCheckBtn.png"));
		emailBtn = new JButton(new ImageIcon("btnImg/sendBtn.png"));
		backBtn = new JButton(new ImageIcon("btnImg/backBtn.png"));

	}

	public void moveComponents() {
		idTf.setBounds(75, 170, 248, 44);
		pwdTf.setBounds(75, 240, 320, 44);
		pwdCheckTf.setBounds(75, 310, 320, 44);
		emailTf.setBounds(75, 445, 248, 44);
		nickNameTf.setBounds(75, 380, 248, 44);
		backBtn.setBounds(0, 0, 65, 65);
		certificationTf.setBounds(75, 510, 248, 44);
		certificationBtn.setBounds(343, 504, 102, 58);
		nickNameCheckBtn.setBounds(343, 372, 102, 58);
		signUpBtn.setBounds(55, 580, 389, 58);
		emailBtn.setBounds(343, 439, 102, 58);
		idDoubleCheckBtn.setBounds(343, 164, 102, 58);

	}

	public void setTfBorder() {
		idTf.setOpaque(false);
		pwdTf.setOpaque(false);
		pwdCheckTf.setOpaque(false);
		nickNameTf.setOpaque(false);
		emailTf.setOpaque(false);
		certificationTf.setOpaque(false);
		idTf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		pwdTf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		pwdCheckTf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		nickNameTf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		emailTf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		certificationTf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	}

	public void setComponents() {
		btnInvisible(idDoubleCheckBtn);
		btnInvisible(emailBtn);
		btnInvisible(signUpBtn);
		btnInvisible(nickNameCheckBtn);
		btnInvisible(certificationBtn);
		btnInvisible(backBtn);
		nickNameTf.setColumns(10);
		idDoubleCheckBtn.setPressedIcon(new ImageIcon("btnImg/doubleCheckPush.png"));
		emailBtn.setPressedIcon(new ImageIcon("btnImg/sendPush.png"));
		signUpBtn.setPressedIcon(new ImageIcon("btnImg/signUpPush.png"));
		nickNameCheckBtn.setPressedIcon(new ImageIcon("btnImg/doubleCheckPush.png"));
		certificationBtn.setPressedIcon(new ImageIcon("btnImg/cerPush.png"));
		backBtn.setPressedIcon(new ImageIcon("btnImg/backBtnPush.png"));
		
	}

	public void setTfFont() {
		tfFont = new Font("", Font.BOLD, 18);
		certificationTf.setFont(tfFont);
		idTf.setFont(tfFont);
		pwdTf.setFont(tfFont);
		nickNameTf.setFont(tfFont);
		emailTf.setFont(tfFont);
		pwdCheckTf.setFont(tfFont);
	}

	public void addComponents() {
		panel.add(idTf);
		panel.add(pwdTf);
		panel.add(pwdCheckTf);
		panel.add(nickNameTf);
		panel.add(emailTf);
		panel.add(idDoubleCheckBtn);
		panel.add(signUpBtn);
		panel.add(nickNameCheckBtn);
		panel.add(certificationBtn);
		panel.add(backBtn);
		panel.add(certificationTf);
		panel.add(emailBtn);
	}

	public void btnInvisible(JButton btn) { // * 버튼 투명화(이미지 보이게) * //
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
	}

	public boolean isSamePassword() { // * 비밀번호 확인 * //
		if (pwdTf.getText().equals(pwdCheckTf.getText())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkBtnEnabled() { // * 중복확인 , 이메일 인증 완료됐는지 확인 * //
		if (!idDoubleCheckBtn.isEnabled() && !nickNameCheckBtn.isEnabled() && !certificationBtn.isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	public void addActionComponents() {
		signUpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isSamePassword() && checkBtnEnabled()) { // * 회원 가입 가능할 때 * //
					ClientHandler.send(SignUpPacket.makeSignUpPacket(idTf.getText(), pwdTf.getText(),
							nickNameTf.getText(), emailTf.getText()));
				} else {
					JOptionPane.showMessageDialog(null, "정상적으로 입력되지 않았습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		nickNameCheckBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientHandler.send(SignUpPacket.makeNickOverlapPacket(nickNameTf.getText()));
			}
		});
		certificationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientHandler.send(SignUpPacket.makeCertificationPacket(certificationTf.getText()));
			}
		});
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new LoginFrame();
			}
		});
		emailBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientHandler.send(SignUpPacket.makeEmailPacket(emailTf.getText()));
			}
		});

		idDoubleCheckBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientHandler.send(SignUpPacket.makeIdOverlapPacket(idTf.getText()));
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
}
