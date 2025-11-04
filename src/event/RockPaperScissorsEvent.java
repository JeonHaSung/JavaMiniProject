package event;

import java.util.Random;
import java.util.Scanner;

public class RockPaperScissorsEvent extends BankEvent {

    public RockPaperScissorsEvent() {
        super("가위바위보 이벤트");
    }
  //-------------------------------------------------------------
    @Override
    public String getEventDescription() {
        return "가위, 바위, 보 중 하나를 입력해서 컴퓨터를 이기세요!\n"
             + "비기면 기본 보상\n"
             + "이기면 기본 보상 + 추가 보상";
    }
  //-------------------------------------------------------------
    @Override
    public int getBaseReward() {
        return 50; // 비겼을 때 기본 보상
    }
  //-------------------------------------------------------------
    @Override
    public int play() {
        startMessage(); // 공통 안내문 자동 출력

        Scanner sc = new Scanner(System.in);
        String[] hands = {"가위", "바위", "보"};
        Random rnd = new Random();

        System.out.print("✊ ✌ ✋ 입력 (가위/바위/보): ");
        String user = sc.nextLine().trim();

        // 입력 검증
        if (!user.equals("가위") && !user.equals("바위") && !user.equals("보")) {
            System.out.println("⚠️ 잘못 입력 - 보상 없음");
            return 0;
        }

        String com = hands[rnd.nextInt(3)];
        System.out.println("🤖 MiNiBank: " + com);

        int base = getBaseReward();
        int bonus = 300; // 승리 시 추가 보상

        // 결과 판정
        if (user.equals(com)) {
            System.out.println("🤝 비겼습니다! +" + base + "원 지급!");
            return base;
        } 
        else if (
            (user.equals("가위") && com.equals("보")) ||
            (user.equals("바위") && com.equals("가위")) ||
            (user.equals("보") && com.equals("바위"))
        ) {
            System.out.println("🏆 승리! +" + (base + bonus) + "원 지급!");
            return base + bonus;
        } 
        else {
            System.out.println("😢 패배! 보상 없음");
            return 0;
        }
    }
}
