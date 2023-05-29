package handlinig.packet;

import handling.packet.header.SendHeader;
import tools.MafiaPacketWriter;

public class ShowMsgPacket {
	
	public static byte[] makeMessagePacket(int id ,int flag) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.MAKE_ROOM);
		writer.writeInt(id);
		writer.writeInt(flag);
		return writer.getPacket();
	}

}
