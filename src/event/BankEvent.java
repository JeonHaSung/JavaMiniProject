package event;

public abstract class BankEvent {

    protected String eventName;

    public BankEvent(String eventName) {
        this.eventName = eventName;
    }

    // 모든 이벤트가 반드시 구현해야 하는 게임 실행 //abstract이 있기때문에 
    public abstract int play();

    // 이벤트 설명
    public abstract String getEventDescription();

    // 기본 보상 (이벤트 별 다르게 줄 수 있음)
    public abstract int getBaseReward();

    // 공통 시작 메시지 
    public void startMessage() {
        System.out.println("\n===== 🎮 " + eventName + " =====");
        System.out.println(getEventDescription());
        System.out.println("------------------------------");
    }

    public String getEventName() {
        return eventName;
    }
}
