package information;

public class ClientInf {

	private static int userId;
	private static String nickName;
	private static int level;
	private static int exp;
	private static int tier;
	private static boolean readyState;
	
	public static boolean isReadyState() {
		return readyState;
	}

	public static void setReadyState(boolean readyState) {
		ClientInf.readyState = readyState;
	}

	public static int getUserId() {
		return userId;
	}

	public static void setUserId(int userId) {
		ClientInf.userId = userId;
	}

	public static String getNickName() {
		return nickName;
	}
	
	public static void setNickName(String nickName) {
		ClientInf.nickName = nickName;
	}
	
	public static int getLevel() {
		return level;
	}
	
	public static void setLevel(int level) {
		ClientInf.level = level;
	}
	
	public static int getExp() {
		return exp;
	}
	
	public static void setExp(int exp) {
		ClientInf.exp = exp;
	}
	
	public static int getTier() {
		return tier;
	}
	
	public static void setTier(int tier) {
		ClientInf.tier = tier;
	}
	

}
