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
	
	public static final int CLASSIFY_JOB[][] = { // ���Ǿ�, �ù�, �ǻ�, ���� ��
			{1, 2, 1, 1}, // 5�� �� ��
			{2, 2, 1, 1}, // 6�� �� ��
			{2, 2, 1, 2}, // 7�� �� ��
			{2, 3, 1, 2}, // 8�� �� ��
			{2, 4, 1, 2}, // 9�� �� ��
			{3, 4, 1, 2}, // 10�� �� ��
	};
	
	public static final int DAY_TIME = 60 * 1000; // ��� �ð� 
	public static final int VOTE_TIME = 30 * 1000; // ��ǥ �ð�
	public static final int OBJECTION_TIME = 30 * 1000; // ������ �ݷ� �ð�
	public static final int AGREE_VOTE_TIME = 30 * 1000; // ���� ��ǥ �ð�
	public static final int NIGHT_TIME = 60 * 1000; // �ɷ� ��� �ð�(��)
	
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
