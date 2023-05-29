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
import ui.LobbyFrame;
import ui.ShowMessage;

/**
 * Interaction for server �������� ��ȣ�ۿ� Ŭ����
 */

public class ClientHandler extends ChannelInboundHandlerAdapter {
	static Channel server;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // * ���� ���� * //
		server = ctx.channel();
		System.out.println("Server Connect");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // * ���� ���� ����� * //
		System.out.println("Server Disconnected");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		byte[] packet = (byte[]) msg;
		MafiaPacketReader reader = new MafiaPacketReader(packet);
		int header = reader.getHeader(); // * header * //
		switch (header) {
		case ReceieveHeader.ID_OVERLAP: { // * ID �ߺ�Ȯ�� * //
			boolean idOverlapCheck = reader.readBoolean();
			FrameHandler.useId(idOverlapCheck);
			break;
		}
		case ReceieveHeader.SEND_EMAIL: { // * �̸��� ���� * //
			boolean emailOverlapCheck = reader.readBoolean();
			boolean completeSendEmail = reader.readBoolean();
			FrameHandler.checkEmail(emailOverlapCheck, completeSendEmail);
			break;
		}
		case ReceieveHeader.NICK_OVERLAP: { // * �г��� �ߺ� * //
			boolean nickOverlapCheck = reader.readBoolean();
			FrameHandler.useNickName(nickOverlapCheck);
			break;
		}
		case ReceieveHeader.CERTIFICATION_EMAIL: { // * �̸��� ���� * //
			boolean isCertificate = reader.readBoolean();
			FrameHandler.checkEmailCode(isCertificate);
			break;
		}
		case ReceieveHeader.REGISTER: { // * ȸ������ �Ϸ� ���� * //
			boolean isRegister = reader.readBoolean();
			FrameHandler.completeRegister(isRegister);
			break;
		}
		case ReceieveHeader.LOBBY_UPDATE: {
			System.out.println("��� [LOBBY_UPDATE����]");
			int roomId = reader.readInt(); // * �� ID * //
			String roomName = reader.readString(); // * �� �̸� * //
			int currentStaff = reader.readInt(); // * ����� * //
			int headCount = reader.readInt(); // * �ѿ� * //
			boolean roomState = reader.readBoolean(); // * �� ���� * //
			RoomInf roomInf = new RoomInf(roomId, currentStaff, headCount, roomName, roomState); // * �� ��ü ���� * //
			if(FrameHandler.getLobbyFrame().getRoomList().containsKey(roomId)) { // * �̹� ���� �����ϸ� ������Ʈ ������ �߰� * //
				FrameHandler.updateRoomPanel(roomId, roomInf);								
			}else {
				FrameHandler.addRoomPanel(roomInf);
			}
			break;
		}
		case ReceieveHeader.USER_INFORMATION: { // * ȸ�� ���� ������Ʈ * //
			System.out.println("��� [USER_INFORMATION����]");
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
		case ReceieveHeader.CHANGE_LOCATION: { // * ��ġ ����� * //
			System.out.println("��� [CHANGE_LOCATION����]");
			int location = reader.readInt(); // * �κ�0 ����1 ������2 * //
			FrameHandler.warp(location);
			break;
		}
		case ReceieveHeader.ENTER_ROOM: { // * ���� ����� * //
			System.out.println("��� [ENTER_ROOM����]");
			boolean isEnter = reader.readBoolean();
			if(isEnter) {
				int personNum = reader.readInt(); // * �ο��� * //
				for(int i=0; i<personNum; i++) {
					int userId = reader.readInt();
					String userNick = reader.readString();
					boolean isReady = reader.readBoolean();
					int level = reader.readInt();
					int tier = reader.readInt();
					UserInf userInf = new UserInf(userId,userNick,isReady,level,tier);
					FrameHandler.addUserPanel(userInf);
					FrameHandler.getWaitingRoomFrame().userList.put(userId,userNick);
				}				
			}
			break;
		}
		case ReceieveHeader.EXIT_ROOM:{ // * ���� ���� * //
			System.out.println("���[EXIT_ROOM����]");
			int userId = reader.readInt();
			FrameHandler.removeUserPanel(userId,FrameHandler.getWaitingRoomFrame().userPanel.get(userId));
			FrameHandler.getWaitingRoomFrame().userList.remove(userId);
			break;
		}
		case ReceieveHeader.LOBBY_REMOVE_ROOM:{ // * �� ���� �ɶ� �κ� ������Ʈ * //
			System.out.println("���[LOBBY_REMOVE_ROOM����]");
			int roomId = reader.readInt();
			try {
				FrameHandler.removeRoomPanel(roomId,FrameHandler.getLobbyFrame().getRoomList().get(roomId));				
			}catch(NullPointerException e) {
			}
			break;
		}
		
		case ReceieveHeader.ROOM_UPDATE:{ // * ���� ���� �� * //
			System.out.println("��� [ROOM_UPDATE����]");
			int userId = reader.readInt();
			int superId = reader.readInt();
			String userNick = reader.readString();
			boolean isReady = reader.readBoolean();
			int level = reader.readInt();
			int tier = reader.readInt();
			UserInf userInf = new UserInf(userId,userNick,isReady,level,tier);
			if(FrameHandler.getWaitingRoomFrame().userList.containsKey(userId)) {
				FrameHandler.updateUserPanel(userInf);
			}else {
				FrameHandler.getWaitingRoomFrame().userList.put(userId,userNick);
				FrameHandler.addUserPanel(userInf);							
			}
			break;	
		}
		case ReceieveHeader.LEADER:{ // * ���� ���� * //
			boolean isLeader = reader.readBoolean();
			if(isLeader) {
				FrameHandler.setLeaderMode(FrameHandler.getWaitingRoomFrame().getReadyBtn());				
			}
			break;
		}
		case ReceieveHeader.INVITE_USER:{ // * �ʴ��ϱ� ���� ���� * //
			String nickName = reader.readString();
			FrameHandler.addInvteUser(nickName);
			break;
		}
		case ReceieveHeader.SHOW_MESSAGE: { // * �˸�â ���� * //
			System.out.println("��� [SHOW_MESSAGE����]");
			int msgType = reader.readInt();
			String title = reader.readString();
			String message = reader.readString();
			int messageId = reader.readInt();
			ShowMessage showMsg = new ShowMessage(msgType, title, message,messageId);
			break;
		}
		case ReceieveHeader.TIMER: {
			long remainTime = reader.readLong();
			GameHandler.setTimer(remainTime);
			break;
		}
		case ReceieveHeader.DAY_AND_NIGHT: { // * �� �� ���� * //
			boolean isNight = reader.readBoolean();
			int day = reader.readInt();
			GameHandler.setNightText(header, GameHandler.getGameFrame().getNightInf());
			break;
		}
		case ReceieveHeader.VOTE: {
			break;
		}
		case ReceieveHeader.CHAT: {
			System.out.println("[��� CHAT����]");
			String nickName = reader.readString();
			String text = reader.readString();
			GameHandler.addMsg(nickName, text, GameHandler.getGameFrame().getChatArea());
			break;
		}
		default :{
			System.out.println("[default ��� ����]");
			break;
		}
		}
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	public static void send(byte[] packet) { // * ������ ��Ŷ �����ϴ� �޼�
		server.writeAndFlush(packet);
		System.out.println("��Ŷ ���� �Ϸ� ( packet size : " + packet.length + ")");
	}
}
