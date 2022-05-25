package handling.game;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
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

	public static void setTimer(JTextField timer, String time) { // * 투표 시간 타이머 * //
		timer.setText(time);
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

	public static String convertTime(long time) {
		time = time / 1000;
		long minute = time / 60;
		time -= 60 * minute;
		String second = time < 10 ? "0" + time : time + "";
		return "남은 시간 " + (minute < 10 ? "0" + minute + " : " : minute + " : ") + second;
	}

}
