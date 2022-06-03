package handlinig.packet;

import handling.packet.header.SendHeader;
import tools.MafiaPacketWriter;

public class GamePacket {

	
	public static byte[] makeMessagePacket(String text) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.CHAT);
		writer.writeString(text);
		return writer.getPacket();
	}
	
	public static byte[] makeVotePacket(int gameNumber) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.VOTE);
		writer.writeInt(gameNumber);
		return writer.getPacket();
	}
}
