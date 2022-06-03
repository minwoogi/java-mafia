package handlinig.packet;

import handling.packet.header.SendHeader;
import tools.MafiaPacketWriter;

public class WaitingRoomPacket {

	public static byte[] quitWaitingRoom(int userId) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.EXIT_ROOM);
		writer.writeInt(userId);
		return writer.getPacket();
	}
	
	public static byte[] makeReadyPacket(boolean isReady) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.READY);
		writer.writeBoolean(isReady);
		return writer.getPacket();
	}
	
	public static byte[] makeInvitePacket(String nickName) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.INVITE_USER);
		writer.writeString(nickName);
		return writer.getPacket();
	}
	
	public static byte[] makeGameStartPacket(int start) { // * 게임시작 정보 만드는 패킷 * //
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.START_GAME);
		writer.writeInt(start);
		return writer.getPacket();
	}
	
}
