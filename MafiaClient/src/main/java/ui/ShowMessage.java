package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class ShowMessage extends JFrame {

	/**
	 * notification message
	 */

	private ImageIcon error;
	private ImageIcon information;
	private ImageIcon question;
	private ImageIcon warning;
	private JPanel mainPanel;
	private JPanel btnPanel;
	private JButton okBtn;
	private JLabel textLbl;
	private JTextPane textPane;

	public ShowMessage() {
//	    viewErrorMsg("error", "ERROR");
//      viewInformationMsg("Information", "Information");
//		viewQuestionMsg("Question", "Question");
//      viewWarningMsg("Warning","Warning");
//	    letYouKnowYourJob(1);
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

			break;
		}
		case 6: { // * 게임장 내 공지 메시지 (굵은 파란색) title == null * //

			break;
		}
		case 7: { // * 게임장 내 일반 메시지 (굵은 빨간색) title == null * //

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
		String jobText = "";
		switch (job) {
		case 0: {
			jobText = "citizen";
			break;
		}
		case 1: {
			jobText = "mafia";
			break;
		}
		case 2: {
			jobText = "police";
			break;
		}
		case 3: {
			jobText = "doctor";
			break;
		}
		}
		JButton jobBtn = new JButton(new ImageIcon("btnImg/" + jobText + "Btn.png"));
		jobBtn.setPressedIcon(new ImageIcon("btnImg/" + jobText + "PushBtn.png"));

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

		btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout());
		btnPanel.setBackground(new Color(222, 222, 222));

		textLbl = new JLabel();

		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.insertComponent(new JLabel(type));
		textPane.insertComponent(textLbl);
		textPane.setBackground(new Color(222, 222, 222));

		textLbl.setText("   " + message);
		textLbl.setFont(new Font("", Font.BOLD, 15));

		okBtn = new JButton(new ImageIcon("btnImg/showOk.png"));
		okBtn.setPressedIcon(new ImageIcon("btnImg/showOkPush.png"));
		okBtn.setFocusPainted(false);
		okBtn.setContentAreaFilled(false);
		okBtn.setBorderPainted(false);

		mainPanel.add(btnPanel, BorderLayout.SOUTH);
		mainPanel.add(textPane);
		btnPanel.add(okBtn, Panel.CENTER_ALIGNMENT);
		add(mainPanel);

		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		setLineWarp(message);
		setVisible(true);
	}

	public void setLineWarp(String msg) { // * LineWarp * //
		int line = msg.length() / 10;
		if (line >= 19) {
			setSize(400, 360);
		} else if (line >= 14) {
			setSize(400, 300);
		} else if (line >= 9) {
			setSize(400, 240);
		}
	}

	public static void main(String[] args) {
		new ShowMessage();
	}

}