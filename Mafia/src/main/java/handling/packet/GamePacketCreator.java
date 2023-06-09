package handling.packet;

import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

public class GamePacketCreator {
	/*
	 * ������ ���� ��Ŷ�� �����ϴ� Ŭ����
	 * 
	 * 1. ��нð�, ��ǥ�ð�, �ɷ� ��� �ð� �� �ð����� ����
	 * 2. ��, �� ��ȯ ����
	 * 3. 
	 *
	*/

	public static byte[] startGame(int people, int id) {
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.START_GAME);
		packet.writeInt(people);
		packet.writeInt(id);
		return packet.getPacket();
	}
	
	public static byte[] remainTime(int type, int time) { 
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.TIMER);
		packet.writeInt(type);
		packet.writeLong(time);
		return packet.getPacket();
	}
	
	
	public static byte[] changeDayAndNight(boolean night, int day) { // ���̸� true
		// ���Ǿ� ������ ���� �� ���� �����Ѵ�.
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.DAY_AND_NIGHT);
		packet.writeBoolean(night);
		packet.writeInt(day);
		return packet.getPacket();		
	}
	
	public static byte[] showJobCard(int job) {
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.SHOW_JOB_CARD);
		packet.writeInt(job);
		return packet.getPacket();		
	}
	
	public static byte[] chat(int type, String msg) {
		/*
		 * 5 : ��� �۾�
		 * 6 : ������ �۾�
		 * 7 : ���� �ϴû� �۾�
		 * 8 : ȸ�� �۾�
		 */
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.CHAT);
		packet.writeInt(type);
		packet.writeString(msg);
		return packet.getPacket();
	}
	
	public static byte[] setImage(int id, int job) {
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.SET_IMAGE);
		packet.writeInt(id);
		packet.writeInt(job);
		return packet.getPacket();
	}
	
	public static byte[] deadPlayer(int id, int job) {
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.DEAD_PLAYER);
		packet.writeInt(id);
		packet.writeInt(job);
		return packet.getPacket();
	}
}
