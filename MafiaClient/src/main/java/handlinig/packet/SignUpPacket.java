package handlinig.packet;

import packet.MafiaPacketWriter;
import packet.SendHeader;

public class SignUpPacket {

	public static byte[] makeidOverlapPacket(String id) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.OVERLAP);
		writer.writeString(id);
		return writer.getPacket();
	}

	public static byte[] makeNickOverlapPacket(String nickName) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.NICK_OVERLAP);
		writer.writeString(nickName);
		return writer.getPacket();
	}

	public static byte[] makeEmailPacket(String email) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.SEND_EMAIL);
		writer.writeString(email);
		return writer.getPacket();
	}

	public static byte[] makeCertificationPacket(String code) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.CERTIFICATION_EMAIL);
		writer.writeString(code);
		return writer.getPacket();
	}

	public static byte[] makeSignUpPacket(String id,String pwd ,String nick , String email) {
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.REGISTER);
		writer.writeString(id);
		writer.writeString(pwd);
		writer.writeString(nick);
	    writer.writeString(email);
		return writer.getPacket();
	}

}
