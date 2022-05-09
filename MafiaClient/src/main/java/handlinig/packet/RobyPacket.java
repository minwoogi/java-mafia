package handlinig.packet;

import packet.MafiaPacketWriter;
import packet.SendHeader;

/**
 * 로비 화면에서 만드는 패킷
 * 
 * makeRoomPacket() - 방만들때 방인원과 방이름 정보 패킷 생성
 * 
 * 
 */

public class RobyPacket {

	public static byte[] writePacket(String id) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.LOBBY_UPDATE);
		writer.writeString(id);
		return writer.getPacket();
	}

	public static byte[] makeRoomPacket(String roomName, int numberOfPeople) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.MAKE_ROOM);
		writer.writeInt(numberOfPeople);
		writer.writeString(roomName);
		return writer.getPacket();
	}

}
