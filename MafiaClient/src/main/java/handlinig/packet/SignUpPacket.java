package handlinig.packet;

import handling.packet.header.SendHeader;
import tools.MafiaPacketWriter;

/**
 * 					 회원가입 화면에서 만드는 패킷
 * makeioOverlapPacket() - 아이디 중복 확인을 위해 id정보 패킷 생성
 * makeNickOverlapPacket() - 닉네임 중복 확인을 위해 nickName정보 패킹 생성
 * makeEmailPacket() - 이메일 전송위해 email정보 패킷 생성
 * makeCertificationPakcet() - 이메일 인증코드 맞는지 확인위해 인증코드(int)정보 패킷 생성 
 * makeSignUpPacket() - 회원가입 완료버튼을 누를때 id,pwd,nick,email정보가 담긴 패킷을 생성
 */

public class SignUpPacket {

	public static byte[] makeIdOverlapPacket(String id) {
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
