package handling.netty;

import java.util.List;

import client.MafiaClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MafiaNettyDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		MafiaClient client = ctx.channel().attr(MafiaClient.CLIENTKEY).get();
		if (client == null) {
			return;
		}
		if (in.readableBytes() < 4) {
			return;
		}
		System.out.println("읽을 수 있는 패킷 길이 : " + in.readableBytes());
		int packetLength = in.readInt();
		System.out.println("받은 패킷 길이 : " + packetLength);
		byte[] decode = new byte[packetLength];
		in.readBytes(decode);
		out.add(decode);
	}
}