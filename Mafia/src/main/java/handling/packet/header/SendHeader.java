package handling.packet.header;

public class SendHeader {
	public static final int LOGIN = 0; // 로그인 버튼
    public static final int ID_OVERLAP = 1; // 아이디 중복 확인
    public static final int SEND_EMAIL = 2; // 이메일 전송 확인
    public static final int CERTIFICATION_EMAIL = 3; // 이메일 인증
    public static final int REGISTER = 4; // 회원가입 버튼
    public static final int LOBBY_UPDATE = 5; // 로비 업데이트
    public static final int ENTER_ROOM = 6; // 대기실 입장
    public static final int MAKE_ROOM = 7; // 방 만들기
    public static final int ROOM_UPDATE = 8; // 방 업데이트
    public static final int LOBBY_USERS = 9;// 초대하기
    public static final int EXIT_ROOM = 10; // 대기실 나가기
    public static final int INVITE_USER = 11; // 유저 초대
    public static final int CHAT = 12; // 채팅
    public static final int VOTE = 13; // 투표 결과
    public static final int TIMER = 14; // 타이머
    public static final int DAY_AND_NIGHT = 15; // 밤 낮 정보
    public static final int VOTE_RESULT = 16; // 투표 집계 결과
    public static final int NICK_OVERLAP = 17; // 닉네임 중복 확인
    public static final int CHANGE_LOCATION = 18; // 위치 변경(로비 0, 방 1, 게임장 2)
    public static final int LOBBY_UPDATE_MAKE = 19; // 로비 업데이트 ( 방 만들 때 )
    public static final int USER_INFORMATION = 20; // 유저 정보 전송
    public static final int LOBBY_REMOVE_ROOM = 21; // 로비에서 방 없어졌다고 전송
    public static final int SHOW_MESSAGE = 22; // 메시지 띄우기
    public static final int DEAD_PLAYER = 23; // 플레이어 사망, 사유 전송
    public static final int START_GAME = 24; // 게임 시작 시 유저 정보 전송(유저 수, id, number)
    public static final int WHAT_DATE = 25; // 게임 중 몇 번 째 날인지
    public static final int LEADER = 26; // 방장 변경 여부
}
