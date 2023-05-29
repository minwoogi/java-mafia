package handling.packet.header;

public class ReceiveHeader {
	public static final int LOGIN = 0; // �α��� ��ư
    public static final int ID_OVERLAP = 1; // ���̵� �ߺ� Ȯ��
    public static final int SEND_EMAIL = 2; // �̸��� ����
    public static final int CERTIFICATION_EMAIL = 3; // �̸��� ����
    public static final int REGISTER = 4; // ȸ������ ��ư
    public static final int LOBBY_UPDATE = 5; // �κ� �� ���� ������Ʈ
    public static final int ENTER_ROOM = 6; // ���� ����
    public static final int MAKE_ROOM = 7; // �� �����
    public static final int ROOM_UPDATE = 8; // �� ������Ʈ
    public static final int LOBBY_USERS = 9; // �ʴ��ϱ�
    public static final int EXIT_ROOM = 10; // ���� ������
    public static final int INVITE_USER = 11; // ���� �ʴ�
    public static final int CHAT = 12; // ä��
    public static final int VOTE = 13; // ��ǥ
    public static final int NICK_OVERAP = 17; // �г��� �ߺ� Ȯ��
    public static final int USER_INFORMATION = 20; // ���� ���� ��û
    public static final int CITIZEN_VOTE = 21; // ��ǥ�ð� ��ǥ ��� (�г���)
    public static final int MAFIA_ABILITY = 22; // ���Ǿư� ������ ��� (�г���)
    public static final int DOCTOR_ABILITY = 23; // �ǻ簡 ������ ��� (�г���)
    public static final int POLICE_ABILITY = 24; // ������ ������ ��� (�г���)
    public static final int END_TIMER = 25; // Ÿ�̸� ���� ��
    public static final int READY = 26; // ���� �غ� ��ư Ŭ�� ��
    public static final int START_GAME = 27; // ���� ���� ��
    public static final int SHOW_MESSAGE = 28; // YES OR NO OR CANCEL �� �ޱ�\
    public static final int LOGOUT = 29;
    
}
