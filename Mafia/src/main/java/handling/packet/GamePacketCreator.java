package handling.packet;

import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

public class GamePacketCreator {
	/*
	 * 게임장 내의 패킷을 생성하는 클래스
	 * 
	 * 1. 토론시간, 투표시간, 능력 사용 시간 등 시간관련 정보
	 * 2. 낮, 밤 전환 명령
	 * 3. 
	 *
	*/

	public static byte[] startGame(int people, int id) {
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.START_GAME);
		packet.writeInt(people);
		packet.writeInt(id);
		return packet.getPacket();
	}
	
	public static byte[] remainTime(int time) { 
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.TIMER);
		packet.writeLong(time);
		return packet.getPacket();
	}
	
	
	public static byte[] changeDayAndNight(boolean night, int day) { // 밤이면 true
		// 마피아 게임장 내의 밤 낮을 설정한다.
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
		 * 5 : 흰색 글씨
		 * 6 : 빨간색 글씨
		 * 7 : 밝은 하늘색 글씨
		 * 8 : 회색 글씨
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
