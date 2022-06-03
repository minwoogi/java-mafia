package information;

public class UserInf {
	private int userId;
	private String userNick;
	private boolean isReady;
	private int level;
	private int tier;
	private boolean isLeader;
	public UserInf(int userId, String userNick, boolean isReady, int level, int tier) {
		this.userId = userId;
		this.userNick = userNick;
		this.isReady = isReady;
		this.level = level;
		this.tier = tier;
	}
	
	public boolean isLeader() {
		return isLeader;
	}

	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

}
