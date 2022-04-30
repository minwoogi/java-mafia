package handling.packet;

import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

public class LoginPacketCreator {
	public static byte[] getLoginWhether(boolean suc, int failCode) { // 로그인 성공 여부
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.LOGIN);
		packet.writeBoolean(suc); // 성공이면 1 실패면 0
		packet.writeInt(failCode); // 실패 사유 코드
		return packet.getPacket();
	}
}
