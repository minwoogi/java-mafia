package handling.packet;

import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

public class LoginPacketCreator {
	public static byte[] getLoginWhether(boolean suc, int failCode) { // �α��� ���� ����
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.LOGIN);
		packet.writeBoolean(suc); // �����̸� 1 ���и� 0
		packet.writeInt(failCode); // ���� ���� �ڵ�
		return packet.getPacket();
	}
}
