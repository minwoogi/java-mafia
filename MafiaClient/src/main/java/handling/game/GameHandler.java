package handling.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import handling.netty.ClientHandler;
import handlinig.packet.GamePacket;
import ui.GameFrame;
import ui.ShowMessage;

public class GameHandler {

	static GameFrame gameFrame;

	public static void setGameFrame(GameFrame gameFrame) {
		GameHandler.gameFrame = gameFrame;
	}

	public static GameFrame getGameFrame() {
		return GameHandler.gameFrame;
	}

	public static void setTimer(long remainTime) { // * ��ǥ �ð� Ÿ�̸� * //
		VoteTimer voteTimer = new VoteTimer(remainTime);
	}

	public static void sendVotePerson(String nickName) { // * ��ǥ�� ��� ������ ���� * //
		ClientHandler.send(GamePacket.makeVotePacket(nickName));
	}

	public void setMafiaMode() { // * ���Ǿ� ��� ���� * //
		gameFrame.getChatTf().setForeground(Color.RED);
	}

	public static void setNightText(int day, JTextField nightTf) { // * ���° ������ �˸� * //
		nightTf.setText(day + "��° ��");
	}

	public static void addMsg(String nickName, String text, JTextArea chatTa) { // * ä�� �ø��� * //
		chatTa.setText(chatTa.getText() + "\n" + nickName + ": " + text + "\n");
	}

	public static void addPersonList(JPanel panel, String nickName) {
		JButton btn = new JButton(new ImageIcon("job/4.png")); // * ����ǥ ������ * //
		personBtnSetting(btn, nickName);
		panel.add(btn);
	}

	public static void addDoubtList(JPanel panel, String nickName) {
		JButton btn = new JButton(new ImageIcon("job/4.png")); // * ����ǥ ������ * //
		doubtBtnSetting(btn, nickName);
		panel.add(btn);
	}

	public static void personBtnSetting(JButton btn, String nickName) { // * �ο� ��ư ���� * //
		btn.setPreferredSize(new Dimension(80, 80));
		btn.setHorizontalTextPosition(JButton.CENTER); // �ؽ�Ʈ ���
		btn.setFont(new Font("", Font.BOLD, 15));
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		btn.setText(nickName);
		GameFrame.lineOverRap(btn);
	}

	public static void doubtBtnSetting(JButton btn, String nickName) { // * ���� �ǽ� ��ư ���� * //
		btn.setPreferredSize(new Dimension(80, 80));
		btn.setHorizontalTextPosition(JButton.CENTER); // �ؽ�Ʈ ���
		btn.setFont(new Font("", Font.BOLD, 15));
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		btn.setText(nickName);
		GameFrame.lineOverRap(btn);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowMessage choiceJob = new ShowMessage();
				choiceJob.doubtJob(btn);
			}
		});
	}

	public static void jobImgSetting(JButton btn, int job) { // * �ο� ��ư ���� �� �̹��� ���� * //
		// 0 - �ù�
		// 1 - ���Ǿ�
		// 2 - ����
		// 3 - �ǻ�
		// 4 - ����ǥ
		btn.setIcon(new ImageIcon("job/" + job + ".png"));
		btn.setPressedIcon(new ImageIcon("job/"+job+".Push.png"));
	}

	static class VoteTimer extends Thread { // * timer * //
		long time, startTime, offTime;
		String timer;
		long timeReceieve; // * �������� ���� �ð� * //
		long remainTime; // * Ÿ�̸� �����ð� * //

		public VoteTimer(long timeReceieve) {
			this.timeReceieve = timeReceieve / 1000;
			remainTime = timeReceieve / 1000;
			startTime = System.currentTimeMillis();
		}

		public void update() {
			offTime = System.currentTimeMillis();
			time = (offTime - startTime) / 1000;
			remainTime = timeReceieve - time;
			timer = convertTime(timeReceieve);
			GameHandler.getGameFrame().getTimer().setText(timer);
		}

		public String convertTime(long time) {
			long minute = remainTime / 60;
			remainTime -= 60 * minute;
			String second = remainTime < 10 ? "0" + remainTime : remainTime + "";
			return "���� �ð� " + (minute < 10 ? "0" + minute + " : " : minute + " : ") + second;
		}

		public void stopThread() {
			super.interrupt();
		}

		public void run() {
			while (true) {
				if (timeReceieve == 0) {
					stopThread();
				}
				update();
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
}
