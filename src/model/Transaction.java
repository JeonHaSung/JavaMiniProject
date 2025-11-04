package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Transaction 클래스
 // 한 번의 금융 거래(입금/출금/송금) 거래내역을 기록하는 객체
 //직렬화하여 파일로 저장 가능

public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    // 거래 발생 시간 (yyyy-MM-dd HH:mm:ss)
    private String dateTime;

    // 송금한 사용자 이름
    private String fromUser;

    // 송금한 계좌 번호
    private String fromAccount;

    // 송금받은 사용자 이름
    private String toUser;

    // 송금받은 계좌 번호
    private String toAccount;

    // 거래 금액
    private int amount;

    // 거래 타입 (입금/출금/송금)
    private String type;

    // 해당 거래가 끝난 후 계좌 잔액
    private int balanceAfter;

    /**
     * 생성자: 거래 정보를 초기화
     * @param fromUser      보내는 사용자 이름
     * @param fromAccount   보내는 계좌번호
     * @param toUser        받는 사용자 이름
     * @param toAccount     받는 계좌번호
     * @param amount        거래 금액
     * @param type          거래 타입 ("입금", "출금", "송금")
     * @param balanceAfter  거래 후 잔액
     */
    public Transaction(String fromUser, String fromAccount,
                       String toUser, String toAccount,
                       int amount, String type, int balanceAfter) {

        this.dateTime = getNowTime(); // 거래 시간 
        this.fromUser = fromUser;
        this.fromAccount = fromAccount;
        this.toUser = toUser;
        this.toAccount = toAccount;
        this.amount = amount;
        this.type = type;
        this.balanceAfter = balanceAfter;
    }

    
     //현재 시간을 "yyyy-MM-dd HH:mm:ss" 형태로 반환
     
    private String getNowTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 숫자 금액을 천 단위 콤마 형식으로 변환
     * @param money 정수 금액
     * @return 1,234 형식 문자열
     */
    private String formatMoney(int money) {
        return String.format("%,d", money);
    }

    /**
     * 거래 내용을 사람이 읽기 쉬운 문자열 형태로 반환
     */
    @Override
    public String toString() {
        String sign = type.equals("입금") ? "+" : "-";

        return "[" + dateTime + "] " + type + "\n"
               + fromUser + " → " + toUser + " | "
               + sign + formatMoney(amount) + "원\n"
               + "현재 잔액: " + formatMoney(balanceAfter) + "원";
    }

    // Getter 메서드 (외부에서 거래 정보 읽을 때 사용)
    public String getDateTime() { return dateTime; }
    public String getFromUser() { return fromUser; }
    public String getFromAccount() { return fromAccount; }
    public String getToUser() { return toUser; }
    public String getToAccount() { return toAccount; }
    public int getAmount() { return amount; }
    public String getType() { return type; }
    public int getBalanceAfter() { return balanceAfter; }
}
