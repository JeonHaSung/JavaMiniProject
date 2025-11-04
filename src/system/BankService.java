package system;

public interface BankService {

    void signUp();          // 회원가입
    void login();           // 로그인
    void logout();          // 로그아웃

    void createAccount();   // 계좌 생성

    void transfer();        // 송금

  void showAccounts();    // 계좌 목록 보기
    void showTransactions(); // 거래내역 보기

    void exitProgram();     // 종료 & 데이터 저장
    
    void deleteAccount();
    //계좌 삭제 기능
   
}
