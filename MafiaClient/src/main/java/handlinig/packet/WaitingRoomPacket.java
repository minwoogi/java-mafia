package handlinig.packet;

import handling.packet.header.SendHeader;
import tools.MafiaPacketWriter;

public class WaitingRoomPacket {

	public static byte[] quitWaitingRoom() {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.EXIT_ROOM);
		writer.writeInt(999);
		return writer.getPacket();
	}
	
}
