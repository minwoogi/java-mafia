package ui;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import handling.game.GameHandler;
import information.ExpInf;
import information.FrameLocation;
import information.LocationInformation;
import information.RoomInf;
import information.UserInf;
import tools.BackgroundMusic;
import ui.ShowMessage.ShowConfirm;
import information.ClientInf;

public class FrameHandler {

	static LoginFrame loginFrame;
	static SignUpFrame signUpFrame;
	static LobbyFrame lobbyFrame;
	static WaitingRoomFrame waitingRoomFrame;
	static InviteFrame inviteFrame;
	static ShowMessage showMessage;
	static ShowConfirm showConfirm;
	static BackgroundMusic bgm;

	public static void playBgm(String file) {
		BackgroundMusic bgm = new BackgroundMusic();
		bgm.playBgm(file);
	}

	public static void playSoundEffect(String file) {
		BackgroundMusic bgm = new BackgroundMusic();
		bgm.playSoundEffect(file);
	}

	public static void stopBgm() {
		try {
			bgm.stopSound();
		} catch (Exception e) {
		}
	}

	public static void useId(boolean overlap) { // * 회원가입시 사용 가능한 ID인지 확인 * //
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

	public static void useNickName(boolean overlap) { // * 회원가입시 사용 가능한 Nickname인지 확인 * //
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

	public static void checkEmailCode(boolean use) { // * 이메일 인증코드 확인 * //
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

	public static void checkEmail(boolean overlap, boolean sendComplete) { // * 회원가입시 사용 가능한 email인지 확인 * //
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

	public static void completeRegister(boolean isRegister) { // * 회원가입 완료 시 메세지 * //
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
		lbl.setText(" LV. " + level);
	}

	public static void updateExpBar(int exp, JProgressBar bar, int level) { // * 경험치 퍼센트 표시 * //
		float tmp = exp;
		bar.setValue((int) ((tmp / ExpInf.needExp(level)) * 100.0));
		bar.setString((int) ((tmp / ExpInf.needExp(level)) * 100.0) + "%  [" + exp + "/" + ExpInf.needExp(level) + "]");
	}

	public static void UpdateNickName(String nickName, JButton btn) { // * 로비나 방안에서 닉네임 표시 * //
		btn.setText(nickName);
	}

	public static void setTfEditable(JTextField tf) { // * 회원가입시 사용할 ID나 NICKNAME결정되면 색변경 * //
		tf.setEditable(false);
		tf.setForeground(Color.RED);
	}

	public static void setLeaderMode(int superId) { // * 방장이면 시작 버튼으로 변경하고 닉네임앞에 [L]붙이기 * //
		if (ClientInf.isLeader()) {
			FrameHandler.getWaitingRoomFrame().getReadyBtn().setIcon(new ImageIcon("btnImg/startGame.png"));
			FrameHandler.getWaitingRoomFrame().getReadyBtn().setPressedIcon(new ImageIcon("btnImg/startGamePush.png"));
		}
		try {
			FrameHandler.getWaitingRoomFrame().userPanel.get(superId).setLeader();
		} catch (NullPointerException e) {
		}
	}

	public static void addInvteUser(String nickName) {
		InviteUserPanel inviteUserPanel = new InviteUserPanel(nickName);
		try {
			FrameHandler.getInviteFrame().inviteRowsPanel.addUserList(inviteUserPanel);
		} catch (Exception e) {

		}
	}

	public static void addRoomPanel(RoomInf roomInf) { // * 로비 프레임에서 방 추가 * //
		LobbyRoomPanel roomPanel = new LobbyRoomPanel(roomInf);
		roomPanel.setRoomInfTf(roomInf);
		FrameHandler.getLobbyFrame().getRoomList().put(roomInf.getRoomId(), roomPanel);
		FrameHandler.getLobbyFrame().getRoomNameList().put(roomInf.getRoomId(), roomInf.getRoomName());
		FrameHandler.getLobbyFrame().rowsPanel.addRoomPanel(roomPanel);
	}

	public static void updateRoomPanel(int roomId, RoomInf roomInf) { // * 로비프레임에서 방정보 수정 * //
		FrameHandler.getLobbyFrame().roomList.get(roomId).setRoomInfTf(roomInf);
	}

	public static void removeRoomPanel(int roomId, LobbyRoomPanel lobbyRoomPanel) { // * 로비 프레임에서 방 삭제 * //
		FrameHandler.getLobbyFrame().rowsPanel.removeRoomPanel(lobbyRoomPanel);
		FrameHandler.getLobbyFrame().getRoomList().remove(roomId);
		FrameHandler.getLobbyFrame().getRoomNameList().remove(roomId);
	}

	public static void addUserPanel(UserInf userInf) { // * 대기실 프레임에서 인원 추가 * //
		WaitingRoomPanel waitingRoomPanel = new WaitingRoomPanel(userInf);
		waitingRoomPanel.setRoomInfTf(userInf);
		FrameHandler.getWaitingRoomFrame().rowsPanel.addUserPanel(waitingRoomPanel);
		FrameHandler.getWaitingRoomFrame().userPanel.put(userInf.getUserId(), waitingRoomPanel);
	}

	public static void updateUserPanel(UserInf userInf) { // * 대기실 프레임에서 정보 업데이트 * //
		FrameHandler.getWaitingRoomFrame().userPanel.get(userInf.getUserId()).setRoomInfTf(userInf);
	}

	public static void removeUserPanel(int userId, WaitingRoomPanel waitingRoomPanel) { // * 대기실 나갔을 경우 삭제 * //
		FrameHandler.getWaitingRoomFrame().rowsPanel.removeUserPanel(waitingRoomPanel);
		FrameHandler.getWaitingRoomFrame().userPanel.remove(userId);
	}

	public static void quitLoginFrame() {
		if (loginFrame.frame == null) {
			return;
		} else {
			loginFrame.frame.dispose();
		}
	}

	public static void quitLobbyFrame() {
		if (FrameHandler.getLobbyFrame() == null) {
			return;
		} else {
			FrameLocation.X = FrameHandler.getLobbyFrame().getX();
			FrameLocation.Y = FrameHandler.getLobbyFrame().getY();
			lobbyFrame.dispose();
			FrameHandler.setLobbyFrame(null);
		}
	}

	public static void quitWaittingFrame() {
		if (FrameHandler.getWaitingRoomFrame() == null) {
			return;
		} else {
			FrameLocation.X = FrameHandler.getWaitingRoomFrame().getX();
			FrameLocation.Y = FrameHandler.getWaitingRoomFrame().getY();
			waitingRoomFrame.dispose();
			FrameHandler.setWaitingRoomFrame(null);
		}
	}

	public static void quitGameFrame() {
		if (GameHandler.getGameFrame() == null) {
			return;
		} else {
			FrameLocation.X = GameHandler.getGameFrame().getX();
			FrameLocation.Y = GameHandler.getGameFrame().getY();
			GameHandler.getGameFrame().dispose();
			GameHandler.setGameFrame(null);
		}
	}

	public static void setFrameLocation() { // * 현재 프레임 위치 얻기 * //
		if (GameHandler.getGameFrame() == null) {
			return;
		} else {
			FrameLocation.X = GameHandler.getGameFrame().getX();
			FrameLocation.Y = GameHandler.getGameFrame().getY();
		}
		if (FrameHandler.getWaitingRoomFrame() == null) {
			return;
		} else {
			FrameLocation.X = FrameHandler.getWaitingRoomFrame().getX();
			FrameLocation.Y = FrameHandler.getWaitingRoomFrame().getY();
		}
		if (FrameHandler.getLobbyFrame() == null) {
			return;
		} else {
			FrameLocation.X = FrameHandler.getLobbyFrame().getX();
			FrameLocation.Y = FrameHandler.getLobbyFrame().getY();
		}
	}

	public static void closeMsg(int msgId) { // * CLOSE_MESSAGE * //
		try {
			if (getShowConfirm().msgId == msgId) {
				getShowConfirm().dispose();
			}
		} catch (NullPointerException e) {

		}
	}

	public static void warp(int location) { // * CHANGE_LOCATION * //
		stopBgm();
		FrameHandler.setFrameLocation();
		FrameHandler.quitLoginFrame();
		FrameHandler.quitGameFrame();
		FrameHandler.quitLobbyFrame();
		FrameHandler.quitWaittingFrame();
		switch (location) {
		case LocationInformation.LOBBY: { // * 로비 입장 * //
			new LobbyFrame();
			playBgm("bgm/ofeliasdream.wav");
			FrameHandler.updateTierImage(ClientInf.getTier(), FrameHandler.getLobbyFrame().getTierLabel());
			FrameHandler.updateLevel(ClientInf.getLevel(), FrameHandler.getLobbyFrame().getLevelLabel());
			FrameHandler.UpdateNickName(ClientInf.getNickName(), FrameHandler.getLobbyFrame().getNickNameLabel());
			FrameHandler.updateExpBar(ClientInf.getExp(), FrameHandler.getLobbyFrame().getExpBar(),
					ClientInf.getLevel()); // * 경험치 표시 * //
			break;
		}
		case LocationInformation.WAITING_ROOM: { // * 대기실 입장 * //
			playBgm("bgm/creepy.wav");
			new WaitingRoomFrame();
			ClientInf.setReadyState(false);
			FrameHandler.updateTierImage(ClientInf.getTier(), FrameHandler.getWaitingRoomFrame().getTierLabel());
			FrameHandler.updateLevel(ClientInf.getLevel(), FrameHandler.getWaitingRoomFrame().getLevelLabel());
			FrameHandler.UpdateNickName(ClientInf.getNickName(), FrameHandler.getWaitingRoomFrame().getNickNameLabel());
			FrameHandler.updateExpBar(ClientInf.getExp(), FrameHandler.getWaitingRoomFrame().getExpBar(),
					ClientInf.getLevel()); // * 경험치 표시 * //
			break;
		}
		case LocationInformation.GAME_ROOM: { // * 게임방 입장 * //
			playBgm("bgm/evolution.wav");
			new GameFrame();
			break;
		}
		}
	}

	public static void removeAllPanel() { // * 방목록 전체 삭제 * //
		FrameHandler.getLobbyFrame().rowsPanel.removeAllPanel();
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

	public static WaitingRoomFrame getWaitingRoomFrame() {
		return FrameHandler.waitingRoomFrame;
	}

	public static void setWaitingRoomFrame(WaitingRoomFrame waitingRoomFrame) {
		FrameHandler.waitingRoomFrame = waitingRoomFrame;
	}

	public static InviteFrame getInviteFrame() {
		return FrameHandler.inviteFrame;
	}

	public static void setInviteFrame(InviteFrame inviteFrame) {
		FrameHandler.inviteFrame = inviteFrame;
	}

	public static void setShowMessage(ShowMessage showMessage) {
		FrameHandler.showMessage = showMessage;
	}

	public static ShowMessage getShowMessage() {
		return FrameHandler.showMessage;
	}

	public static ShowConfirm getShowConfirm() {
		return FrameHandler.showConfirm;
	}

	public static void setShowConfirm(ShowConfirm showConfirm) {
		FrameHandler.showConfirm = showConfirm;
	}

	public static BackgroundMusic getBackgroundMusic() {
		return FrameHandler.bgm;
	}

	public static void setBackgroundMusic(BackgroundMusic backgroundMusic) {
		FrameHandler.bgm = backgroundMusic;
	}

}
