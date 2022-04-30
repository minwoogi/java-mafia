package handling.packet;

import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

public class RegisterPacketCreator {
	public static byte[] getIDOverlap(boolean overlap) { // ID 중복체크
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.ID_OVERLAP);
		packet.writeBoolean(overlap);
		return packet.getPacket();
	}
	
	public static byte[] getNickNameOverlap(boolean overlap) { // 닉네임 중복 체크
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.NICK_OVERLAP);
		packet.writeBoolean(overlap);
		return packet.getPacket();	
	}
	
	public static byte[] completeRegister(boolean complete) { // 계정 생성 됐는지
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.REGISTER);
		packet.writeBoolean(complete);
		return packet.getPacket();				
	}
	
	public static byte[] completeSendEmail(boolean overlap, boolean complete) { //이메일 중복 , 이메일 전송 완료 
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.SEND_EMAIL);
		packet.writeBoolean(overlap);
		packet.writeBoolean(complete);
		return packet.getPacket();		
	}
	
	public static byte[] certificationCodeEqual(boolean equal) { // 인증코드 일치하면 true
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.CERTIFICATION_EMAIL);
		packet.writeBoolean(equal);
		return packet.getPacket();		
	}
	
}
