package handling.netty;

import java.nio.ByteBuffer;

import client.MafiaClient;
import handling.lobby.Lobby;
import handling.lobby.WaitingRoom;
import handling.login.handler.LoginHandler;
import handling.login.handler.Register;
import handling.packet.LobbyPacketCreator;
import handling.packet.LoginPacketCreator;
import handling.packet.RegisterPacketCreator;
import handling.packet.header.ReceiveHeader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import tools.packet.MafiaPacketReader;

public class MafiaNettyHandler extends SimpleChannelInboundHandler<byte[]> {
	private ByteBuf tmp;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		MafiaClient client = new MafiaClient(ctx.channel()); // 추후 수정
		ctx.channel().attr(MafiaClient.CLIENTKEY).set(client);
		System.out.println(ctx.channel().remoteAddress() + "에서 서버에 접속했습니다.");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		MafiaClient c = ctx.channel().attr(MafiaClient.CLIENTKEY).get();
		if(c.getLocation() != -1) {
			
		}
		ctx.channel().attr(MafiaClient.CLIENTKEY).set(null);
		System.out.println(ctx.channel().remoteAddress() + "에서 접속을 종료했습니다.");

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
		System.out.println("header : " + header);
		switch (header) {
		case ReceiveHeader.LOGIN: {
			String id = pr.readString();
			String password = pr.readString();
			int login = LoginHandler.canLogin(id, password, c);
			c.getSession().writeAndFlush(LoginPacketCreator.getLoginWhether(login == -1 ? true : false, login));
			if (login == -1) {
				c.login(id);
				System.out.println("[" + c.getInetAddress() + "] 에서 " + id + " 계정으로 로그인에 성공했습니다.");
			} else {
				System.out.println("[" + c.getInetAddress() + "] 에서 " + id + " 계정으로 로그인에 실패했습니다.");
			}
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
			// 이메일 전송
			String email = pr.readString();
			boolean overlap = Register.isOverlapEmail(email);
			boolean sendok = false;
			if(!overlap) {
				String code = Register.getCertificationCode(); // 인증코드 생성
				String text = "인증코드는 [ " + code + " ] 입니다. 해당 코드를 인증창에 입력해 주세요."; 
				c.setCertificationCode(code);
				System.out.println(email + "으로 인증코드를 전송했습니다.");
				System.out.println(text);
				sendok = true;
			} else {
				System.out.println(email + "은 이미 존재하는 이메일 입니다.");
			}
			c.getSession().writeAndFlush(RegisterPacketCreator.completeSendEmail(overlap, sendok));
			break;
		}
		case ReceiveHeader.CERTIFICATION_EMAIL: {
			String inputCode = pr.readString();
			String certCode = c.getCertificationCode();
			boolean equal = inputCode.equals(certCode);
			if(equal) {
				System.out.println("인증코드 일치");
			} else {
				System.out.println("인증코드 틀림");
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
			if(register) {
				System.out.println("[ " + c.getInetAddress() + " ] 에서 ID: " + accName + " NICK : " + charName + " 으로 가입에 성공했습니다.");
			} else {
				System.out.println("[ " + c.getInetAddress() + " ] 에서 ID: " + accName + " NICK : " + charName + " 으로 가입에 실패했습니다.");
			}
			c.getSession().writeAndFlush(RegisterPacketCreator.completeRegister(register));
			break;
		case ReceiveHeader.ENTER_ROOM:
			int roomId = pr.readInt();
			
			break;
		case ReceiveHeader.MAKE_ROOM:
			break;
		case ReceiveHeader.ROOM_UPDATE:
			break;
		case ReceiveHeader.LOBBY_USERS:
			break;
		case ReceiveHeader.EXIT_ROOM:
			break;
		case ReceiveHeader.INVITE_USER:
			break;
		case ReceiveHeader.CHAT:
			break;
		case ReceiveHeader.VOTE:
			break;
		}
	}
}
