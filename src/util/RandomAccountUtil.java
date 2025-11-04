package util;

import java.util.Random;

public class RandomAccountUtil {

    private static Random rand = new Random();

    // 8자리 랜덤 계좌번호 생성
    public static String createAccountNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            sb.append(rand.nextInt(10)); // 0~9
        }
        return sb.toString();
    }
}
