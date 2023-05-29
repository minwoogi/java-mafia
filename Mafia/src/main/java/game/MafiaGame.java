package game;

import java.util.ArrayList;
import java.util.Collections;

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
	private int days = 0; // �� ��° �� ����
	private boolean night = false; // ���̸� true, ���̸� false
	private long remainTime = 0; // ���� �ð�
	private int status = 0;

	/*
	 * status �� ���� �������� �ð��� 0 : ��� �ð�(��) 1 : ��ǥ �ð�(��) 2 : ������ �ݷ� �ð�(��) 3 : ��ǥ ����� ����
	 * ��ǥ �ð�(��) 4 : �ɷ� ��� �ð�(��)
	 */

	public MafiaGame(WaitingRoom room) {
		this.room = room;
		this.distributeJob();
		this.setStatus(0); // ��� �ð���
	}

	public void distributeJob() { // ���� �й�
		int[] classify = ServerConstants.CLASSIFY_JOB[room.getOnlines() - 5];
		int mafia = classify[0];
		int citizen = classify[1];
		int doctor = classify[2];
		int police = classify[3];
		Collections.shuffle(room.getClients());
		int count = 0;
		for (int i = 0; i < mafia; i++) {
			room.getClients().get(count).setJob(ServerConstants.MAFIA);
			mafias.add(room.getClients().get(count++));
		}

		for (int i = 0; i < citizen; i++) {
			room.getClients().get(count).setJob(ServerConstants.CITIZEN);
			citizens.add(room.getClients().get(count++));
		}

		for (int i = 0; i < doctor; i++) {
			room.getClients().get(count).setJob(ServerConstants.DOCTOR);
			doctors.add(room.getClients().get(count++));
		}

		for (int i = 0; i < police; i++) {
			room.getClients().get(count).setJob(ServerConstants.POLICE);
			polices.add(room.getClients().get(count++));
		}

		for (MafiaClient c : room.getClients()) {
			if (ServerConstants.isMafia(c.getJob()))
				c.dropMessage(2, "����� ������ ���Ǿ��Դϴ�.");

			if (ServerConstants.isCitizen(c.getJob()))
				c.dropMessage(2, "����� ������ �ù��Դϴ�.");

			if (ServerConstants.isDoctor(c.getJob()))
				c.dropMessage(2, "����� ������ �ǻ��Դϴ�.");

			if (ServerConstants.isPolice(c.getJob()))
				c.dropMessage(2, "����� ������ �����Դϴ�.");
		}
	}

	public void dropMessage(int type, String msg) {
		broadCast(ClientPacketCreator.showMessage(type, null, msg, 0));
	}

	public void broadCast(byte[] packet) {
		for (MafiaClient c : room.getClients())
			c.getSession().writeAndFlush(packet);
	}

	public long getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(long remainTime) { // Ÿ�̸� ����
		this.remainTime = remainTime;
		broadCast(GamePacketCreator.remainTime(remainTime));
	}

	public boolean isNight() {
		return night;
	}

	public void setNight(boolean night) {
		this.night = night;
		broadCast(GamePacketCreator.changeDayAndNight(night));
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) { // ��ǥ ���� �� ��ǥ ���� ���� �ʱ�ȭ
		this.status = status;
		switch (status) {
		case 0: // ��� �ð���
			this.addDays(1); // 1�� �߰�
			if (isEnd()) {
				this.end = true;
				this.getRoom().endGame(this.isMafiaWin());
				break;
			}
			if(this.getDays() > 1)
				this.useAbilityResult();
			this.setRemainTime(ServerConstants.DAY_TIME);
			this.setNight(false);
			break;
		case 1: // ��ǥ �ð���
			this.setRemainTime(ServerConstants.VOTE_TIME);
			this.setNight(false);
			break;
		case 2: // ������ �ݷ� �ð���
			this.setRemainTime(ServerConstants.OBJECTION_TIME);
			this.setNight(false);
			this.citizenVoteResult();
			break;
		case 3: // ���� ��ǥ �ð���
			this.setRemainTime(ServerConstants.AGREE_VOTE_TIME);
			this.setNight(false);
			break;
		case 4: // �ɷ� ��� �ð���
			if (isEnd()) {
				this.end = true;
				this.getRoom().endGame(this.isMafiaWin());
				break;
			}
			this.setRemainTime(ServerConstants.NIGHT_TIME);
			this.setNight(true);
			break;
		}
	}

	public void citizenVoteResult() { // ó�� ��ǥ ���
		MafiaClient client = null;
		int max = 0;
		int second = 0;
		for (MafiaClient c : this.room.getClients()) {
			if (c.getCitizenVote() > max) {
				max = c.getCitizenVote();
				client = c;
			} else if (c.getCitizenVote() == max) {
				second = max;
			}
		}
		
		if (max != second)
			this.dropMessage(6, "[�˸�] '" + client.getCharName() + "' ���� ������� ó�� ���� ��ǥ�� �̷���� �����Դϴ�.");
		else {
			this.dropMessage(6, "[�˸�] ��ǥ ���� ���� ���� �÷��̾ �� �� �̻� �̹Ƿ� ���� ��ǥ�� �����˴ϴ�.");
			this.setStatus(4);
		}
	}

	public void agreeOppositeResult() { // ���� ��ǥ ���
		if(execution == null) {
			return;
		}
		
		if(this.getAlivePerson() / 2 <= execution.getAgree()) { // ���� �� �̻� ���� ��
			this.dropMessage(7, "[ó��] '" + execution.getCharName() + "' ���� ó���Ǿ����ϴ�.");
			execution.setDead(true);	
		} else {
			this.dropMessage(7, "[����] '" + execution.getCharName() + "' ���� ó���� ���� �� �̻� �������� �ʾ� �����Ͽ����ϴ�.");
		}
		
	}
	public void useAbilityResult() { // �ɷ� ��� ���

	}

	public boolean isMafiaWin() { // ���Ǿ� �� : true, �ù� �� : false
		return this.getAliveMafia() > 0;
	}

	public boolean isEnd() { // ������ ����� ��Ȳ����
		/*
		 * 1. �ù����� ��� ���� ��� 2. ���Ǿư� ��� ���� ��� �ɷ»�� �ð��� -> ��� �ð� �� Ȯ�� ���� ��ǥ �ð��� -> �� �� Ȯ��
		 */
		boolean end = false;
		int mafia = getAliveMafia();
		int citizen = getAliveCitizen();
		return (mafia == 0 || citizen == 0);
	}

	public int getAliveMafia() {
		int mafia = 0;
		for (MafiaClient c : mafias) {
			if (!c.isDead() && c.isConnected())
				mafia++;
		}
		return mafia;
	}

	public int getAliveCitizen() {
		int citizen = 0;
		for (MafiaClient c : citizens) {
			if (!c.isDead() && c.isConnected())
				citizen++;
		}
		for (MafiaClient c : polices) {
			if (!c.isDead() && c.isConnected())
				citizen++;
		}
		return citizen;
	}

	public int getAlivePerson() {
		int persons = 0;
		for (MafiaClient c : this.getRoom().getClients()) {
			if(!c.isDead() && c.isConnected()) 
				persons++;
		}
		return persons;
	}
	public int getNextPhase() { // ���� �ܰ� ��ȯ
		// ��� �ð� -> ��ǥ �ð� -> ������ �ݷ� �ð� -> ���� ��ǥ �ð� -> �ɷ� ��� �ð� -> ��� �ð� ...
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
	}
	
	public void addDays(int days) {
		this.setDays(this.getDays() + days);
	}

}
