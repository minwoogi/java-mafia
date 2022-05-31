package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import handling.netty.ClientHandler;
import handlinig.packet.ShowMsgPacket;

public class ShowMessage extends JFrame {

	/**
	 * notification message
	 */

	private ImageIcon error;
	private ImageIcon information;
	private ImageIcon question;
	private ImageIcon warning;
	private JPanel mainPanel;
	private JPanel subPanel;
	private JPanel btnPanel;
	private JButton okBtn;
	private JTextArea textArea;

	public ShowMessage() {
//	    viewErrorMsg("error", "ERROR");
//		viewInformationMsg("Information", "Information");
//		viewQuestionMsg("Question", "Question");
//      viewWarningMsg("Warning","Warning");
//	    letYouKnowYourJob(1);
//	    doubtJob();
//		gameMsg(7,"투표가 모두 끝났습니다.");
//		showConfirm(1,"Yes or No");
	}

	public ShowMessage(int type, String title, String message) {
		switch (type) {
		case 1: { // * ERROR_MESSAGE * //
			viewErrorMsg(title, message);
			break;
		}
		case 2: { // * INFORMATION_MESSAGE * //
			viewInformationMsg(title, message);
			break;
		}
		case 3: { // * QUESTION_MESSAGE * //
			viewQuestionMsg(title, message);
			break;
		}
		case 4: { // * WARNING_MESSAGE * //
			viewWarningMsg(title, message);
			break;
		}
		case 5: { // * 게임장 내 일반 메시지 (흰색) title == null * //
			gameMsg(type,message);
			break;
		}
		case 6: { // * 게임장 내 공지 메시지 (굵은 파란색) title == null * //
			gameMsg(type, message);
			break;
		}
		case 7: { // * 게임장 내 일반 메시지 (굵은 빨간색) title == null * //
			gameMsg(type, message);
			break;
		}
		}
	}

	public void viewErrorMsg(String title, String message) {
		error = new ImageIcon("optionPaneIcon/error.png");
		Image icon = getToolkit().getImage("optionPaneIcon/error.png");
		setIconImage(icon);
		initFrame(title, message, error);
	}

	public void viewInformationMsg(String title, String message) {
		information = new ImageIcon("optionPaneIcon/information.png");
		Image icon = getToolkit().getImage("optionPaneIcon/information.png");
		setIconImage(icon);
		initFrame(title, message, information);
	}

	public void viewQuestionMsg(String title, String message) {
		question = new ImageIcon("optionPaneIcon/question.png");
		Image icon = getToolkit().getImage("optionPaneIcon/question.png");
		setIconImage(icon);
		initFrame(title, message, question);
	}

	public void viewWarningMsg(String title, String message) {
		warning = new ImageIcon("optionPaneIcon/warning.png");
		Image icon = getToolkit().getImage("optionPaneIcon/warning.png");
		setIconImage(icon);
		initFrame(title, message, warning);
	}

	public void letYouKnowYourJob(int job) {
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);

		setLayout(new BorderLayout());
		// 0 - 시민
		// 1 - 마피아
		// 2 - 경찰
		// 3 - 의사
		// 4 - 물음표
		JButton jobBtn = new JButton(new ImageIcon("job/" + job + ".png"));
		jobBtn.setPressedIcon(new ImageIcon("job/" + job + "push.png"));

		jobBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		add(jobBtn, BorderLayout.CENTER);
		setVisible(true);

	}

	public void initFrame(String title, String message, ImageIcon type) {
		setTitle(title);
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		subPanel = new JPanel();
		subPanel.setLayout(new FlowLayout());
		subPanel.setBackground(new Color(222, 222, 222));
		JLabel imgLbl = new JLabel(type);
		imgLbl.setPreferredSize(new Dimension(50, 50));

		btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout());
		btnPanel.setBackground(new Color(222, 222, 222));

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setText(message);
		textArea.setBackground(new Color(222, 222, 222));
		textArea.setFont(new Font("", Font.BOLD, 15));
		textArea.setPreferredSize(new Dimension(300, 100));

		subPanel.add(imgLbl);
		subPanel.add(textArea);

		okBtn = new JButton(new ImageIcon("btnImg/showOk.png"));
		okBtn.setPressedIcon(new ImageIcon("btnImg/showOkPush.png"));
		okBtn.setFocusPainted(false);
		okBtn.setContentAreaFilled(false);
		okBtn.setBorderPainted(false);

		mainPanel.add(btnPanel, BorderLayout.SOUTH);
		mainPanel.add(subPanel, BorderLayout.CENTER);
		btnPanel.add(okBtn, Panel.CENTER_ALIGNMENT);
		add(mainPanel);

		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		setVisible(true);
	}

	public void showConfirm(int id ,String message) {
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		Image icon = getToolkit().getImage("optionPaneIcon/question.png");
		setIconImage(icon);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout());
		btnPanel.setBackground(new Color(222, 222, 222));

		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setText(message);
		textPane.setBackground(new Color(222, 222, 222));
		textPane.setFont(new Font("", Font.BOLD, 20));

		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		okBtn = new JButton(new ImageIcon("btnImg/showOk.png"));
		okBtn.setPressedIcon(new ImageIcon("btnImg/showOkPush.png"));
		okBtn.setFocusPainted(false);
		okBtn.setContentAreaFilled(false);
		okBtn.setBorderPainted(false);

		JButton cancelBtn = new JButton(new ImageIcon("btnImg/showOk.png"));
		cancelBtn.setFocusPainted(false);
		cancelBtn.setContentAreaFilled(false);
		cancelBtn.setBorderPainted(false);

		mainPanel.add(btnPanel, BorderLayout.SOUTH);
		mainPanel.add(textPane, BorderLayout.CENTER);
		btnPanel.add(okBtn, Panel.CENTER_ALIGNMENT);
		btnPanel.add(cancelBtn, Panel.CENTER_ALIGNMENT);
		add(mainPanel);

		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientHandler.send(ShowMsgPacket.makeMessagePacket(id,true));
				dispose();
			}
		});
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		setVisible(true);

	}

	public void gameMsg(int num, String message) { // * 5,6,7 메세지 * //
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(new Color(222, 222, 222));
		JLabel lbl = new JLabel(message);
		lbl.setHorizontalAlignment(JLabel.CENTER);
		lbl.setFont(lbl.getFont().deriveFont(15.0f));
		switch (num) {
		case 6: {
			lbl.setForeground(Color.RED);
			break;
		}
		case 7: {
			lbl.setForeground(Color.BLUE);
			break;
		}
		}
		add(panel);
		panel.add(lbl);
		setVisible(true);

	}

	public void doubtJob(JButton btn) { // * 직업 의심 메세지 * //
		setSize(400, 250);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		JButton cancelBtn = new JButton(new ImageIcon("btnImg/makeRoomCancel.png"));
		JButton okBtn = new JButton(new ImageIcon("btnImg/makeRoomBtn.png"));
		String[] jobList = { "시민", "마피아", "경찰", "의사", "물음표" };
		JComboBox<String> jobBox = new JComboBox<String>(jobList);
		JPanel panel = new BackGroundPanel();
		panel.setLayout(null);

		jobBox.setBounds(125, 110, 150, 40);
		okBtn.setBounds(90, 170, 90, 40);
		cancelBtn.setBounds(210, 170, 90, 40);

		jobBox.setBackground(Color.BLACK);
		jobBox.setForeground(Color.WHITE);
		jobBox.setOpaque(false);
		jobBox.setFont(new Font("", Font.BOLD, 20));

		okBtn.setPressedIcon(new ImageIcon("btnImg/makeRoomBtnPush.png"));
		cancelBtn.setPressedIcon(new ImageIcon("btnImg/makeRoomCancelPush.png"));

		okBtn.setFocusPainted(false);
		okBtn.setContentAreaFilled(false);
		okBtn.setBorderPainted(false);
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn.setIcon(new ImageIcon("btnImg/" + jobBox.getSelectedIndex() + ".png"));
			}
		});

		cancelBtn.setFocusPainted(false);
		cancelBtn.setContentAreaFilled(false);
		cancelBtn.setBorderPainted(false);
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		add(panel);
		panel.add(cancelBtn);
		panel.add(okBtn);
		panel.add(jobBox);
		setVisible(true);
	}

	class BackGroundPanel extends JPanel { // * 직업 의심 배경 * //
		Image background = new ImageIcon("backgroundImage/selectJob.png").getImage();

		public BackGroundPanel() {
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(background, 0, 0, this);
		}
	}

	public static void main(String[] args) {
		new ShowMessage();
	}

}