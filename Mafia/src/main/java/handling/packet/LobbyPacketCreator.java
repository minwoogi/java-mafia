package handling.packet;

import client.MafiaClient;
import handling.lobby.Lobby;
import handling.lobby.WaitingRoom;
import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

public class LobbyPacketCreator {
	
	public static byte[] updateRoom(WaitingRoom room) {
		// 클라측에서 받아들인 방 ID로 해당 방의 내용을 업데이트 시켜준다.
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.LOBBY_UPDATE);
		packet.writeInt(room.getId()); // 방 ID
		packet.writeString(room.getName()); // 방이름
		packet.writeInt(room.getOnlines()); // 현원
		packet.writeInt(room.getMaxPerson()); // 총원
		packet.writeBoolean(room.isStart()); // 방 상태 : true 시작 / false 대기
		return packet.getPacket();
	}
	
	public static byte[] removeRoom(int roomId) {
		// 클라측에서 받아들인 방 ID로 해당 방을 화면에서 지운다.
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.LOBBY_REMOVE_ROOM);
		packet.writeInt(roomId);
		return packet.getPacket();		
	}
	
	public static byte[] loadUsers() {
		// 초대하기 버튼 클릭 시 전송
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.LOBBY_USERS);
		packet.writeInt(Lobby.getClients().size());
		for(MafiaClient c : Lobby.getClients()) {
			packet.writeInt(c.getAccId()); // id 값
			packet.writeString(c.getCharName()); // 닉네임
			packet.writeInt(c.getLevel()); // 레벨
		}
		return packet.getPacket();
	}
	
}
