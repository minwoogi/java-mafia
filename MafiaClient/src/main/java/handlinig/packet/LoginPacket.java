package handlinig.packet;

import packet.MafiaPacketWriter;
import packet.SendHeader;

public class LoginPacket {

	public static byte[] makeLoginPacket(String id, String password) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.LOGIN);
		writer.writeString(id);
		writer.writeString(password);
		return writer.getPacket();
	}
	
	

}
