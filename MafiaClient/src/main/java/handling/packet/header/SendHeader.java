package handling.packet.header;

public class SendHeader {

	public static final int LOGIN = 0; // 로그인 버튼
	public static final int OVERLAP = 1; // 중복 확인
	public static final int SEND_EMAIL = 2; // 이메일 전송
	public static final int CERTIFICATION_EMAIL = 3; // 이메일 인증
	public static final int REGISTER = 4; // 회원가입 버튼
	public static final int LOBBY_UPDATE = 5; // 로비 내 대기실 업데이트
	public static final int ENTER_ROOM = 6; // 대기실 입장
	public static final int MAKE_ROOM = 7; // 방 만들기
	public static final int ROOM_UPDATE = 8; // 방 업데이트
	public static final int LOBBY_USERS = 9; // 초대하기
	public static final int EXIT_ROOM = 10; // 대기실 나가기
	public static final int INVITE_USER = 11; // 유저 초대
	public static final int CHAT = 12; // 채팅
	public static final int VOTE = 13; // 투표
	public static final int NICK_OVERLAP = 17; // 닉네임 중복
	public static final int USER_INFORMATION = 20; // 유저 정보 업데이트
	public static final int READY = 26; // 대기실에서 레디
	public static final int LOGOUT = 29; // 로그아웃
	public static final int START_GAME = 27; // 게임시작
}