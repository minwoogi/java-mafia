package information;

public class ServerConstants {
	public static final int[] need_exp = {
			15, 
			34, 
			57, 
			92, 
			135,
			
			372,
			560, 
			840, 
			1242, 
			1242, 
			
			1242, 
			1242, 
			1242, 
			1242, 
			1242, 
			
			1490, 
			1788,
			2145, 
			2574, 
			3088,
			
			3705, 
			4446, 
			5335, 
			6402, 
			7682, 
			
			9218, 
			11061, 
			13273, 
			15927, 
			19112
	};
	public static final int MAX_LEVEL = 30;
	public static final int MAFIA = 0;
	public static final int CITIZEN = 1;
	public static final int DOCTOR = 3;
	public static final int POLICE = 4;
	
	public static final int CLASSIFY_JOB[][] = { // 마피아, 시민, 의사, 경찰 순
			{1, 2, 1, 1}, // 5명 일 때
			{2, 2, 1, 1}, // 6명 일 때
			{2, 2, 1, 2}, // 7명 일 때
			{2, 3, 1, 2}, // 8명 일 때
			{2, 4, 1, 2}, // 9명 일 때
			{3, 4, 1, 2}, // 10명 일 때
	};
	
	public static final int DAY_TIME = 60 * 1000; // 토론 시간 
	public static final int VOTE_TIME = 30 * 1000; // 투표 시간
	public static final int OBJECTION_TIME = 30 * 1000; // 최후의 반론 시간
	public static final int AGREE_VOTE_TIME = 30 * 1000; // 찬반 투표 시간
	public static final int NIGHT_TIME = 60 * 1000; // 능력 사용 시간(밤)
	
	public static boolean isMafia(int job) {
		return job == MAFIA;
	}
	
	public static boolean isCitizen(int job) {
		return job == CITIZEN;
	}
	
	public static boolean isDoctor(int job) {
		return job == DOCTOR;
	}
	
	public static boolean isPolice(int job) {
		return job == POLICE;
	}
	
	public static int needExp(int level) {
		return need_exp[level - 1];
	}
}
