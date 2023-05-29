package handling.netty;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import client.MafiaClient;
import handling.lobby.Lobby;
import handling.lobby.WaitingRoom;
import handling.login.handler.LoginHandler;
import handling.login.handler.Register;
import handling.packet.ClientPacketCreator;
import handling.packet.LobbyPacketCreator;
import handling.packet.LoginPacketCreator;
import handling.packet.RegisterPacketCreator;
import handling.packet.RoomPacketCreator;
import handling.packet.header.ReceiveHeader;
import handling.packet.header.SendHeader;
import information.LocationInformation;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import tools.packet.MafiaPacketReader;
import tools.packet.MafiaPacketWriter;

public class MafiaNettyHandler extends SimpleChannelInboundHandler<byte[]> {
	private ByteBuf tmp;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		MafiaClient client = new MafiaClient(ctx.channel()); // ���� ����
		ctx.channel().attr(MafiaClient.CLIENTKEY).set(client);
		System.out.println(ctx.channel().remoteAddress() + "���� ������ �����߽��ϴ�.");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		MafiaClient c = ctx.channel().attr(MafiaClient.CLIENTKEY).get();
		if (c.getLocation() != -1) { // �α���â�� �ƴϸ�
			c.disconnect();
		}
		ctx.channel().attr(MafiaClient.CLIENTKEY).set(null);
		System.out.println(ctx.channel().remoteAddress() + "���� ������ �����߽��ϴ�.");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// cause.printStackTrace();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
		MafiaClient c = ctx.channel().attr(MafiaClient.CLIENTKEY).get();
		MafiaPacketReader pr = new MafiaPacketReader(msg);
		int header = pr.getHeader();
		Field[] fields = ReceiveHeader.class.getDeclaredFields();
		for (Field field : fields) {
			if ((int) field.get(field) == header) {
				System.out.println("RECEIVE : [" + field.getName() + "] ("+pr.getBytes().length+")");
			}
		}
		switch (header) {
		case ReceiveHeader.LOGIN: {
			String id = pr.readString();
			String password = pr.readString();
			int login = LoginHandler.canLogin(id, password, c);
			// c.getSession().writeAndFlush(LoginPacketCreator.getLoginWhether(login == -1 ?
			// true : false, login));
			if (login == -1) {
				c.login(id);
				System.out.println("[" + c.getInetAddress() + "] ���� " + id + " �������� �α��ο� �����߽��ϴ�.");
			} else if (login == 1) {
				c.dropMessage(8, "���̵� �Ǵ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			} else if (login == 2) {
				c.dropMessage(4, "'" + id + "' ������ ���� �������� �����Դϴ�.");
			}
			break;
		}
		case ReceiveHeader.LOGOUT: {
			int userId = pr.readInt();
			c.disconnect();
			break;
		}
		case ReceiveHeader.ID_OVERLAP: {
			String id = pr.readString();
			boolean overlap = Register.isOverlapID(id);
			System.out.println("ID Overlap : " + overlap);
			c.getSession().writeAndFlush(RegisterPacketCreator.getIDOverlap(overlap));
			break;
		}
		case ReceiveHeader.NICK_OVERAP: {
			String name = pr.readString();
			boolean overlap = Register.isOverlapNickName(name);
			System.out.println("NICK Overlap : " + overlap);
			c.getSession().writeAndFlush(RegisterPacketCreator.getNickNameOverlap(overlap));
			break;
		}
		case ReceiveHeader.SEND_EMAIL: {
			// �̸��� ����
			String email = pr.readString();
			boolean overlap = Register.isOverlapEmail(email);
			boolean sendok = false;
			if (!overlap) {
				String code = Register.getCertificationCode(); // �����ڵ� ����
				String text = "�����ڵ�� [ " + code + " ] �Դϴ�. �ش� �ڵ带 ����â�� �Է��� �ּ���.";
				c.setCertificationCode(code);
				System.out.println(email + "���� �����ڵ带 �����߽��ϴ�.");
				System.out.println(text);
				sendok = true;
			} else {
				System.out.println(email + "�� �̹� �����ϴ� �̸��� �Դϴ�.");
			}
			c.getSession().writeAndFlush(RegisterPacketCreator.completeSendEmail(overlap, sendok));
			break;
		}
		case ReceiveHeader.CERTIFICATION_EMAIL: {
			String inputCode = pr.readString();
			String certCode = c.getCertificationCode();
			boolean equal = inputCode.equals(certCode);
			if (equal) {
				System.out.println("�����ڵ� ��ġ");
			} else {
				System.out.println("�����ڵ� Ʋ��");
			}
			c.getSession().writeAndFlush(RegisterPacketCreator.certificationCodeEqual(equal));
			break;
		}
		case ReceiveHeader.REGISTER:
			String accName = pr.readString();
			String password = pr.readString();
			String charName = pr.readString();
			String email = pr.readString();
			boolean register = Register.register(accName, password, charName, email);
			if (register) {
				System.out.println(
						"[ " + c.getInetAddress() + " ] ���� ID: " + accName + " NICK : " + charName + " ���� ���Կ� �����߽��ϴ�.");
			} else {
				System.out.println(
						"[ " + c.getInetAddress() + " ] ���� ID: " + accName + " NICK : " + charName + " ���� ���Կ� �����߽��ϴ�.");
			}
			c.getSession().writeAndFlush(RegisterPacketCreator.completeRegister(register));
			break;
		case ReceiveHeader.ENTER_ROOM: {
			/*
			 * 1. warp 2. �������� ����
			 * 
			 */
			int roomId = pr.readInt();
			WaitingRoom room = Lobby.getRoom(roomId);
			c.warp(LocationInformation.WAITING_ROOM, room);
			break;
		}
		case ReceiveHeader.MAKE_ROOM: {
			int maxPerson = pr.readInt();
			String roomName = pr.readString();
			WaitingRoom room = new WaitingRoom(roomName, c, maxPerson);
			Lobby.addRoom(room);
			c.warp(LocationInformation.WAITING_ROOM, room);
			break;
		}
		case ReceiveHeader.READY: {
			/*
			 * ���ǿ��� ������ �غ� ��ư�� ���� �� �ش� ������ ��� �������� �Ʒ� ��Ŷ ����
			 */

			boolean isReady = pr.readBoolean();
			c.setReady(isReady);
			c.getWaitingRoom().broadCast(RoomPacketCreator.updateRoom(c));
			break;
		}
		case ReceiveHeader.LOBBY_USERS: {
			int tmp = pr.readInt();
			c.getSession().writeAndFlush(LobbyPacketCreator.loadUsers()); // �κ� ���� ���� ����
			break;
		}
		case ReceiveHeader.EXIT_ROOM: {
			int tmp = pr.readInt();
			c.warp(LocationInformation.LOBBY);
			break;
		}
		case ReceiveHeader.SHOW_MESSAGE: {
			int msgId = pr.readInt();
			int flag = pr.readInt();
			System.out.println("SHOW_MESSAGE : msgId : " + msgId + ", flag : " + flag);
			break;
		}
		case ReceiveHeader.INVITE_USER:
			break;
		case ReceiveHeader.CHAT:
			String message = pr.readString();
			c.getWaitingRoom().getGame().dropMessage(5, message);
			break;
		case ReceiveHeader.VOTE:

			break;
		case ReceiveHeader.END_TIMER:
			// Ŭ���� Ÿ�̸� ���� �� ����
			int tmp = pr.readInt();
			if (c.getWaitingRoom().getLeader().getAccId() == c.getAccId()) { // ��û�ڰ� �����̸�
				c.getWaitingRoom().getGame().setStatus(c.getWaitingRoom().getGame().getNextPhase()); // ���� �ð���� ����
			}
			break;
		}
	}
}
