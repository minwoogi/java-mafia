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
		int packetLength = in.readInt();
		byte[] decode = new byte[packetLength];
		in.readBytes(decode);
		out.add(decode);
	}
}