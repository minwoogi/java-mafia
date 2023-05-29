package handling.packet;

import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

public class RegisterPacketCreator {
	public static byte[] getIDOverlap(boolean overlap) { // ID �ߺ�üũ
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.ID_OVERLAP);
		packet.writeBoolean(overlap);
		return packet.getPacket();
	}
	
	public static byte[] getNickNameOverlap(boolean overlap) { // �г��� �ߺ� üũ
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.NICK_OVERLAP);
		packet.writeBoolean(overlap);
		return packet.getPacket();	
	}
	
	public static byte[] completeRegister(boolean complete) { // ���� ���� �ƴ���
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.REGISTER);
		packet.writeBoolean(complete);
		return packet.getPacket();				
	}
	
	public static byte[] completeSendEmail(boolean overlap, boolean complete) { //�̸��� �ߺ� , �̸��� ���� �Ϸ� 
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.SEND_EMAIL);
		packet.writeBoolean(overlap);
		packet.writeBoolean(complete);
		return packet.getPacket();		
	}
	
	public static byte[] certificationCodeEqual(boolean equal) { // �����ڵ� ��ġ�ϸ� true
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.CERTIFICATION_EMAIL);
		packet.writeBoolean(equal);
		return packet.getPacket();		
	}
	
}
