package tools.packet;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class MafiaPacketReader {
	private final Charset charset = Charset.forName("UTF-8");
	private final byte[] bf;
	private int position = 0;
	private final int length;
	private final int header;
	
	public MafiaPacketReader(byte[] bytes) {
		this.bf = bytes;
		this.length = bf.length;
		this.position = 0;
		this.header = this.readInt();
	}
	public final int getHeader() {
		return this.header;
	}
	
	public final byte[] getBytes() {
		return bf;
	}
	
	public final byte readByte() {
		if(position >= length) {
			System.out.println("[MafiaPacketReader] 패킷의 범위를 벗어났습니다.");
			return -1;
		}
		return bf[position++];
	}
	
	public final boolean readBoolean() { // 1이면 true
		byte bool = this.readByte();
		return bool == 1;
	}
	
	public final int readInt() {
        final int byte1 = readByte();
        final int byte2 = readByte();
        final int byte3 = readByte();
        final int byte4 = readByte();
        return (byte4 << 24) + (byte3 << 16) + (byte2 << 8) + byte1;
	}
	
	public final long readLong() {
        final long byte1 = readByte();
        final long byte2 = readByte();
        final long byte3 = readByte();
        final long byte4 = readByte();
        final long byte5 = readByte();
        final long byte6 = readByte();
        final long byte7 = readByte();
        final long byte8 = readByte();
        return ((byte8 << 56) + (byte7 << 48) + (byte6 << 40) + (byte5 << 32) + (byte4 << 24) + (byte3 << 16)
                + (byte2 << 8) + byte1);
	}
	
	public final String readString() {
		int n = readInt();
        byte ret[] = new byte[n];
        for (int x = 0; x < n; x++) {
            ret[x] = readByte();
        }
        return new String(ret, Charset.forName("MS949"));
	}
	
	
}
