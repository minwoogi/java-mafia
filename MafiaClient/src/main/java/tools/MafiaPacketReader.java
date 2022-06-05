package tools;


import java.nio.ByteBuffer;
import java.nio.charset.Charset;


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
        final byte byte1 = readByte();
        final byte byte2 = readByte();
        final byte byte3 = readByte();
        final byte byte4 = readByte();
        byte[] bytes = {byte4, byte3, byte2, byte1};
        return ByteBuffer.wrap(bytes).getInt();
	}
	
	public final long readLong() {
        final byte byte1 = readByte();
        final byte byte2 = readByte();
        final byte byte3 = readByte();
        final byte byte4 = readByte();
        final byte byte5 = readByte();
        final byte byte6 = readByte();
        final byte byte7 = readByte();
        final byte byte8 = readByte();
        byte[] bytes = {byte8, byte7, byte6, byte5, byte4, byte3, byte2, byte1};
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
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
