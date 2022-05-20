package handling.packet;

import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

public class GamePacketCreator {
	/*
	 * 게임장 내의 패킷을 생성하는 클래스
	 * 
	 * 1. 토론시간, 투표시간, 능력 사용 시간 등 시간관련 정보
	 * 2. 낮, 밤 전환 명령
	 * 3. 투표 결과 정보
	 * 4. 채팅 내용
	 *
	 *
	*/
	
	public static byte[] remainTime(long time) { // ms 단위로 전송
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.TIMER);
		packet.writeLong(time);
		return packet.getPacket();
	}
	
	public static byte[] changeDayAndNight(boolean night) { // 밤이면 true
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.DAY_AND_NIGHT);
		packet.writeBoolean(night);
		return packet.getPacket();		
	}
	
	public static byte[] resultVote() { // 투표 결과 전송
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.VOTE_RESULT);
		// 
		return packet.getPacket();		
	}
}
