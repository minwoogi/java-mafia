package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	private Point initialClick;

	public ShowMessage() {
		viewErrorMsg("제목", "ID나 PASSWORD가 잘못 입력 되었습니다");
		//viewInformationMsg("제목", "Information");
		// viewQuestionMsg("제목", " ID나 PASSWORD가 잘못 입력 되었습니다");
		// viewWarningMsg("title","내용");
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
		information = new ImageIcon("optionPaneIcon/information.png");
		initFrame(title, message, information);
	}

	public void viewQuestionMsg(String title, String message) {
		question = new ImageIcon("optionPaneIcon/question.png");
		initFrame(title, message, question);
	}

	public void viewWarningMsg(String title, String message) {
		warning = new ImageIcon("optionPaneIcon/warning.png");
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

		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setText("  " + message);
		textPane.setFont(new Font("", Font.BOLD, 15));
		textPane.insertComponent(new JLabel(type));
//		textPane.setBackground(new Color(238,238,238));

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
		this.addMouseListener(new MoveWindow());
		this.addMouseMotionListener(new MoveWindow());

		setLineWarp(message);
		setVisible(true);
	}

	public void setLineWarp(String msg) { // * LineWapr * //
		int line = msg.length() / 10;
		if (line >= 19) {
			setSize(400, 360);
		} else if (line >= 14) {
			setSize(400, 300);
		} else if (line >= 9) {
			setSize(400, 240);
		}
	}

	class MoveWindow extends MouseAdapter { // * 프레임 이동 * //
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

	public static void main(String[] args) {
		new ShowMessage();
	}

}
