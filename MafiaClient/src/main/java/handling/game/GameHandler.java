package handling.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import handling.netty.ClientHandler;
import handlinig.packet.GamePacket;
import information.ClientInf;
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

	public static void setTimer(long remainTime) { // * 투표 시간 타이머 * //
		VoteTimer voteTimer = new VoteTimer(remainTime);
	}

	public void setMafiaMode() { // * 마피아 모드 설정 * //
		gameFrame.getChatTf().setForeground(Color.RED);
	}

	public static void setNightText(boolean isNight,int day, JTextField nightTf) { // * 몇번째 밤인지 낮인지 알림 * //
		if(isNight) {
			nightTf.setText(day + "번째 밤");			
		}else {
			nightTf.setText(day+ "번째 낮");
		}
	}

	public static void addMsg(String nickName, String text, JTextArea chatTa) { // * 채팅 올리기 * //
		chatTa.setText(chatTa.getText() + "\n" + nickName + ": " + text + "\n");
	}

	public static void addPersonList(JPanel panel, int gameNumber) {
		JButton btn = new JButton(new ImageIcon("job/4.png")); // * 물음표 아이콘 * //
		personBtnSetting(btn, gameNumber+"");
		if(ClientInf.getGameNumber() == gameNumber) {
			setTextMe(btn);
		}
		GameHandler.getGameFrame().btnMap.put(gameNumber, btn);
		GameHandler.getGameFrame().btnState.put(btn,0);
		panel.add(btn);
	}

	public static void addDoubtList(JPanel panel, int gameNumber) {
		JButton btn = new JButton(new ImageIcon("job/4.png")); // * 물음표 아이콘 * //
		doubtBtnSetting(btn, gameNumber+"");
		if(ClientInf.getGameNumber() == gameNumber) {
			setTextMe(btn);
		}
		panel.add(btn);
	}

	public static void personBtnSetting(JButton btn, String gameNumber) { // * 인원 버튼 세팅 * //
		btn.setPreferredSize(new Dimension(80, 80));
		btn.setHorizontalTextPosition(JButton.CENTER); // 텍스트 가운데
		btn.setFont(new Font("", Font.BOLD, 15));
		btn.setForeground(Color.RED);
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(false);
		btn.setText("No."+gameNumber);
		btn.addActionListener(new voteBtnHandler());
	}
	
	public static void setTextMe(JButton btn) { // * 내 버튼 ME 로 설정 
		btn.setText("ME");
	}

	public static void doubtBtnSetting(JButton btn, String gameNumber) { // * 직업 의심 버튼 세팅 * //
		btn.setPreferredSize(new Dimension(80, 80));
		btn.setHorizontalTextPosition(JButton.CENTER); // 텍스트 가운데
		btn.setFont(new Font("", Font.BOLD, 15));
		btn.setFocusPainted(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		btn.setForeground(Color.RED);
		btn.setText("No."+gameNumber);
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowMessage choiceJob = new ShowMessage();
				choiceJob.doubtJob(btn);
			}
		});
	}

	public static void jobImgSetting(JButton btn, int job) { // * 인원 버튼 직업 별 이미지 설정 * //
		// 0 - 시민
		// 1 - 마피아
		// 2 - 경찰
		// 3 - 의사
		// 4 - 물음표
		btn.setIcon(new ImageIcon("job/" + job + ".png"));
		btn.setPressedIcon(new ImageIcon("job/"+job+".Push.png"));
	}
	
	public static void deadBtnSetting(JButton btn,int job) {
		btn.setIcon(new ImageIcon("job/dead" + job + ".png"));
		btn.setPressedIcon(new ImageIcon("job/dead"+job+".png"));
		btn.setEnabled(false);
	}
	
	public static void initChoicedBtn() {  // * 버튼 모두 선택안한걸로 초기화 * //
		Iterator<Integer> mapIter = GameHandler.getGameFrame().btnMap.keySet().iterator();
		while(mapIter.hasNext()){
			Integer key = mapIter.next();
			GameHandler.getGameFrame().btnState.put(GameHandler.getGameFrame().btnMap.get(key),0);             
			GameHandler.getGameFrame().btnMap.get(key).setBorder(new EmptyBorder(5,3,5,3));
        }
		
	}
	
	static class voteBtnHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			initChoicedBtn();
			JButton btn = (JButton)e.getSource();
			LineBorder border = new LineBorder(Color.RED,3);
		    btn.setBorder(border);
		    GameHandler.getGameFrame().btnState.put(btn,1);
		}
	}
	
	static class VoteTimer extends Thread { // * timer * //
		long time, startTime, offTime;
		String timer;
		long timeReceieve; // * 서버에서 받은 시간 * //
		long remainTime; // * 타이머 남은시간 * //

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
			return "남은 시간 " + (minute < 10 ? "0" + minute + " : " : minute + " : ") + second;
		}

		public void stopThread() {
			super.interrupt();
		}

		public void run() {
			while (remainTime > 0) {
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
