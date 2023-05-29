package handling.packet.header;

public class SendHeader {
	public static final int LOGIN = 0; // �α��� ��ư
    public static final int ID_OVERLAP = 1; // ���̵� �ߺ� Ȯ��
    public static final int SEND_EMAIL = 2; // �̸��� ���� Ȯ��
    public static final int CERTIFICATION_EMAIL = 3; // �̸��� ����
    public static final int REGISTER = 4; // ȸ������ ��ư
    public static final int LOBBY_UPDATE = 5; // �κ� ������Ʈ
    public static final int ENTER_ROOM = 6; // ���� ����
    public static final int MAKE_ROOM = 7; // �� �����
    public static final int ROOM_UPDATE = 8; // �� ������Ʈ
    public static final int LOBBY_USERS = 9;// �ʴ��ϱ�
    public static final int EXIT_ROOM = 10; // ���� ������
    public static final int INVITE_USER = 11; // ���� �ʴ�
    public static final int CHAT = 12; // ä��
    public static final int VOTE = 13; // ��ǥ ���
    public static final int TIMER = 14; // Ÿ�̸�
    public static final int DAY_AND_NIGHT = 15; // �� �� ����
    public static final int VOTE_RESULT = 16; // ��ǥ ���� ���
    public static final int NICK_OVERLAP = 17; // �г��� �ߺ� Ȯ��
    public static final int CHANGE_LOCATION = 18; // ��ġ ����(�κ� 0, �� 1, ������ 2)
    public static final int LOBBY_UPDATE_MAKE = 19; // �κ� ������Ʈ ( �� ���� �� )
    public static final int USER_INFORMATION = 20; // ���� ���� ����
    public static final int LOBBY_REMOVE_ROOM = 21; // �κ񿡼� �� �������ٰ� ����
    public static final int SHOW_MESSAGE = 22; // �޽��� ����
    public static final int DEAD_PLAYER = 23; // �÷��̾� ���, ���� ����
    public static final int START_GAME = 24; // ���� ���� �� ���� ���� ����(���� ��, id, number)
    public static final int WHAT_DATE = 25; // ���� �� �� �� ° ������
    public static final int LEADER = 26; // ���� ���� ����
}
