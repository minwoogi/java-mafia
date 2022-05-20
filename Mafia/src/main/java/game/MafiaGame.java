package game;

import java.util.ArrayList;
import java.util.Collections;

import client.MafiaClient;
import handling.lobby.WaitingRoom;
import information.ServerConstants;

public class MafiaGame extends Thread {
	public boolean end = false;
	
	private ArrayList<MafiaClient> mafias = new ArrayList<MafiaClient>();
	private ArrayList<MafiaClient> citizens = new ArrayList<MafiaClient>();
	private ArrayList<MafiaClient> doctors = new ArrayList<MafiaClient>();
	private ArrayList<MafiaClient> polices = new ArrayList<MafiaClient>();
	private WaitingRoom room;
	private boolean night = false; // 밤이면 true, 낮이면 false
	private long remainTime = 0; // 남은 시간
	private int status = 0; 
	/*
	 * status 는 현재 진행중인 시간대
	 * 0 : 토론 시간(낮)
	 * 1 : 투표 시간(낮)
	 * 2 : 최후의 반론 시간(낮)
	 * 3 : 투표 대상자 찬반 투표 시간(낮)
	 * 4 : 능력 사용 시간(밤)
	 */
	
	
	
	public MafiaGame(WaitingRoom room) {
		this.room = room;
		this.distributeJob();
		this.setStatus(0); // 토론 시간대
		this.setRemainTime(ServerConstants.DAY_TIME);
		this.start();
		
	}
	
	public void run() { // 시간 관리
		int tick = 500; // 0.5초마다 실행
		while(!end) { // 게임이 진행중이면 반복
			try {
				sleep(tick);
				this.setRemainTime(this.getRemainTime() - tick);
				if(this.getRemainTime() < 0) {
					this.setStatus(this.getNextPhase());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void distributeJob() { // 직업 분배
		int[] classify = ServerConstants.CLASSIFY_JOB[room.getOnlines() - 5];
		int mafia = classify[0];
		int citizen = classify[1];
		int doctor = classify[2];
		int police = classify[3];
		Collections.shuffle(room.getClients());
		int count = 0;
		for(int i = 0; i < mafia; i++) {
			room.getClients().get(count).setJob(ServerConstants.MAFIA);
			mafias.add(room.getClients().get(count++));
		}
		
		for(int i = 0; i < citizen; i++) { 
			room.getClients().get(count).setJob(ServerConstants.CITIZEN);
			citizens.add(room.getClients().get(count++));
		}
		
		for(int i = 0; i < doctor; i++) { 
			room.getClients().get(count).setJob(ServerConstants.DOCTOR);
			doctors.add(room.getClients().get(count++));
		}
		
		for(int i = 0; i < police; i++) { 
			room.getClients().get(count).setJob(ServerConstants.POLICE);
			polices.add(room.getClients().get(count++));
		}
		
		for(MafiaClient c : room.getClients()) {
			if(ServerConstants.isMafia(c.getJob())) 
				c.dropMessage(2, "당신의 직업은 마피아입니다.");
			
			if(ServerConstants.isCitizen(c.getJob())) 
				c.dropMessage(2, "당신의 직업은 시민입니다.");
			
			if(ServerConstants.isDoctor(c.getJob())) 
				c.dropMessage(2, "당신의 직업은 의사입니다.");
			
			if(ServerConstants.isPolice(c.getJob())) 
				c.dropMessage(2, "당신의 직업은 경찰입니다.");
		}
	}
	
		
	public void broadCast(byte[] packet) {
		for(MafiaClient c : room.getClients()) 
			c.getSession().writeAndFlush(packet);
	}

	public long getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(long remainTime) {
		this.remainTime = remainTime;
	}

	public boolean isNight() {
		return night;
	}

	public void setNight(boolean night) {
		this.night = night;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		switch(status) {
		case 0: // 토론 시간대
			if(isEnd()) {
				break;
			}
			this.setRemainTime(ServerConstants.DAY_TIME);
			this.setNight(false);
			break;
		case 1: // 투표 시간대
			this.setRemainTime(ServerConstants.VOTE_TIME);
			this.setNight(false);
			break;
		case 2: // 최후의 반론 시간대
			this.setRemainTime(ServerConstants.OBJECTION_TIME);
			this.setNight(false);
			break;
		case 3: // 찬반 투표 시간대
			this.setRemainTime(ServerConstants.AGREE_VOTE_TIME);
			this.setNight(false);
			break;
		case 4: // 능력 사용 시간대
			if(isEnd()) {
				this.getRoom().endGame(this.isMafiaWin());
				break;
			}
			this.setRemainTime(ServerConstants.NIGHT_TIME);
			this.setNight(true);
			break;
		}
	}
	
	public boolean isMafiaWin() { // 마피아 승 : true, 시민 승 : false
		return this.getAliveMafia() > 0;		
	}
	public boolean isEnd() { // 게임이 종료될 상황인지
		/* 1. 시민팀이 모두 죽은 경우
		 * 2. 마피아가 모두 죽은 경우
		 * 능력사용 시간대 -> 토론 시간 에 확인
		 * 찬반 투표 시간대 -> 밤 에 확인
		 */
		boolean end = false;
		int mafia = getAliveMafia();
		int citizen = getAliveCitizen();				
		return (mafia == 0 || citizen == 0);
	}

	public int getAliveMafia() {
		int mafia = 0;
		for(MafiaClient c : mafias) {
			if(!c.isDead() && c.isConnected())
				mafia++;
		}
		return mafia;		
	}
	
	public int getAliveCitizen() {
		int citizen = 0;
		for(MafiaClient c : citizens) {
			if(!c.isDead() && c.isConnected())
				citizen++;
		}
		for(MafiaClient c : polices) {
			if(!c.isDead() && c.isConnected())
				citizen++;
		}
		return citizen;		
	}
	
	public int getNextPhase() { // 다음 단계 반환
		// 토론 시간 -> 투표 시간 -> 최후의 반론 시간 -> 찬반 투표 시간 -> 능력 사용 시간 -> 토론 시간 ...
		int status = this.getStatus() + 1;
		return status > 4 ? 0 : status;
	}
	
	public WaitingRoom getRoom() {
		return this.room;
	}
	
}
