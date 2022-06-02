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
	
	
	public static byte[] remainTime(long time) { 
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.TIMER);
		packet.writeLong(time);
		return packet.getPacket();
	}
	
	public static byte[] nowDays(int days) { // 몇 번 째 날인지 전송
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.WHAT_DATE);
		packet.writeInt(days);
		return packet.getPacket();
	}
	
	public static byte[] changeDayAndNight(boolean night) { // 밤이면 true
		// 마피아 게임장 내의 밤 낮을 설정한다.
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.DAY_AND_NIGHT);
		packet.writeBoolean(night);
		return packet.getPacket();		
	}
	
}
