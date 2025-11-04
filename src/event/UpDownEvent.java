package event;

import java.util.Random;
import java.util.Scanner;

public class UpDownEvent extends BankEvent {

    public UpDownEvent() {
        super("UP & DOWN 이벤트");
    }
//-------------------------------------------------------------
    @Override
    public String getEventDescription() {
        return "1 ~ 20 사이의 숫자를 맞추면 보상을 드립니다!\n"
             + "정답을 맞추면 기본 보상 지급!";
    }
  //-------------------------------------------------------------
    @Override
    public int getBaseReward() {
        return 300; // 정답 시 기본 보상
    }
  //-------------------------------------------------------------
    @Override
    public int play() {
        startMessage(); // ✅ 공통 안내문

        Scanner sc = new Scanner(System.in);
        Random rnd = new Random();

        int answer = rnd.nextInt(20) + 1; // 1~20
        int attempts = 5; // 기회 5번

        System.out.println("🎯 숫자 맞추기 게임 시작!");
        System.out.println("👉 1 ~ 20 사이 숫자를 맞춰보세요!");
        System.out.println("⏳ 기회: " + attempts + "번");

        while (attempts-- > 0) {

            System.out.print("숫자 입력: ");
            int guess;

            try {
                guess = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("❌ 숫자만 입력하세요!");
                attempts++; 
                continue;
            }

            if (guess == answer) {
                System.out.println("🎉 정답입니다!");
                System.out.println("💰 +" + getBaseReward() + "원 지급!");
                return getBaseReward();
            }
            else if (guess < answer) {
                System.out.println("UP ↑");
            } 
            else {
                System.out.println("DOWN ↓");
            }

            System.out.println("남은 기회: " + attempts + "번\n");
        }

        System.out.println("😢 실패! 정답은 " + answer + " 였습니다.");
        return 0;
    }
}
