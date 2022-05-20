package information;

public class UserInf {

	private static String nickName;
	private static int level;
	private static int exp;
	private static int tier;
	

	public static String getNickName() {
		return nickName;
	}
	
	public static void setNickName(String nickName) {
		UserInf.nickName = nickName;
	}
	
	public static int getLevel() {
		return level;
	}
	
	public static void setLevel(int level) {
		UserInf.level = level;
	}
	
	public static int getExp() {
		return exp;
	}
	
	public static void setExp(int exp) {
		UserInf.exp = exp;
	}
	
	public static int getTier() {
		return tier;
	}
	
	public static void setTier(int tier) {
		UserInf.tier = tier;
	}
	

}
