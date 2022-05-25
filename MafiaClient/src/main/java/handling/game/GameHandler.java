package handling.game;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import handling.netty.ClientHandler;
import handlinig.packet.GamePacket;
import ui.GameFrame;

public class GameHandler {

	static GameFrame gameFrame;

	public static void setGameFrame(GameFrame gameFrame) {
		GameHandler.gameFrame = gameFrame;
	}

	public static GameFrame getGameFrame() {
		return GameHandler.gameFrame;
	}

	public void setDebateTime() { // * 토론 시간으로 변경 * //

	}

	public static void setTimer(JTextField timer) { // * 투표 시간 타이머 * //
		timer.setText("남은시간 05 : 00");
		VoteTimer voteTimer = new VoteTimer();
		timer.setText(voteTimer.timer);
	}

	public void setVoteChance() {
		int voteChance = 1;
	}

	public void viewDeadPerson() { // * 살해당한 사람 알림 * //

	}

	public static void sendVotePerson(String nickName) { // * 투표한 사람 서버로 전송 * //
		ClientHandler.send(GamePacket.makeVotePacket(nickName));
	}

	public void setMafiaMode() { // * 마피아 모드 설정 * //
		gameFrame.getChatTf().setForeground(Color.RED);
	}

	public void setCitizenMode() { // * 시민 모드 설정 * //

	}

	public static void setNightText(int day, JTextField nightTf) { // * 몇번째 밤인지 알림 * //
		nightTf.setText(day + "번째 밤");
	}

	public static void addMsg(String nickName, String text, JTextArea chatTa) { // * 채팅 올리기 * //
		chatTa.setText(chatTa.getText() + "\n" + nickName + ": " + text + "\n");
	}

	public static void addPersonList(JPanel panel, String nickName) {
		JButton btn = new JButton();
		panel.add(btn);
		btn.setPreferredSize(new Dimension(80, 80));
		btn.setHorizontalTextPosition(JButton.CENTER); // 텍스트 가운데
		btn.setText(nickName);
	}

	static class VoteTimer extends Thread { // * timer * //
		long time, startTime, offTime;
		String timer;
		long remainingTime = 300;

		public VoteTimer() {
			startTime = System.currentTimeMillis();
		}

		public void update() {
			offTime = System.currentTimeMillis();
			time = (offTime - startTime) / 1000;
			remainingTime = 300 - time;
			timer = convertTime(remainingTime);
		}

		public String convertTime(long time) {
			long minute = remainingTime / 60;
			remainingTime -= 60 * minute;
			String second = remainingTime < 10 ? "0" + remainingTime : remainingTime + "";
			return "남은 시간 " + (minute < 10 ? "0" + minute + " : " : minute + " : ") + second;
		}
		
		public void stopThread() {
			super.interrupt();
		}

		public void run() {
			while (true) {
				if(remainingTime == 0) {
					stopThread();
				}
				update();
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return ;
				}
			}
		}
	}

}
