package data;

import model.User;
import java.io.*;
import java.util.HashMap;

public class DataManager {

    private static final String FILE_NAME = "bankdata.ser";

    // 데이터 저장 (User 정보 전체 저장)
    public static void saveData(HashMap<String, User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
            System.out.println("📁 데이터 저장 완료");
        } catch (IOException e) {
            System.out.println("❌ 데이터 저장 중 오류 발생");
            e.printStackTrace();
        }
    }

    // 데이터 로드 (프로그램 실행 시 불러오기)
    public static HashMap<String, User> loadData() {   //HashMap<String, User> 의 User는 import model.User;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) { //FileInputStream 파일 바이트 읽기  ObjectInputStream 자바객체로 해석 하여 읽을 준비
            System.out.println("✅ 데이터 로드 완료");
            return (HashMap<String, User>) ois.readObject(); //(캐스팅 hashMap으로 ) readDbject는 오브젝트 타입이기때문 readDbject로 읽기
        } catch (FileNotFoundException e) {
            System.out.println("⚠️ 저장된 데이터 없음 — 새로 시작합니다.");
        } catch (Exception e) {
            System.out.println("❌ 데이터 로드 실패");
            e.printStackTrace();
        }

        return new HashMap<>(); // 데이터 없으면 빈 HashMap 반환
    }
}
