package handlinig.packet;

import packet.MafiaPacketWriter;
import packet.SendHeader;

public class RobyPacket {
	
	
	public static byte[] writePacket(String id) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.LOBBY_UPDATE);
		writer.writeString(id);
		return writer.getPacket();
	}
	
	public static byte[] makeRoomPacket(String roomName,int numberOfPeople) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.MAKE_ROOM);
		writer.writeInt(numberOfPeople);
		writer.writeString(roomName);
		return writer.getPacket();
	}

}
