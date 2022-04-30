package Frame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import information.RoomInf;

public class FrameHandler {

	static LoginFrame loginFrame;
	static SignUpFrame signUpFrame;
	static LobbyFrame lobbyFrame;

	public static void failedLogin(boolean loginCheck) {
		if (loginCheck) {
			FrameHandler.getLoginFrame().frame.dispose();
			new LobbyFrame();
		} else {
			JOptionPane.showMessageDialog(loginFrame.panel, "id 또는 password가 잘못 입력되었습니다.", "error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void useId(boolean overlap) {
		if (overlap) {
			JOptionPane.showMessageDialog(loginFrame.panel, "이미 존재하는 아이디입니다.", "Overlap", JOptionPane.ERROR_MESSAGE);
		} else {
			int useId = JOptionPane.showConfirmDialog(signUpFrame.panel, "사용 가능한 id입니다. 사용하시겠습니까?", "id 사용",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (useId == 0) {
				FrameHandler.setTfEditable(FrameHandler.getSignUpFrame().getidTf());
				FrameHandler.getSignUpFrame().getidDoubleCheckBtn().setEnabled(false);
			}
		}
	}

	public static void useNickName(boolean overlap) {
		if (overlap) {
			JOptionPane.showMessageDialog(loginFrame.panel, "이미 존재하는 닉네임입니다.", "Overlap", JOptionPane.ERROR_MESSAGE);
		} else {
			int useNick = JOptionPane.showConfirmDialog(signUpFrame.panel, "사용 가능한 닉네임입니다. 사용하시겠습니까?", "nickname 사용",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (useNick == 0) {
				FrameHandler.setTfEditable(FrameHandler.getSignUpFrame().getnickNameTf());
				FrameHandler.getSignUpFrame().getnickNameCheckBtn().setEnabled(false);
			}
		}
	}

	public static void checkEmailCode(boolean use) {
		if (use) {
			JOptionPane.showMessageDialog(signUpFrame.panel, "인증 번호가 확인됐습니다.", "email OK",
					JOptionPane.INFORMATION_MESSAGE);
			FrameHandler.getSignUpFrame().getcertificationBtn().setEnabled(false);
			FrameHandler.getSignUpFrame().getemailBtn().setEnabled(false);
			FrameHandler.setTfEditable(FrameHandler.getSignUpFrame().getemailTf());
		} else {
			JOptionPane.showMessageDialog(loginFrame.panel, "인증번호가 틀렸습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void checkEmail(boolean overlap, boolean sendComplete) {
		if (overlap) {
			JOptionPane.showMessageDialog(loginFrame.panel, "이미 존재하는 email입니다.", "Overlap", JOptionPane.ERROR_MESSAGE);
		} else {
			if (!sendComplete) {
				JOptionPane.showMessageDialog(loginFrame.panel, "이메일 전송에 실패했습니다.", "Overlap",
						JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(loginFrame.panel, "이메일 전송에 성공했습니다.", "Complete",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public static void completeRegister(boolean isRegister) {
		if (isRegister) {
			JOptionPane.showMessageDialog(signUpFrame.panel, "회원 가입이 정상적으로 완료 됐습니다.", "회원가입 완료",
					JOptionPane.INFORMATION_MESSAGE);
			signUpFrame.frame.dispose();
			new LoginFrame();
		} else {
			JOptionPane.showMessageDialog(signUpFrame.panel, "회원 가입에 실패하였습니다...", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void updateTierImage(int tier, JLabel lbl) { // * 로비나 방안에서 tier 표시 * //
		lbl.setIcon(new ImageIcon("tierImage/" + tier + ".jpg"));
	}

	public static void updateLevel(int level, JLabel lbl) { // * 로비나 방안에서 level 표시 * //
		lbl.setFont(new Font("", Font.PLAIN, 18));
		lbl.setText("LV " + level);
	}

	public static void updateExpBar(int exp, JProgressBar bar) {  // * 경험치 퍼센트 표시 * //
		bar.setValue(exp);
	}

	public static void UpdateNickName(String nickName, JLabel lbl) { // * 로비나 방안에서 닉네임 표시 * //
		lbl.setText(nickName);
	}

	public static void setTfEditable(JTextField tf) { // * 회원가입시 사용할 ID나 NICKNAME결정되면 색변경 * //
		tf.setEditable(false);
		tf.setForeground(Color.RED);
	}

	public static void addRoomPanel(int roomId, int currentStaff, int headCount, String roomName, boolean roomState) {
		RoomInf roomInf = new RoomInf(roomId, currentStaff, headCount, roomName, roomState);
		RoomPanel roomPanel = new RoomPanel(roomInf);
		roomPanel.setRoomInfTf(roomInf);
		FrameHandler.getLobbyFrame().rowsPanel.addRoomPanel(roomPanel);
	}

	public static void setLoginFrame(LoginFrame loginFrame) {
		FrameHandler.loginFrame = loginFrame;
	}

	public static LoginFrame getLoginFrame() {
		return FrameHandler.loginFrame;
	}

	public static SignUpFrame getSignUpFrame() {
		return FrameHandler.signUpFrame;
	}

	public static void setSignUpFrame(SignUpFrame signUpFrame) {
		FrameHandler.signUpFrame = signUpFrame;
	}

	public static LobbyFrame getLobbyFrame() {
		return FrameHandler.lobbyFrame;
	}

	public static void setLobbyFrame(LobbyFrame robyFrame) {
		FrameHandler.lobbyFrame = robyFrame;
	}

}
