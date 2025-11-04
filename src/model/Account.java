package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accountNumber;  // 계좌번호
    private int balance;           // 잔고
    private String accountName = "MiNi우대통장"; // 계좌 이름

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    //----------------------------------------------------------------------
    //거래내역 저장
    private ArrayList<Transaction> transactions = new ArrayList<>(); 

    public void addTransaction(Transaction tx) {
        transactions.add(tx);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
    //----------------------------------------------------------------------

    // 입금
    public void deposit(int amount) {
        if (amount <= 0) {
            System.out.println("잘못된 금액입니다.");
            return;
        }
        this.balance += amount;
//        System.out.println("입금 완료 : +" + amount + "원");
    }
    //----------------------------------------------------------------------
    // 출금 (송금/인출)
    public boolean withdraw(int amount) {
        if (amount <= 0) { //출금한 값이 0이하면 잘못된 금액
            System.out.println("잘못된 금액입니다.");
            return false; //false를 반환 Bank시스템 클래스에서 false값으로 조건문 하기위함
        }

        if (this.balance < amount) {
            System.out.println(" 잔액이 부족합니다. 현재 잔액: " + this.balance + "원");
            return false;
        }

        this.balance -= amount; //출금성공 로직
        System.out.println("출금 완료 : -" + amount + "원");
        return true;
    }
    //----------------------------------------------------------------------
    // 잔고 조회
    public int getBalance() {
//        System.out.println("💰 현재 잔고 : " + this.balance + "원");
        return this.balance;
    }
    //----------------------------------------------------------------------
    // 계좌번호 조회
    public String getAccountNumber() {
        return accountNumber;
    }
    //----------------------------------------------------------------------
    // 계좌 이름 조회 (필요할 수도 있어서 유지)
    public String getAccountName() {
        return accountName;
    }
}
