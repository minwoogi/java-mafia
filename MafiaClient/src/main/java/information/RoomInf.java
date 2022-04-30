package information;

import javax.swing.JPanel;

public class RoomInf { // * 대기중,개임중 방 객체 정보 * //

	private int roomId;
	private int headCount;
	private int currentStaff;
	private boolean roomState;
	private String roomName;

	public RoomInf(int roomId, int currentStaff, int headCount, String roomName, boolean roomState) {
		this.roomId = roomId;
		this.currentStaff = currentStaff;
		this.headCount = headCount;
		this.roomName = roomName;
		this.roomState = roomState;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getHeadCount() {
		return headCount;
	}

	public void setHeadCount(int headCount) {
		this.headCount = headCount;
	}

	public int getCurrentStaff() {
		return currentStaff;
	}

	public void setCurrentStaff(int currentStaff) {
		this.currentStaff = currentStaff;
	}

	public boolean isRoomState() {
		return roomState;
	}

	public void setRoomState(boolean roomState) {
		this.roomState = roomState;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

}
