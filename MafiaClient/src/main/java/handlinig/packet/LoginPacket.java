package handlinig.packet;

import handling.packet.header.SendHeader;
import tools.MafiaPacketWriter;

/**
 * 				로그인 화면에서 만드는 패킷
 * makeLoginPacket() - 로그인시 서버로 (id,password)정보 패킷 생성
 * 
 */


public class LoginPacket {

	public static byte[] makeLoginPacket(String id, String password) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.LOGIN);
		writer.writeString(id);
		writer.writeString(password);
		return writer.getPacket();
	}
	
	

}
