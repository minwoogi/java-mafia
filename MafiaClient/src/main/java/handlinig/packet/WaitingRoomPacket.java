package handlinig.packet;

import handling.packet.header.SendHeader;
import tools.MafiaPacketWriter;

public class WaitingRoomPacket {

	public static byte[] quitWaitingRoom() {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.EXIT_ROOM);
		writer.writeInt(999);
		return writer.getPacket();
	}
	
	public static byte[] makeReadyPacket(boolean isReady) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.READY);
		writer.writeBoolean(isReady);
		return writer.getPacket();
	}
	
}
