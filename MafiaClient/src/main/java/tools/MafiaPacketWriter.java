package tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MafiaPacketWriter {
	private final ByteArrayOutputStream baos;
	private final int header;
	private int length;
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
	
	public int getLength() {
		return this.length;
	}
	public final byte[] getPacket() {
		ByteArrayOutputStream tmp = new ByteArrayOutputStream(32);
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((length & 0xFF000000) >> 24);
        bytes[1] = (byte) ((length & 0x00FF0000) >> 16);
        bytes[2] = (byte) ((length & 0x0000FF00) >> 8);
        bytes[3] = (byte) (length & 0x000000FF);
        tmp.write(bytes[0]);
        tmp.write(bytes[1]);
        tmp.write(bytes[2]);
        tmp.write(bytes[3]);
        byte[] org = baos.toByteArray();
        for(int i = 0; i < org.length; i++) {
            tmp.write(org[i]);
        }
        return tmp.toByteArray();
    }

	public final void write(byte b) {
		length++;
		baos.write(b);
	}

	public final void write(int b) {
		this.write((byte) b);
	}
	
	public final void write(byte[] b) {
		for(int i = 0; i < b.length; i++) 
			this.write(b[i]);
	}
	
	public final void writeBoolean(boolean bool) {
		this.write(bool ? 1 : 0); 
	}

	public final void writeString(String s) {
		try {
			writeInt(s.getBytes().length);
			this.write(s.getBytes("MS949"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final void writeInt(final int i) {
		if (i != -88888) {
			this.write((byte) (i & 0xFF));
			this.write((byte) ((i >>> 8) & 0xFF));
			this.write((byte) ((i >>> 16) & 0xFF));
			this.write((byte) ((i >>> 24) & 0xFF));
		}
	}

	public void writeInt(long i) {
		this.write((byte) (i & 0xFF));
		this.write((byte) ((i >>> 8) & 0xFF));
		this.write((byte) ((i >>> 16) & 0xFF));
		this.write((byte) ((i >>> 24) & 0xFF));
	}

	public final void writeLong(final long l) {
		this.write((byte) (l & 0xFF));
		this.write((byte) ((l >>> 8) & 0xFF));
		this.write((byte) ((l >>> 16) & 0xFF));
		this.write((byte) ((l >>> 24) & 0xFF));
		this.write((byte) ((l >>> 32) & 0xFF));
		this.write((byte) ((l >>> 40) & 0xFF));
		this.write((byte) ((l >>> 48) & 0xFF));
		this.write((byte) ((l >>> 56) & 0xFF));
	}

	public final void writeDouble(double d) {
		writeLong(Double.doubleToLongBits(d));
	}
}