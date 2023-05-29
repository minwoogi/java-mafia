package handling.packet;

import client.MafiaClient;
import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

public class ClientPacketCreator {
	
	public static byte[] showMessage(int type, String title, String msg, int msgId) {
		// type에 맞는 메시지 창을 띄운다.
		/*
		 * type 별 메시지 형태
		 * 1 : 에러(X) 메시지 (OK)
		 * 2 : 정보(i) 메시지 (OK)
		 * 3 : 질문(?) 메시지 (OK)
		 * 4 : 경고(!) 메시지 (OK)
		 * 5 : 게임장 내 일반 메시지 (흰색) title은 null
		 * 6 : 게임장 내 공지 메시지 (굵은 파란색) title은 null
		 * 7 : 게임장 내 공지 메시지 (굵은 빨간색) title은 null
		 * 8 : Yes or No
		 * 9 : Yes or No or Cancel
		 */
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.SHOW_MESSAGE);
		packet.writeInt(type);
		packet.writeString(title == null ? "알림" : title);
		packet.writeString(msg);
		packet.writeInt(msgId);
		return packet.getPacket();
	}

	public static byte[] setLeader(boolean isLeader) {
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.LEADER);
		packet.writeBoolean(isLeader);
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
		// 유저 정보 전송
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.USER_INFORMATION);
		packet.writeInt(c.getAccId()); 
		packet.writeString(c.getCharName());
		packet.writeInt(c.getLevel());
		packet.writeInt(c.getExp());
		packet.writeInt(c.getGrade());
		return packet.getPacket();
	}
}
