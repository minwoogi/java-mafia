package handlinig.packet;

import packet.MafiaPacketWriter;
import packet.SendHeader;

public class GamePacket {

	
	public static byte[] makeMessagePacket(String text) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.CHAT);
		writer.writeString(text);
		return writer.getPacket();
	}
	
	public static byte[] makeVotePacket(String nickName) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.VOTE);
		writer.writeString(nickName);
		return writer.getPacket();
	}
}
