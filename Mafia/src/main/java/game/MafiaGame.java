package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import client.MafiaClient;
import handling.lobby.WaitingRoom;
import handling.packet.ClientPacketCreator;
import handling.packet.GamePacketCreator;
import information.ServerConstants;

public class MafiaGame {
	public boolean end = false;

	private ArrayList<MafiaClient> mafias = new ArrayList<MafiaClient>();
	private ArrayList<MafiaClient> citizens = new ArrayList<MafiaClient>();
	private ArrayList<MafiaClient> doctors = new ArrayList<MafiaClient>();
	private ArrayList<MafiaClient> polices = new ArrayList<MafiaClient>();
	private MafiaClient execution;
	private WaitingRoom room;
	private int days = 0; // 몇 번째 날 인지
	private boolean night = false; // 밤이면 true, 낮이면 false
	private int remainTime = 0; // 남은 시간
	private int status = 0;
	private Timer timer = new Timer();

	/*
	 * status 는 현재 진행중인 시간대 0 : 토론 시간(낮) 1 : 투표 시간(낮) 2 : 최후의 반론 시간(낮) 3 : 투표 대상자 찬반
	 * 투표 시간(낮) 4 : 능력 사용 시간(밤)
	 */

	public MafiaGame(WaitingRoom room) {
		this.room = room;
		this.distributeJob();
		this.setStatus(0); // 토론 시간대
		timer.start();
	}

	public void distributeJob() { // 직업 분배
		int[] classify = ServerConstants.CLASSIFY_JOB[room.getOnlines() - 1];
		int mafia = classify[0];
		int citizen = classify[1];
		int doctor = classify[2];
		int police = classify[3];
		Collections.shuffle(room.getClients());
		int count = 0;
		for (int i = 0; i < mafia; i++) {
			this.getClients().get(count).setJob(ServerConstants.MAFIA);
			this.mafias.add(room.getClients().get(count++));
		}

		for (int i = 0; i < citizen; i++) {
			this.getClients().get(count).setJob(ServerConstants.CITIZEN);
			this.citizens.add(room.getClients().get(count++));
		}

		for (int i = 0; i < doctor; i++) {
			this.getClients().get(count).setJob(ServerConstants.DOCTOR);
			this.doctors.add(room.getClients().get(count++));
		}

		for (int i = 0; i < police; i++) {
			this.getClients().get(count).setJob(ServerConstants.POLICE);
			this.polices.add(room.getClients().get(count++));
		}
		
		for (MafiaClient c : this.getClients()) {
			c.showJobCard(c.getJob());
			this.broadCast(GamePacketCreator.setImage(c.getId(), ServerConstants.QUESS));
			c.getSession().writeAndFlush(GamePacketCreator.setImage(c.getId(), c.getJob()));
		}
		for (MafiaClient c : this.mafias) 
			this.broadCast(this.mafias, GamePacketCreator.setImage(c.getId(), c.getJob()));
	}

	public void chat(MafiaClient c, String msg) {
		if (c.isDead()) {
			this.broadCastDeadPlayer(GamePacketCreator.chat(8, "[죽은사람에게]" + c.getId() + "번 : " + msg));
		} else {
			this.chat(null, 5, c.getId() + "번 : " + msg);
		}
	}

	public void chat(int type, String msg) {
		this.chat(null, type, msg);
	}

	public void chat(List<MafiaClient> clients, int type, String msg) {
		this.broadCast(clients == null ? this.getClients() : clients, GamePacketCreator.chat(type, msg));
	}

	public void broadCastDeadPlayer(byte[] packet) {
		for (MafiaClient c : this.getClients()) {
			if (c.isConnected() && c.isDead())
				c.getSession().writeAndFlush(packet);
		}
	}

	public void broadCast(byte[] packet) {
		for (MafiaClient c : this.getClients())
			if (c.isConnected())
				c.getSession().writeAndFlush(packet);
	}

	public void broadCast(List<MafiaClient> clients, byte[] packet) {
		for (MafiaClient c : clients)
			if (c.isConnected())
				c.getSession().writeAndFlush(packet);
	}

	public long getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) { // 타이머 설정
		this.remainTime = remainTime;
		broadCast(GamePacketCreator.remainTime(remainTime));
		timer.setTime(remainTime);
	}

	public boolean isNight() {
		return night;
	}

	public void setNight(boolean night) {
		this.night = night;
		broadCast(GamePacketCreator.changeDayAndNight(night, this.getDays()));
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) { // 투표 시작 전 투표 관련 변수 초기화
		this.status = status;
		switch (status) {
		case 0: // 토론 시간대
			this.addDays(1); // 1일 추가
			if (this.getDays() > 1)
				this.useAbilityResult();
			if (isEnd()) {
				this.end = true;
				this.setRemainTime(ServerConstants.END_TIME);
				this.setNight(false);
				return;
			}
			this.setRemainTime(ServerConstants.DAY_TIME);
			this.setNight(false);
			break;
		case 1: // 투표 시간대
			for (MafiaClient c : this.getRoom().getClients())
				c.clearVote();
			for (MafiaClient c : this.getRoom().getClients())
				c.setVote(1);
			this.setRemainTime(ServerConstants.VOTE_TIME);
			this.setNight(false);
			break;
		case 2: // 최후의 반론 시간대
			boolean skip = this.citizenVoteResult();
			if (skip) {
				this.setStatus(4);
				return;
			}
			this.setRemainTime(ServerConstants.OBJECTION_TIME);
			this.setNight(false);
			break;
		case 3: // 찬반 투표 시간대
			this.setRemainTime(ServerConstants.AGREE_VOTE_TIME);
			this.setNight(false);
			break;
		case 4: // 능력 사용 시간대
			if (isEnd()) {
				this.end = true;
				this.setRemainTime(ServerConstants.END_TIME);
				this.setNight(false);
				return;
			}
			for (MafiaClient c : this.getRoom().getClients())
				c.clearVote();
			for (MafiaClient c : this.mafias)
				c.setVote(1);
			for (MafiaClient c : this.doctors)
				c.setVote(1);
			for (MafiaClient c : this.polices)
				c.setVote(1);
			this.setRemainTime(ServerConstants.NIGHT_TIME);
			this.setNight(true);
			break;
		case 5: // 게임 종료 시
			this.getRoom().endGame(this.isMafiaWin());			
			this.timer.stopThread();
		}
		String text[] = { "토론 시간대", "투표 시간대", "최후의 반론 시간대", "찬반 투표 시간대", "능력 사용 시간대", "게임 종료"};
		this.chat(5, "현재 [" + text[status] + "] 입니다.");
	}

	public MafiaClient getClient(int gameNumber) {
		for (MafiaClient c : this.getRoom().getClients()) {
			if (c.isConnected() && c.getId() == gameNumber)
				return c;
		}
		return null;
	}

	public List<MafiaClient> getClients() {
		return this.getRoom().getClients();
	}

	public void receiveVote(MafiaClient voter, int gameNumber) {
		MafiaClient c = this.getClient(gameNumber);
		if (c == null)
			return;
		if (voter.getVote() <= 0) {
			voter.dropMessage(2, "이미 투표를 했거나 투표가 가능한 시간대가 아닙니다.");
			return;
		}
		voter.setVote(0);
		if (this.getStatus() == 1) { // 낮 투표 시간
			c.setCitizenVote(c.getCitizenVote() + 1);
			voter.dropMessage(3, gameNumber + "번에게 투표를 했습니다.");
		} else if (this.getStatus() == 4) { // 밤 능력 사용 시간
			if (ServerConstants.isMafia(voter.getJob())) {
				c.setMafiaVote(c.getMafiaVote() + 1);
				voter.dropMessage(3, gameNumber + "번을 지목했습니다.");
				chat(this.mafias, 7, gameNumber + "번이 마피아에게 지목당했습니다. (" + c.getMafiaVote() + "표)");
			} else if (ServerConstants.isDoctor(voter.getJob())) {
				c.setDoctorVote(c.getDoctorVote() + 1);
				voter.dropMessage(3, gameNumber + "번을 마피아에게 한 번 보호합니다.");
			} else if (ServerConstants.isPolice(voter.getJob())) {
				c.setPoliceVote(c.getPoliceVote() + 1);
				voter.dropMessage(3, gameNumber + "번을 마피아로 의심합니다.");
				chat(this.mafias, 8, gameNumber + "번이 경찰에게 지목당했습니다. (" + c.getPoliceVote() + "표)");
			}
		}
	}

	public boolean citizenVoteResult() { // 처형 투표 결과
		MafiaClient client = null;
		int max = 0;
		int second = 0;
		for (MafiaClient c : this.getClients()) {
			if (c.getCitizenVote() > max) {
				max = c.getCitizenVote();
				client = c;
			} else if (c.getCitizenVote() == max) {
				second = max;
			}
		}

		if (max != second) {
			this.chat(7, "[알림] " + client.getId() + "번을 대상으로 처형 찬반 투표가 이루어질 예정입니다.");
			this.execution = client;
			return false;
		} else {
			this.chat(6, "[알림] 투표 수가 가장 많은 플레이어가 두 명 이상 이므로 찬반 투표는 생략됩니다.");
			return true;
		}
	}

	public void agreeOppositeResult() { // 찬반 투표 결과
		if (this.execution == null) {
			return;
		}
		
		if (this.getAlivePerson() / 2 <= this.execution.getAgree()) { // 과반 수 이상 동의 시
			this.chat(6, "[처형] " + this.execution.getId() + "번이 처형되었습니다.");
			this.killPlayer(this.execution);
		} else {
			this.chat(7, "[생존] " + this.execution.getCharName() + "번의 처형에 과반 수 이상 동의하지 않아 생존하였습니다.");
		}

	}

	public void useAbilityResult() { // 능력 사용 결과
		int max = 0, second = 0;
		MafiaClient client = null;
		for (MafiaClient c : this.getClients()) {
			if (c.getMafiaVote() > max) {
				max = c.getMafiaVote();
				client = c;
			} else if (c.getMafiaVote() == max) {
				second = max;
			}
		}
		if (max == 0) {
			chat(6, "마피아가 능력사용을 하지 않았습니다.");
		} else {
			if (client.getDoctorVote() > 0) {
				chat(7, "[마피아] 마피아가 " + client.getId() + "번을 암살하려 했지만 의사가 방어했습니다.");
			} else {
				killPlayer(client);
				this.chat(6, "[마피아] 마피아가 " + client.getId() + "번을 암살했습니다.");
			}
		}
		max = 0;
		second = 0;
		client = null;
		for (MafiaClient c : this.getClients()) {
			if (c.getPoliceVote() > max) {
				max = c.getPoliceVote();
				client = c;
			} else if (c.getPoliceVote() == max) {
				second = max;
			}
		}
		if (max == 0)
			chat(7, "경찰이 능력사용을 하지 않았습니다.");
		else
			chat(this.polices, 7, "[경찰] " + client.getId() + "번은 마피아"
					+ (ServerConstants.isMafia(client.getJob()) ? "입니다." : "가 아닙니다."));
	}

	public boolean isMafiaWin() { // 마피아 승 : true, 시민 승 : false
		return this.getAliveMafia() > 0;
	}

	public boolean isEnd() { // 게임이 종료될 상황인지
		/*
		 * 1. 시민팀이 모두 죽은 경우 2. 마피아가 모두 죽은 경우 능력사용 시간대 -> 토론 시간 에 확인 찬반 투표 시간대 -> 밤 에 확인
		 */
		boolean end = false;
		int mafia = getAliveMafia();
		int citizen = getAliveCitizen();
		return (mafia == 0 || citizen == 0);
	}

	public int getAliveMafia() {
		int mafia = 0;
		for (MafiaClient c : mafias) {
			if (c.isConnected() && !c.isDead())
				mafia++;
		}
		return mafia;
	}

	public int getAliveCitizen() {
		int citizen = 0;
		for (MafiaClient c : this.getClients()) {
			if (c.isConnected() && !c.isDead() && !ServerConstants.isMafia(c.getJob()))
				citizen++;
		}
		return citizen;
	}

	public int getAlivePerson() {
		int persons = 0;
		for (MafiaClient c : this.getClients()) {
			if (!c.isDead() && c.isConnected())
				persons++;
		}
		return persons;
	}

	public int getNextPhase() { // 다음 단계 반환
		// 토론 시간 -> 투표 시간 -> 최후의 반론 시간 -> 찬반 투표 시간 -> 능력 사용 시간 -> 토론 시간 ...
		int status = this.getStatus() + 1;
		return status > 4 ? 0 : status;
	}

	public WaitingRoom getRoom() {
		return this.room;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
		this.broadCast(GamePacketCreator.changeDayAndNight(this.isNight(), days));
	}

	public void addDays(int days) {
		this.setDays(this.getDays() + days);
	}

	public void killPlayer(MafiaClient c) {
		c.setDead(true);
		this.broadCast(GamePacketCreator.deadPlayer(c.getId(), c.getJob()));
	}

	class Timer extends Thread {
		private long remainTime;
		private long startTime;

		public void setTime(long remainTime) {
			this.remainTime = remainTime;
			this.startTime = System.currentTimeMillis();
		}

		public void stopThread() {
			this.interrupt();
		}

		public void run() {
			while (!this.isInterrupted()) {
				if (System.currentTimeMillis() - this.startTime >= this.remainTime) {
					if(end) {
						setStatus(5);
						return;
					}
					setStatus(getNextPhase());
				}
				try {
					this.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
