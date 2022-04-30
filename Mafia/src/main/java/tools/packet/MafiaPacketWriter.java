package tools.packet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MafiaPacketWriter {
	private final ByteArrayOutputStream baos;
	private final int header;
	public MafiaPacketWriter(final int header, final int size) {
		this.baos = new ByteArrayOutputStream(size);
		this.header = header;
		this.writeInt(header);
	}

	public MafiaPacketWriter(int header) {
		this(header, 32);
	}

	public int getHeader() {
		return this.header;
	}
	public final byte[] getPacket() {
		return baos.toByteArray();
	}

	public final void write(byte b) {
		baos.write(b);
	}
	
	public final void writeBoolean(boolean bool) {
		baos.write(bool ? 1 : 0); 
	}

	public final void writeString(String s) {
		try {
			writeInt(s.getBytes().length);
			baos.write(s.getBytes("MS949"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final void writeInt(final int i) {
		if (i != -88888) {
			baos.write((byte) (i & 0xFF));
			baos.write((byte) ((i >>> 8) & 0xFF));
			baos.write((byte) ((i >>> 16) & 0xFF));
			baos.write((byte) ((i >>> 24) & 0xFF));
		}
	}

	public void writeInt(long i) {
		baos.write((byte) (i & 0xFF));
		baos.write((byte) ((i >>> 8) & 0xFF));
		baos.write((byte) ((i >>> 16) & 0xFF));
		baos.write((byte) ((i >>> 24) & 0xFF));
	}

	public final void writeLong(final long l) {
		baos.write((byte) (l & 0xFF));
		baos.write((byte) ((l >>> 8) & 0xFF));
		baos.write((byte) ((l >>> 16) & 0xFF));
		baos.write((byte) ((l >>> 24) & 0xFF));
		baos.write((byte) ((l >>> 32) & 0xFF));
		baos.write((byte) ((l >>> 40) & 0xFF));
		baos.write((byte) ((l >>> 48) & 0xFF));
		baos.write((byte) ((l >>> 56) & 0xFF));
	}

	public final void writeDouble(double d) {
		writeLong(Double.doubleToLongBits(d));
	}
}
