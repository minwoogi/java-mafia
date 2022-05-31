package handling.netty;

import handling.game.GameHandler;
import handling.packet.header.ReceieveHeader;
import information.RoomInf;
import information.UserInf;
import information.ClientInf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import tools.MafiaPacketReader;
import ui.FrameHandler;
import ui.ShowMessage;

/**
 * Interaction for server 서버와의 상호작용 클래스
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
		System.out.println("헤더 ["+header +"받음]");
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
		case ReceieveHeader.REGISTER: { // * 회원가입 완료 여부 * //
			boolean isRegister = reader.readBoolean();
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
			int userId = reader.readInt();
			String nickName = reader.readString();
			int level = reader.readInt();
			int exp = reader.readInt();
			int tier = reader.readInt();
			ClientInf.setUserId(userId);
			ClientInf.setNickName(nickName);
			ClientInf.setLevel(level);
			ClientInf.setExp(exp);
			ClientInf.setTier(tier);
			break;
		}
		case ReceieveHeader.LOBBY_UPDATE_MAKE: {
			int roomId = reader.readInt(); // * 방 ID * //
			String roomName = reader.readString(); // * 방 이름 * //
			int currentStaff = reader.readInt(); // * 현재원 * //
			int headCount = reader.readInt(); // * 총원 * //
			boolean roomState = reader.readBoolean(); // * 방 상태 * //
			RoomInf roomInf = new RoomInf(roomId, currentStaff, headCount, roomName, roomState); // * 방 객체 생성 * //
			FrameHandler.addRoomPanel(roomInf);
			FrameHandler.getLobbyFrame().getRoomList().put(roomId, roomInf);
			break;
		}
		case ReceieveHeader.MAKE_ROOM: { // * 방 생성후 대기실 입장 * //
			boolean isMakeRoom = reader.readBoolean();
			FrameHandler.failedMakeRoom(isMakeRoom); // * 방 만들어졌는지 확인 * //
			break;
		}
		case ReceieveHeader.CHANGE_LOCATION: { // * 위치 변경시 * //
			int location = reader.readInt(); // * 로비0 대기실1 게임장2 * //
			FrameHandler.warp(location);
			break;
		}
		case ReceieveHeader.ENTER_ROOM: { // * 대기실 입장시 * //
			boolean isEnter = reader.readBoolean();
			if(isEnter) {
				int personNum = reader.readInt(); // * 인원수 * //
				for(int i=0; i<personNum; i++) {
					int userId = reader.readInt();
					String userNick = reader.readString();
					boolean isReady = reader.readBoolean();
					int level = reader.readInt();
					int tier = reader.readInt();
					UserInf userInf = new UserInf(userId,userNick,isReady,level,tier);
					FrameHandler.addUserPanel(userInf);
					FrameHandler.getWaitingRoomFrame().userList.add(userId);
				}				
			}
			break;
		}
		case ReceieveHeader.EXIT_ROOM:{
			int userId = reader.readInt();
			FrameHandler.removeUserPanel(userId,FrameHandler.getWaitingRoomFrame().userPanel.get(userId));
			FrameHandler.getWaitingRoomFrame().userList.remove(userId);
		}
		case ReceieveHeader.ROOM_UPDATE:{
			int userId = reader.readInt();
			String userNick = reader.readString();
			boolean isReady = reader.readBoolean();
			int level = reader.readInt();
			int tier = reader.readInt();
			UserInf userInf = new UserInf(userId,userNick,isReady,level,tier);
			if(FrameHandler.getWaitingRoomFrame().userList.contains(userId)) {
				FrameHandler.updateUserPanel(userInf);
				
			}else {
				FrameHandler.getWaitingRoomFrame().userList.add(userId);
				FrameHandler.addUserPanel(userInf);							
			}
			break;	
		}
		case ReceieveHeader.SHOW_MESSAGE: { // * 알림창 생성 * //
			int msgType = reader.readInt();
			String title = reader.readString();
			String message = reader.readString();
			ShowMessage showMsg = new ShowMessage(msgType, title, message);
		}
		case ReceieveHeader.TIMER: {
			long remainTime = reader.readLong();
			GameHandler.setTimer(remainTime);
		}
		case ReceieveHeader.DAY_AND_NIGHT: { // * 밤 낮 정보 * //
			boolean isNight = reader.readBoolean();
			int day = reader.readInt();
			GameHandler.setNightText(header, GameHandler.getGameFrame().getNightInf());
		}
		case ReceieveHeader.VOTE: {

		}
		case ReceieveHeader.CHAT: {
			String nickName = reader.readString();
			String text = reader.readString();
			GameHandler.addMsg(nickName, text, GameHandler.getGameFrame().getChatArea());
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
