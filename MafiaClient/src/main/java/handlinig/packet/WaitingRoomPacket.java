package handlinig.packet;

import packet.MafiaPacketWriter;
import packet.SendHeader;

public class WaitingRoomPacket {

	public static byte[] quitWaitingRoom() {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.EXIT_ROOM);
		writer.writeInt(999);
		return writer.getPacket();
	}
	
}
