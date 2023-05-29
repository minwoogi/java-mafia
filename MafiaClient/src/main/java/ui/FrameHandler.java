package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import handling.game.GameHandler;
import handling.netty.ClientHandler;
import handlinig.packet.LobbyPacket;
import information.ExpInf;
import information.FrameLocation;
import information.LocationInformation;
import information.RoomInf;
import information.UserInf;
import information.ClientInf;



public class FrameHandler {

	static LoginFrame loginFrame;
	static SignUpFrame signUpFrame;
	static LobbyFrame lobbyFrame;
	static WaitingRoomFrame waitingRoomFrame;
	static InviteFrame inviteFrame;

//	public static void failedLogin(boolean loginCheck) {
//		if (loginCheck) {
//			FrameHandler.getLoginFrame().frame.dispose();
//		} else {
//			JOptionPane.showMessageDialog(loginFrame.panel, "id �Ǵ� password�� �߸� �ԷµǾ����ϴ�.", "error",
//					JOptionPane.ERROR_MESSAGE);
//		}
//	}

	public static void useId(boolean overlap) {  // * ȸ�����Խ� ��� ������ ID���� Ȯ�� * // 
		if (overlap) {
			JOptionPane.showMessageDialog(loginFrame.panel, "�̹� �����ϴ� ���̵��Դϴ�.", "Overlap", JOptionPane.ERROR_MESSAGE);
		} else {
			int useId = JOptionPane.showConfirmDialog(signUpFrame.panel, "��� ������ id�Դϴ�. ����Ͻðڽ��ϱ�?", "id ���",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (useId == 0) {
				FrameHandler.setTfEditable(FrameHandler.getSignUpFrame().getidTf());
				FrameHandler.getSignUpFrame().getidDoubleCheckBtn().setEnabled(false);
			}
		}
	}

	public static void useNickName(boolean overlap) { // * ȸ�����Խ� ��� ������ Nickname���� Ȯ�� * //
		if (overlap) {
			JOptionPane.showMessageDialog(loginFrame.panel, "�̹� �����ϴ� �г����Դϴ�.", "Overlap", JOptionPane.ERROR_MESSAGE);
		} else {
			int useNick = JOptionPane.showConfirmDialog(signUpFrame.panel, "��� ������ �г����Դϴ�. ����Ͻðڽ��ϱ�?", "nickname ���",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (useNick == 0) {
				FrameHandler.setTfEditable(FrameHandler.getSignUpFrame().getnickNameTf());
				FrameHandler.getSignUpFrame().getnickNameCheckBtn().setEnabled(false);
			}
		}
	}

	public static void checkEmailCode(boolean use) { // * �̸��� �����ڵ� Ȯ�� * //
		if (use) {
			JOptionPane.showMessageDialog(signUpFrame.panel, "���� ��ȣ�� Ȯ�εƽ��ϴ�.", "email OK",
					JOptionPane.INFORMATION_MESSAGE);
			FrameHandler.getSignUpFrame().getcertificationBtn().setEnabled(false);
			FrameHandler.getSignUpFrame().getemailBtn().setEnabled(false);
			FrameHandler.setTfEditable(FrameHandler.getSignUpFrame().getemailTf());
		} else {
			JOptionPane.showMessageDialog(loginFrame.panel, "������ȣ�� Ʋ�Ƚ��ϴ�.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void checkEmail(boolean overlap, boolean sendComplete) { // * ȸ�����Խ� ��� ������ email���� Ȯ�� * //
		if (overlap) {
			JOptionPane.showMessageDialog(loginFrame.panel, "�̹� �����ϴ� email�Դϴ�.", "Overlap", JOptionPane.ERROR_MESSAGE);
		} else {
			if (!sendComplete) {
				JOptionPane.showMessageDialog(loginFrame.panel, "�̸��� ���ۿ� �����߽��ϴ�.", "Overlap",
						JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(loginFrame.panel, "�̸��� ���ۿ� �����߽��ϴ�.", "Complete",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public static void completeRegister(boolean isRegister) { // * ȸ������ �Ϸ� �� �޼��� * //
		if (isRegister) {
			JOptionPane.showMessageDialog(signUpFrame.panel, "ȸ�� ������ ���������� �Ϸ� �ƽ��ϴ�.", "ȸ������ �Ϸ�",
					JOptionPane.INFORMATION_MESSAGE);
			signUpFrame.frame.dispose();
			new LoginFrame();
		} else {
			JOptionPane.showMessageDialog(signUpFrame.panel, "ȸ�� ���Կ� �����Ͽ����ϴ�...", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void updateTierImage(int tier, JLabel lbl) { // * �κ� ��ȿ��� tier ǥ�� * //
		lbl.setIcon(new ImageIcon("tierImage/" + tier + ".jpg"));
	}

	public static void updateLevel(int level, JLabel lbl) { // * �κ� ��ȿ��� level ǥ�� * //
		lbl.setText(" LV. " + level);
	}

	public static void updateExpBar(int exp, JProgressBar bar, int level) { // * ����ġ �ۼ�Ʈ ǥ�� * //
		float tmp = exp;
		bar.setValue((int) ((tmp / ExpInf.needExp(level)) * 100.0));
	}

	public static void UpdateNickName(String nickName, JButton btn) { // * �κ� ��ȿ��� �г��� ǥ�� * //
		btn.setText(nickName);
	}

	public static void setTfEditable(JTextField tf) { // * ȸ�����Խ� ����� ID�� NICKNAME�����Ǹ� ������ * //
		tf.setEditable(false);
		tf.setForeground(Color.RED);
	}
	
	public static void setLeaderMode(JButton btn) { // * �����̸� ���� ��ư���� �����ϰ� �г��Ӿտ� [L]���̱� * // 
		btn.setIcon(new ImageIcon("btnImg/startGame.png"));
		btn.setPressedIcon(new ImageIcon("btnImg/startGamePush.png"));
		FrameHandler.getWaitingRoomFrame().userPanel.get(ClientInf.getUserId()).getStateBtn().setIcon(new ImageIcon(""
				+ "btnImg/leader.png"));
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientHandler.send(LobbyPacket.makeGameStartPacket(1));
			}
		});
	}
	public static void addInvteUser(String nickName) {
		InviteUserPanel inviteUserPanel = new InviteUserPanel(nickName);
		FrameHandler.getInviteFrame().inviteRowsPanel.addUserList(inviteUserPanel);
	}
	
	public static void addRoomPanel(RoomInf roomInf) { // * �κ� �����ӿ��� �� �߰� * //
		LobbyRoomPanel roomPanel = new LobbyRoomPanel(roomInf);
		roomPanel.setRoomInfTf(roomInf);
		FrameHandler.getLobbyFrame().getRoomList().put(roomInf.getRoomId(), roomPanel);
		FrameHandler.getLobbyFrame().rowsPanel.addRoomPanel(roomPanel);
	}
	
	public static void updateRoomPanel(int roomId,RoomInf roomInf) { // * �κ������ӿ��� ������ ���� * //
		FrameHandler.getLobbyFrame().roomList.get(roomId).setRoomInfTf(roomInf);
	}
	
	public static void removeRoomPanel(int roomId ,LobbyRoomPanel lobbyRoomPanel) { // * �κ� �����ӿ��� �� ���� * //
		FrameHandler.getLobbyFrame().rowsPanel.removeRoomPanel(lobbyRoomPanel);
		FrameHandler.getLobbyFrame().getRoomList().remove(roomId);
	}
	
	public static void addUserPanel(UserInf userInf) { // * ���� �����ӿ��� �ο� �߰� * //
		WaitingRoomPanel waitingRoomPanel = new WaitingRoomPanel(userInf);
		waitingRoomPanel.setRoomInfTf(userInf);
		FrameHandler.getWaitingRoomFrame().rowsPanel.addUserPanel(waitingRoomPanel);
		FrameHandler.getWaitingRoomFrame().userPanel.put(userInf.getUserId(),waitingRoomPanel);
	}
	
	public static void updateUserPanel(UserInf userInf) { // * ���� �����ӿ��� ���� ������Ʈ * //
		FrameHandler.getWaitingRoomFrame().userPanel.get(userInf.getUserId()).setRoomInfTf(userInf);
	}
	
	public static void removeUserPanel(int userId, WaitingRoomPanel waitingRoomPanel) { // * ���� ������ ��� ���� * //
		FrameHandler.getWaitingRoomFrame().rowsPanel.removeUserPanel(waitingRoomPanel);
		FrameHandler.getWaitingRoomFrame().userPanel.remove(userId);
	}
	
	public static void quitLoginFrame() {
		FrameLocation.X = FrameHandler.getLoginFrame().frame.getX();
		FrameLocation.Y = FrameHandler.getLoginFrame().frame.getY();
		loginFrame.frame.dispose();
	}
	
	public static void quitLobbyFrame() {
		FrameLocation.X = FrameHandler.getLobbyFrame().getX();
		FrameLocation.Y = FrameHandler.getLobbyFrame().getY();
		lobbyFrame.dispose();
	}
	
	public static void quitWaittingFrame() {
		FrameLocation.X = FrameHandler.getWaitingRoomFrame().getX();
		FrameLocation.Y = FrameHandler.getWaitingRoomFrame().getY();
		waitingRoomFrame.dispose();
	}
	
	
	
	public static void warp(int location) {  // * CHANGE_LOCATION * //
		switch(location) {
		case LocationInformation.LOBBY:{ // * �κ� ���� * //
			FrameHandler.quitLoginFrame();
			new LobbyFrame();
			FrameHandler.updateTierImage(ClientInf.getTier(), FrameHandler.getLobbyFrame().getTierLabel()); // * Ƽ����� ǥ�� * //
			FrameHandler.updateLevel(ClientInf.getLevel(), FrameHandler.getLobbyFrame().getLevelLabel()); // * ���� ǥ�� * //
			FrameHandler.UpdateNickName(ClientInf.getNickName(), FrameHandler.getLobbyFrame().getNickNameLabel()); // * �г��� ǥ�� * //
			FrameHandler.updateExpBar(ClientInf.getExp(), FrameHandler.getLobbyFrame().getExpBar(), ClientInf.getLevel()); // * ����ġ ǥ�� * //
			break;
		}
		case LocationInformation.WAITING_ROOM:{ // * ���� ���� * //
			FrameHandler.quitLobbyFrame(); // * �κ� ������ * //
			new WaitingRoomFrame();
			ClientInf.setReadyState(false);
			FrameHandler.updateTierImage(ClientInf.getTier(), FrameHandler.getWaitingRoomFrame().getTierLabel()); // * Ƽ����� ǥ�� * //
			FrameHandler.updateLevel(ClientInf.getLevel(), FrameHandler.getWaitingRoomFrame().getLevelLabel()); // * ���� ǥ�� * //
			FrameHandler.UpdateNickName(ClientInf.getNickName(), FrameHandler.getWaitingRoomFrame().getNickNameLabel()); // * �г��� ǥ�� * //
			FrameHandler.updateExpBar(ClientInf.getExp(), FrameHandler.getWaitingRoomFrame().getExpBar(), ClientInf.getLevel()); // * ����ġ ǥ�� * //
			break;
		}
		case LocationInformation.GAME_ROOM:{ // * ���ӹ� ���� * //
			FrameHandler.quitWaittingFrame();
			new GameFrame();
			break;
		}
		}
	}
	public static void removeAllPanel() { // * ���� ��ü ���� * //
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

}
