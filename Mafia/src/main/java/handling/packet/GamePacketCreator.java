package handling.packet;

import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

public class GamePacketCreator {
	/*
	 * ������ ���� ��Ŷ�� �����ϴ� Ŭ����
	 * 
	 * 1. ��нð�, ��ǥ�ð�, �ɷ� ��� �ð� �� �ð����� ����
	 * 2. ��, �� ��ȯ ���
	 * 3. 
	 *
	*/
	
	
	public static byte[] remainTime(long time) { 
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.TIMER);
		packet.writeLong(time);
		return packet.getPacket();
	}
	
	public static byte[] nowDays(int days) { // �� �� ° ������ ����
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.WHAT_DATE);
		packet.writeInt(days);
		return packet.getPacket();
	}
	
	public static byte[] changeDayAndNight(boolean night) { // ���̸� true
		// ���Ǿ� ������ ���� �� ���� �����Ѵ�.
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.DAY_AND_NIGHT);
		packet.writeBoolean(night);
		return packet.getPacket();		
	}
	
}
