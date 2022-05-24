package handling.game;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

	public void setVoteTime() { // * 투표 시간으로 변경 * //

	}

	public void viewDeadPerson() { // * 살해당한 사람 알림 * //

	}

	public void sendVotePerson() { // * 투표한 사람 서버로 전송 * //

	}

	public void setMafiaMode() { // * 마피아 모드 설정 * //

	}

	public void setCitizenMode() { // * 시민 모드 설정 * //

	}

	public static void setNightText(int day, JTextField nightTf) { // * 몇번째 밤인지 알림 * //
		nightTf.setText(day + "번째 밤");
	}

	public static void addPersonList(JButton btn, JPanel panel) {
		panel.add(btn);
	}

}
