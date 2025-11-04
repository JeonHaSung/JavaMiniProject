package system;

import data.DataManager;
import exception.BankException;
import model.User;
import model.Account;
import model.Transaction;
import util.RandomAccountUtil;

import java.util.HashMap;
import java.util.Scanner;

import event.BankEvent;
import event.RockPaperScissorsEvent;
import event.UpDownEvent;

public class BankSystem implements BankService {

    private HashMap<String, User> users;
    private User currentUser; //로그인된 해당 객체
    private Scanner sc = new Scanner(System.in);
    
  //------------------------------------------------------------------------------------------------------ 
    //데이터 불러오기
    public BankSystem() {
        users = DataManager.loadData();
    }
  //------------------------------------------------------------------------------------------------------  
    //시작
    public void start() {
        while (true) {
            System.out.println("\n===== 🏦 Mini Bank =====");
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("0. 종료");
            System.out.print("선택> ");

            int sel = -1; 
            String input = sc.nextLine(); 

            try {
                sel = Integer.parseInt(input); 
            } catch (NumberFormatException e) {
              
            }

            switch (sel) {
                case 1 -> signUp();
                case 2 -> login();
                case 0 -> exitProgram();
                default -> System.out.println("⚠️ 잘못 입력했습니다.");
            }
        }
    }
  //------------------------------------------------------------------------------------------------------  
    //메인 메뉴 (로그인 후 입장)
 // 로그인 후 메뉴
    private void loginMenu() {

    	// 로그인 후 첫 계좌 자동 개설
    	if (currentUser.getAccounts().isEmpty()) {
    		try {
    			System.out.println("======================");
    			  Thread.sleep(500);
        	    System.out.println("엇! 첫 고객님이시군요 계좌를 만들어볼까요? \n");
        	    Thread.sleep(1500);
        	    System.out.println("계좌를 개설하면 축하금으로 10만원을 드리고 있어요 \n");
        	    Thread.sleep(1500);
        	    System.out.println("간단한 절차만 거치면 됩니다..\n");
        	    Thread.sleep(1500);
        	    System.out.println("이름인증");
    		}catch (InterruptedException e) {
    		    e.printStackTrace();
    		}

    		while (true) { // 이름 입력만 반복
    		    System.out.print("본인 이름 입력: ");
    		    String accessName = sc.nextLine();

    		    if (currentUser.getName().equals(accessName)) {
    		        System.out.println("\n인증 성공!");
    		        
    		        try { Thread.sleep(1500); } catch (InterruptedException e) {}
    		        System.out.println("계좌 발급 중...\n");
    		        
    		        try { Thread.sleep(1500); } catch (InterruptedException e) {}
    		        createAccount(true);
    		        
    		        try { Thread.sleep(1500); } catch (InterruptedException e) {}
    		        System.out.println("💸 계좌 개설 이벤트 +100,000원 지급!");
    		        System.out.println("______________________________");
    		        
    		        break; // 정상 탈출
    		    } else {
    		        System.out.println("인증 실패했어요 다시 입력해주세요.\n");
    		    }
    		}

    	}



   
    	// 메인 메뉴 (간단 버전)
    	while (currentUser != null) {

    	    System.out.println("\n===== 👤 " + currentUser.getName() + "님 =====");
    	    System.out.print("1. 계좌 만들기  ");
    	    System.out.print("0. 로그아웃  ");
    	    System.out.println("5. 🎁 이벤트(가위바위보)");  
 
    	    showAccounts(); // 계좌는 항상 표시
    	    System.out.print("2. 송금  ");
    	    System.out.print("3. 거래내역 조회  ");
    	    System.out.println("4. 계좌 삭제  ");

  
    	    System.out.print("선택> ");

    	    int sel = -1; 
            String input = sc.nextLine(); 

            try {
                sel = Integer.parseInt(input); 
            } catch (NumberFormatException e) {
               
            }

    	    switch (sel) {
    	        case 1 -> createAccount();
    	        case 2 -> transfer();
    	        case 3 -> showTransactions();
    	        case 4 -> deleteAccount();
    	        case 5 -> eventMenu();
    	        case 0 -> logout();
    	        default -> System.out.println("⚠️ 잘못 입력했습니다.");
    	    }
    	}

    }

  //------------------------------------------------------------------------------------------------------  
     //회원가입
    @Override
    public void signUp() {
    	System.out.println("MINIBANK 회원가입");
    	try {
    	 System.out.print("이름: ");
         String name = sc.nextLine();
        System.out.print("아이디: ");
        String id = sc.nextLine();

        if (users.containsKey(id)) { //hashMap 메서드 containsKey(id) 키 찾는 메서드 (회원 id(<String,) 말한다.
        	throw new BankException("이미 존재하는 아이디입니다.");
        }
        

        System.out.print("비밀번호: ");
        String pw = sc.nextLine();
       

        System.out.println("0: 가입 | 1: 취소");
        int choice = sc.nextInt();
        sc.nextLine(); // 🔥 버퍼 정리 (nextInt → nextLine 문제 해결)

        if (choice == 1) {
        	throw new BankException("회원가입이 취소되었습니다.");
        }
        
        users.put(id, new User(id, pw, name)); //추가하기 new User -> 클래스 User.java
        DataManager.saveData(users); //데이터매니저로 이동
        
        System.out.println("축하드려요 회원가입 완료!");     
    }catch (BankException e) {
     System.out.println(e.getMessage());
    }
}
  //------------------------------------------------------------------------------------------------------  
  //로그인 
    @Override
    public void login() {
        System.out.println("\n");
        System.out.println("MiNiBank 로그인\n");

        try {
            System.out.print("아이디: ");
            String id = sc.nextLine();

            System.out.print("비밀번호: ");
            String pw = sc.nextLine(); // 

            //해시맵 메서드 .containsKey(id) id존재확인 있을시/ .get(id) 해당 객체 가져와서 
            //.equals(pw) 해당객체의 비밀번호와 현재 비밀번호 같을시 참 
            if (!users.containsKey(id)) {
                throw new BankException("❌ 존재하지 않는 아이디입니다.");
            }

            if (!users.get(id).getPassword().equals(pw)) {
                throw new BankException("❌ 비밀번호가 일치하지 않습니다.");
            }

            //currentUser = 에 해당하는 객체를 담는다. 해당 객체의 User.getName() (예: 홍길동)
            currentUser = users.get(id);
            System.out.println("✅ 로그인 성공! " + currentUser.getName() + "님 환영합니다.");

            //로그인 메뉴 실행 
            loginMenu();

        } catch (BankException e) {
            System.out.println(e.getMessage());
            System.out.println("↩ 다시 로그인 해주세요.\n");
        }
    }

    
  //------------------------------------------------------------------------------------------------------  
    @Override
    public void logout() {
        currentUser = null;
        System.out.println("👋 로그아웃 되었습니다.");
    }
    //------------------------------------------------------------------------------------------------------  
    //계좌 생성 ( Account acc = new Account(accNum)) 
    
    @Override
    public void createAccount() {
        createAccount(false);
    }
    public void createAccount(boolean bonus) {
        if (currentUser == null) return;

        String accNum = RandomAccountUtil.createAccountNumber();
        Account acc = new Account(accNum);
        currentUser.addAccount(acc);
        
       if(!bonus) {
    	   try {
      		 Thread.sleep(1500);
  	            System.out.println("계좌 발급 중...\n");
  	            Thread.sleep(1500);
  	             System.out.println("완료했어요.");
  	            System.out.println("______________________________");
  	            Thread.sleep(1500);
      	}catch (InterruptedException e) {
  		    e.printStackTrace();
  		}
    	  
       }

        if (bonus) { 
            acc.deposit(100000);

            // 계좌 자체에 거래내역 저장
            acc.addTransaction(new Transaction(
                    "MINIBANK", 
                    "SYSTEM", 
                    currentUser.getName(), 
                    accNum, 
                    100000, 
                    "입금",
                    acc.getBalance()
            ));

  
        }

        DataManager.saveData(users);
        System.out.println("📌 계좌 생성 완료: " + accNum);
    }


//--------------------------------------------------------------------------------------------
  //--------------------------------------------------------------------------------------------
    //송금로직
    @Override //예외처리
    public void transfer() {
    	 if (currentUser == null) return;

    	    Account fromAcc = null;
    	    try {
    	        fromAcc = selectAccount("송금할 계좌 선택");
    	    } catch (BankException e) {
    	        System.out.println(e.getMessage());
    	        return; // 다시 메뉴로
    	    }

        System.out.print("받는 계좌번호 입력 (0 = 취소): ");
        String toAccNum1 = sc.nextLine();
        String toAccNum = toAccNum1.trim();

        if (toAccNum.equals("0")) {  
            System.out.println("(↩ 송금 취소하고 메인으로 돌아갑니다)\n");
            return;
        }

        // 받는 유저 검색
        User toUser = null;
        Account toAcc = null;
        //---
        for (User u : users.values()) { //users(HashMap)의 value(User 객체들)를 순회하면서 u에 하나씩 담아 확인
            if (u.getAccount(toAccNum) != null) { 
                //User.java -> getAccount() 이동하여 입력받은 toAccNum 계좌번호가 존재하면 해당하는 Account 객체 반환(null 아니면 존재)
                toUser = u; // toUser에 그 User 객체 담기
                toAcc = u.getAccount(toAccNum); //toAcc에 해당 계좌번호의 Account 객체 담기
                break;
            }
        }
        //---
        if (toAcc == null) { //해당 계좌가 모든 user들 중 어디에도 없으면
            System.out.println("❌ 상대 계좌가 존재하지 않습니다.");
            return;
        }

        System.out.print("송금 금액 입력 (0 = 취소): ");
        int money = sc.nextInt(); //송금할 금액 입력
        sc.nextLine();

        if (money == 0) {
            System.out.println("(↩ 송금 취소)\n");
            return;
        }

        if (fromAcc.withdraw(money)) { 
            //Account.java 내계좌에서 출금(송금) 메서드 호출(money) -> 현재잔고 - 송금잔고 -> true반환으로 if문 실행

            toAcc.deposit(money); 
            //Account.java 받는계좌 객체에 입금

            //  A 계좌의 거래 기록 저장 (출금)
            fromAcc.addTransaction(new Transaction(  
                    //Account.java -> addTransaction (new Transaction()-> 해당 객체가 ) -> addTransaction로 전달.
                    currentUser.getName(),                // Transaction() 보내는 사용자 이름
                    fromAcc.getAccountNumber(),          //보내는 계좌번호
                    toUser.getName(),                    // 받는 사용자 이름
                    toAccNum,                            //받는 계좌번호
                    money,                               //거래 금액
                    "출금",                               //거래타입
                    fromAcc.getBalance()                 //잔액
            ));

            // B 계좌의 거래 기록 저장 (입금)
            toAcc.addTransaction(new Transaction(
                    currentUser.getName(),              // Transaction() 보내는 사용자 이름
                    fromAcc.getAccountNumber(),        //보내는 계좌번호
                    toUser.getName(),                  // 받는 사용자 이름
                    toAccNum,                          //받는 계좌번호
                    money,                             //거래 금액
                    "입금",                             //거래타입
                    toAcc.getBalance()                 //받는 계좌 잔액
            ));

            DataManager.saveData(users); //업데이트

            // 출력은 아래에서 처리되도록 바로 메인으로 안가는 설정

            System.out.println("\n✅ 송금 완료!");
            System.out.println("보낸 계좌: " + fromAcc.getAccountNumber());
            System.out.println("받는 계좌: " + toAccNum);
            System.out.println("보낸 금액: " + money + "원");
            System.out.println("남은 잔액: " + fromAcc.getBalance() + "원");
        } else {
            System.out.println("❌ 잔액 부족 또는 잘못된 금액");
        }

        // 뒤로가기 대기 (화면 유지)
        System.out.println("\n0. 뒤로가기 (메뉴)");
        System.out.print("선택> ");
        String cmd = sc.nextLine();
        if (cmd.equals("0")) {
            System.out.println("(↩ 메인 메뉴로 돌아갑니다)\n");
            return;
        }

        System.out.println("⚠️ 잘못된 입력\n");
    }

  //--------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
    //보유 계좌 목록
    @Override
    public void showAccounts() {
        if (currentUser == null) return;

        System.out.println("\n📂 보유 계좌 목록");

        if (currentUser.getAccounts().isEmpty()) {
            System.out.println("❌ 계좌 없음");
            return;
        }

        for (String accNum : currentUser.getAccounts().keySet()) { //hash맵의 Key 값들만 가져옴
            Account acc = currentUser.getAccounts().get(accNum); //key값에 해당하는 객체를 가져와 acc에 담는다.
            System.out.println(acc.getBalance() + "원" + "\n" + acc.getAccountName() + "  " + accNum );
            System.out.println("__________________");
        }
    }
  //--------------------------------------------------------------------------------------------------
    //거래내역 조회할 계좌 선택
    @Override
    public void showTransactions() {
    	Account acc = null;
    	try {
    	    acc = selectAccount("거래내역 조회할 계좌 선택");
    	} catch (BankException e) {
    	    System.out.println(e.getMessage());
    	    return;
    	}

        String accNum = acc.getAccountNumber();

        System.out.println("\n====== 📜 거래내역 | 계좌: " + accNum + " ======");
        System.out.println("잔액: " + acc.getBalance() + "원");
        System.out.println("-----------------------------------");

        var list = acc.getTransactions();

        if (list.isEmpty()) {
            System.out.println("❌ 거래내역 없음");
        } else {
            for (Transaction t : list) {
                System.out.println(t);
                System.out.println("-----------------------------------");
            }
        }

        // 메인 메뉴 유지한 상태에서 선택만 기다림
        System.out.println("\n0. 뒤로가기 (메뉴로)");
        System.out.print("선택> ");
        String cmd = sc.nextLine();

        // 아무것도 안 하고 그냥 돌아가기
        if (cmd.equals("0")) {
            System.out.println("(↩ 메인 메뉴로 돌아갑니다)\n");
            return;
        }

        System.out.println("⚠️ 잘못된 입력\n");
    }

  //--------------------------------------------------------------------------------------------------
    @Override
    public void exitProgram() {
        DataManager.saveData(users);
        System.out.println("💾 저장됨. 프로그램 종료");
        System.exit(0);
    }
    //--------------------------------------------------------------------------------------------------   
 // 계좌 선택 UI (번호 선택)
    private Account selectAccount(String message) throws BankException {
        if (currentUser.getAccounts().isEmpty()) {
        	 throw new BankException("❌ 계좌가 없습니다. 먼저 계좌를 생성해주세요.");
        }

        System.out.println("\n📂 " + message);
        int index = 1;
        String[] keys = new String[currentUser.getAccounts().size()]; //User 계좌 해시맵 크기 가져오기 

        for (String accNum : currentUser.getAccounts().keySet()) { //User 계좌 해시맵에서 Key만 가지고 온다 계좌번호들
            Account acc = currentUser.getAccounts().get(accNum); // 계좌번호 전부 담기 
            System.out.println(index + ". " + acc.getBalance() + "원  | " + acc.getAccountName() + " | " + accNum);
            keys[index - 1] = accNum; //인덱스에 계좌 담기 -1인이유 0부터 배열을 가져오기 위함 프린트에서는 1번 항목 선택을 위해
            index++;
        }

        System.out.print("번호 선택 > ");
        int sel = sc.nextInt(); //선택
        sc.nextLine();

        if (sel < 1 || sel > keys.length) {
        	 throw new BankException("⚠️ 잘못된 선택입니다.");
        
        }
        return currentUser.getAccount(keys[sel - 1]); //선택값이 1번 계좌라면 -1 0번의 해당하는 계좌번호를 User.java getAccount 메서드로 전달하여 해당 객체를 받아옴
    }
    //--------------------------------------------------------------------------------------------------
    //계좌 삭제 로직
    @Override
    public void deleteAccount() {
        if (currentUser == null) return;

        Account acc = null;
        try {
            acc = selectAccount("삭제할 계좌 선택");
        } catch (BankException e) {
            System.out.println(e.getMessage());
            return; // 메뉴로 돌아가기
        }

        // 잔액 체크
        if (acc.getBalance() != 0) {
            System.out.println("❌ 잔액이 0원이어야 계좌 삭제가 가능합니다.");
            System.out.println("현재 잔액: " + acc.getBalance() + "원");
            return;
        }

        System.out.println("\n⚠️ 정말 계좌를 삭제하시겠습니까?");
        System.out.print("0: 예   |   1: 아니요 > ");

        try {
            Thread.sleep(300);
            int choice = sc.nextInt();
            sc.nextLine(); // 버퍼 정리

            if (choice == 0) {
                currentUser.getAccounts().remove(acc.getAccountNumber());
                DataManager.saveData(users);

                Thread.sleep(300);
                System.out.println("✅ 계좌가 정상적으로 삭제되었습니다.");
                Thread.sleep(300);
                System.out.println("↩ 메인 메뉴로 돌아갑니다.\n");
            } 
            else if (choice == 1) {
                Thread.sleep(300);
                System.out.println("↩ 삭제 취소. 메인 메뉴로 돌아갑니다.\n");
            } 
            else {
                throw new BankException("⚠️ 잘못된 입력입니다. (0 또는 1만 입력하세요)");
            }

        } catch (InterruptedException e) {
            System.out.println("⚠️ 시스템 오류 발생. 다시 시도해주세요.");
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }
    }

  //-----------------------------------------------------------------------------
    //이벤트
 // 이벤트 메뉴
    private void eventMenu() { // 이벤트 메뉴
        while (true) {
            System.out.println("\n===== 🎮 이벤트 메뉴 =====");
            System.out.println("1. 가위바위보");
            System.out.println("2. UP & DOWN");
            System.out.println("0. 뒤로가기");
            System.out.print("선택> ");

            int sel = sc.nextInt();
            sc.nextLine();

            if (sel == 0) {
                System.out.println("메인 메뉴로 이동");
                return;
            }

            BankEvent event = null;

            switch (sel) {
                case 1 -> event = new RockPaperScissorsEvent();
                case 2 -> event = new UpDownEvent();
                default -> {
                    System.out.println("❌ 잘못 입력");
                    continue;
                }
            }

            int reward = event.play();

            if (reward > 0) {
                String accNum = currentUser.getAccounts().keySet().iterator().next();
                Account acc = currentUser.getAccount(accNum);
                acc.deposit(reward);

                acc.addTransaction(new Transaction(
                        "MINIBANK",
                        "EVENT",
                        currentUser.getName(),
                        accNum,
                        reward,
                        "입금",
                        acc.getBalance()
                ));

                DataManager.saveData(users);

                System.out.println("🎉 이벤트 보상 +" + reward + "원 지급 완료! (" + accNum + ")");
            }

            System.out.println("\n👉 Enter 입력 시 이벤트 메뉴로 이동");
            sc.nextLine();
        }
    }



}

