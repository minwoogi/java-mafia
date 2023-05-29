package handlinig.packet;

import handling.packet.header.SendHeader;
import tools.MafiaPacketWriter;

/**
 * 				�α��� ȭ�鿡�� ����� ��Ŷ
 * makeLoginPacket() - �α��ν� ������ (id,password)���� ��Ŷ ����
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
