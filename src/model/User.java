package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * User 클래스
 * 한 명의 사용자 정보 + 보유 계좌 + 거래내역 관리
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;                        // 아이디
    private String password;                  // 비밀번호
    private String name;                      // 사용자 이름

    private HashMap<String, Account> accounts = new HashMap<>(); // 계좌 목록 (계좌번호 → Account)

    /**
     * User 생성자
     * @param id 사용자 아이디
     * @param password 비밀번호
     * @param name 사용자 이름
     */
    public User(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    // Getter 메서드들
    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Account> getAccounts() {
        return accounts; //해시맵 바ㄴ환
    }

    /**
     * 계좌 추가 메서드
     * @param account 추가할 Account 객체
     */
    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account); //해시맵에 계좌넣기 키값 계좌번호 / 값 accpunt 객체
    }

    /**
     * 계좌 조회
     * @param accountNumber 조회할 계좌번호
     * @return Account 객체 또는 null
     */
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber); //key -> accountNumber 에 해당하는 벨류 -> 해당 객체를 가져온다.
    }

   

   
    
    
}
