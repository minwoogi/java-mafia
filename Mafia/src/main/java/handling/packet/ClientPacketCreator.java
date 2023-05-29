package handling.packet;

import client.MafiaClient;
import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

public class ClientPacketCreator {
	
	public static byte[] showMessage(int type, String title, String msg, int msgId) {
		// type�� �´� �޽��� â�� ����.
		/*
		 * type �� �޽��� ����
		 * 1 : ����(X) �޽��� (OK)
		 * 2 : ����(i) �޽��� (OK)
		 * 3 : ����(?) �޽��� (OK)
		 * 4 : ���(!) �޽��� (OK)
		 * 5 : ������ �� �Ϲ� �޽��� (���) title�� null
		 * 6 : ������ �� ���� �޽��� (���� �Ķ���) title�� null
		 * 7 : ������ �� ���� �޽��� (���� ������) title�� null
		 * 8 : Yes or No
		 * 9 : Yes or No or Cancel
		 */
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.SHOW_MESSAGE);
		packet.writeInt(type);
		packet.writeString(title == null ? "�˸�" : title);
		packet.writeString(msg);
		packet.writeInt(msgId);
		return packet.getPacket();
	}

	public static byte[] setLeader(boolean isLeader) {
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.LEADER);
		packet.writeBoolean(isLeader);
		return packet.getPacket();
	}
	public static byte[] makeRoom() {
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.MAKE_ROOM);
		packet.writeBoolean(true);
		return packet.getPacket();
		
	}
	public static byte[] warp(int location, int roomId) { 
		// Ŭ���̾�Ʈ ������ location���� �̵���Ű�� �̵� ��ų ���� �����̸� roomId�� ���� ������ �̵���Ų��.
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.CHANGE_LOCATION);
		packet.writeInt(location);
		packet.writeInt(roomId);
		return packet.getPacket();
	}
	
	public static byte[] userInformation(MafiaClient c) {
		// ���� ���� ����
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.USER_INFORMATION);
		packet.writeInt(c.getAccId()); 
		packet.writeString(c.getCharName());
		packet.writeInt(c.getLevel());
		packet.writeInt(c.getExp());
		packet.writeInt(c.getGrade());
		return packet.getPacket();
	}
}
