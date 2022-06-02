package handling.packet;

import client.MafiaClient;
import handling.lobby.WaitingRoom;
import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

public class RoomPacketCreator {
	/*
	 *  대기실에 있는 사람들에게 전송할 패킷
	 * 
	 *  
	 * 1. 레디 상태 변경 될 때
	 * 2. 게임 끝난 후 레벨, 티어가 변경될 때
	 * 
	 */
	
	public static byte[] updateRoom(MafiaClient c) {
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.ROOM_UPDATE);
		System.out.println("updateRoom");
		packet.writeInt(c.getAccId()); // id( 키 값 )
		packet.writeInt(c.getWaitingRoom().getLeader().getAccId()); // 방장 id
		packet.writeString(c.getCharName());
		packet.writeBoolean(c.isReady()); // 준비 상태
		packet.writeInt(c.getLevel()); // 레벨 
		packet.writeInt(c.getGrade()); // 티어
		System.out.println("updateRoom");
		return packet.getPacket();
	}
	
	public static byte[] exitClient(int accId) {
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.EXIT_ROOM);
		packet.writeInt(accId);
		return packet.getPacket();				
	}
	
	public static byte[] roomUserInformation(boolean enter, WaitingRoom room) { // 미사용
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.ENTER_ROOM);
		packet.writeBoolean(enter); // 입장 가능 여부
		if(enter && room != null) {
			int users = room.getOnlines();
			packet.writeInt(users); // 유저 수
			for(MafiaClient c : room.getClients()) {
				packet.writeInt(c.getAccId()); // accid
				packet.writeString(c.getCharName()); // charName
				packet.writeBoolean(c.isReady()); // 준비 상태
				packet.writeInt(c.getLevel()); // 레벨
				packet.writeInt(c.getGrade()); // 티어
			}
 		}
		return packet.getPacket();
	
	}
	
}
