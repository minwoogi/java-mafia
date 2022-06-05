package handling.netty;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MafiaNettyDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if(in.readableBytes() < 4)
			return;
		int packetLength = in.readInt();
		if(in.readableBytes() < packetLength) {
			in.resetReaderIndex();
			return;
		}
		in.markReaderIndex();
		byte[] decode = new byte[packetLength];
		in.readBytes(decode);
		in.markReaderIndex();
		out.add(decode);
	}

}