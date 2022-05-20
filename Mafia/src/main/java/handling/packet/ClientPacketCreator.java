package handling.packet;

import client.MafiaClient;
import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

public class ClientPacketCreator {
	
	public static byte[] showMessage(int type, String title, String msg) {
		// type에 맞는 메시지 창을 띄운다.
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.SHOW_MESSAGE);
		packet.writeInt(type);
		packet.writeString(title);
		packet.writeString(msg);
		return packet.getPacket();
	}

	public static byte[] makeRoom() {
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.MAKE_ROOM);
		packet.writeBoolean(true);
		return packet.getPacket();
		
	}
	public static byte[] warp(int location, int roomId) { 
		// 클라이언트 측에서 location으로 이동시키고 이동 시킬 곳이 대기실이면 roomId을 가진 방으로 이동시킨다.
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.CHANGE_LOCATION);
		packet.writeInt(location);
		packet.writeInt(roomId);
		return packet.getPacket();
	}
	
	public static byte[] userInformation(MafiaClient c) {
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.USER_INFORMATION);
		packet.writeString(c.getCharName());
		packet.writeInt(c.getLevel());
		packet.writeInt(c.getExp());
		packet.writeInt(c.getGrade());
		System.out.println("레벨 : " + c.getLevel());
		System.out.println("경험치 : " + c.getExp());
		System.out.println("티어 : " + c.getGrade());
		return packet.getPacket();
	}
}
