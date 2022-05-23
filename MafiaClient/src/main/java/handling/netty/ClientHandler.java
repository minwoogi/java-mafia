package handling.netty;

import information.RoomInf;
import information.UserInf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import packet.MafiaPacketReader;
import packet.ReceieveHeader;
import ui.FrameHandler;
import ui.ShowMessage;

/**
 * Interaction for server
 * 서버와의 상호작용 클래스
 */

public class ClientHandler extends ChannelInboundHandlerAdapter {
	static Channel server;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // * 서버 연결 * //
		server = ctx.channel();
		System.out.println("Server Connect");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // * 서버 연결 끊길시 * //
		System.out.println("Server Disconnected");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		byte[] packet = (byte[]) msg;
		MafiaPacketReader reader = new MafiaPacketReader(packet);
		int header = reader.getHeader(); // * header * //
		System.out.println( header + "받음");
		switch (header) {
		case ReceieveHeader.LOGIN: // * 로그인시 * //
			boolean loginCheck = reader.readBoolean();
			FrameHandler.failedLogin(loginCheck);
			break;
		case ReceieveHeader.ID_OVERLAP: { // * ID 중복확인 * //
			boolean idOverlapCheck = reader.readBoolean();
			FrameHandler.useId(idOverlapCheck);
			break;
		}
		case ReceieveHeader.SEND_EMAIL: { // * 이메일 전송 * //
			boolean emailOverlapCheck = reader.readBoolean();
			boolean completeSendEmail = reader.readBoolean();
			FrameHandler.checkEmail(emailOverlapCheck, completeSendEmail);
			break;
		}
		case ReceieveHeader.NICK_OVERLAP: { // * 닉네임 중복 * //
			boolean nickOverlapCheck = reader.readBoolean();
			FrameHandler.useNickName(nickOverlapCheck);
			break;
		}
		case ReceieveHeader.CERTIFICATION_EMAIL: { // * 이메일 인증 * //
			boolean isCertificate = reader.readBoolean();
			FrameHandler.checkEmailCode(isCertificate);
			break;
		}
		case ReceieveHeader.REGISTER: {
			boolean isRegister = reader.readBoolean(); // * 회원가입 완료 여부 * //
			FrameHandler.completeRegister(isRegister);
			break;
		}
		case ReceieveHeader.LOBBY_UPDATE: {
			int roomId = reader.readInt(); // * 방 ID * //
			String roomName = reader.readString(); // * 방 이름 * //
			int currentStaff = reader.readInt(); // * 현재원 * //
			int headCount = reader.readInt(); // * 총원 * //
			boolean roomState = reader.readBoolean(); // * 방 상태 * //
			break;
		}
		case ReceieveHeader.USER_INFORMATION: { // * 회원 정보 업데이트 * //
			System.out.println("Header : USER_INFORMATION");
			String nickName = reader.readString();
			int level = reader.readInt();
			int exp = reader.readInt();
			int tier = reader.readInt();
			UserInf.setNickName(nickName);
			UserInf.setLevel(level);
			UserInf.setExp(exp);
			UserInf.setTier(tier);
			break;
		}
		case ReceieveHeader.LOBBY_UPDATE_MAKE: {
			System.out.println("Header : LOBBY_UPDATE_MAKE");
			int roomId = reader.readInt(); // * 방 ID * //
			String roomName = reader.readString(); // * 방 이름 * //
			int currentStaff = reader.readInt(); // * 현재원 * //
			int headCount = reader.readInt(); // * 총원 * //
			boolean roomState = reader.readBoolean(); // * 방 상태 * //
			RoomInf roomInf = new RoomInf(roomId, currentStaff, headCount, roomName, roomState); // * 방 객체 생성 * //
			FrameHandler.addRoomPanel(roomInf);
			break;
		}
		case ReceieveHeader.MAKE_ROOM:{ // * 방 생성후 대기실 입장 * //
			System.out.println("Header : MAKE_ROOM");
			boolean isMakeRoom = reader.readBoolean();
			FrameHandler.failedMakeRoom(isMakeRoom); // * 방 만들어졌는지 확인 * //
			break;
		}
		case ReceieveHeader.ROOM_UPDATE:{// * 대기실 업데이트 * //
			
			
		}
		case ReceieveHeader.CHANGE_LOCATION:{
			System.out.println("Header : CHANGE_LOCATION");
			int location = reader.readInt();
			System.out.println(location);
			FrameHandler.warp(location);
			break;	
		}
		
		case ReceieveHeader.SHOW_MESSAGE:{  // * 알림창 생성 * //
			int msgType = reader.readInt();  
			String title = reader.readString();
			String message = reader.readString();
			ShowMessage showMsg = new ShowMessage(msgType,title,message);
		}
		default: {
			
		}
		}
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	public static void send(byte[] packet) { // * 서버로 패킷 전송하는 메소
		server.writeAndFlush(packet);
		System.out.println("패킷 전송 완료 ( packet size : " + packet.length + ")");
	}
}
