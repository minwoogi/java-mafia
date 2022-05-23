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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

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
	private JTextPane textPane;

	public ShowMessage() {
		viewErrorMsg("제목", "zz");
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
		}
	}

	public void viewErrorMsg(String title, String message) {
		error = new ImageIcon("optionPaneIcon/error.png");
		initFrame(title, message, error);
	}

	public void viewInformationMsg(String title, String message) {
		information = new ImageIcon("optionPaneIcon/error.png");
		initFrame(title, message, information);
	}

	public void viewQuestionMsg(String title, String message) {
		question = new ImageIcon("optionPaneIcon/error.png");
		initFrame(title, message, question);
	}

	public void viewWarningMsg(String title, String message) {
		warning = new ImageIcon("optionPaneIcon/error.png");
		initFrame(title, message, warning);
	}

	public void initFrame(String title, String message, ImageIcon type) {
		setTitle(title);
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout());
		btnPanel.setBackground(Color.gray);

		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setText(message);
		textPane.setFont(new Font("",Font.BOLD,20));
		textPane.insertComponent(new JLabel(type));
		
		

		okBtn = new JButton("확인");
		// okBtn.setFocusPainted(false);
		// okBtn.setContentAreaFilled(false);
		// okBtn.setBorderPainted(false);

		mainPanel.add(btnPanel, BorderLayout.SOUTH);
		mainPanel.add(textPane);
		btnPanel.add(okBtn, Panel.CENTER_ALIGNMENT);
		add(mainPanel);

		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		setVisible(true);
	}

	public static void main(String[] args) {
		new ShowMessage();
	}

}
