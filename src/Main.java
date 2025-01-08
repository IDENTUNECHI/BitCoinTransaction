import PasswordSystem.UniqueHexGenerator;
import Player.Player1_Sender;
import Player.Player2_recipient;
import Player.Player3_recipient;

import java.util.*;

/**
 * FRONT
 *
 * 1. Player1의 입력을 받는다.
 * 2. 입력 프롬프트는 다음과 같다.
 *     1. 어떤 도움이 필요한가요? (잔고 확인, 송금하기)
 *     2. 누구에게 송금할까요? (Player2와 Player3 둘 중 하나만 가능하다.)
 *     3. 몇 BTC 를 송금할까요?
 */
public class Main {

    // 플레이어 3명 생성
    private Player1_Sender player1;
    private Player2_recipient player2;
    private Player3_recipient player3;

    // 키 생성기 생성
    private UniqueHexGenerator keyGenerator1 = new UniqueHexGenerator("김이든");
    private UniqueHexGenerator keyGenerator2 = new UniqueHexGenerator("이성원");
    private UniqueHexGenerator keyGenerator3 = new UniqueHexGenerator("휴 잭맨");

    private Scanner input = new Scanner(System.in);
    /**
     * player 생성자
     */
    public Main() {

        try {
            keyGenerator1.initializeKeys();
            keyGenerator2.initializeKeys();
            keyGenerator3.initializeKeys();
        } catch (Exception e) {
            System.err.println("Error initializing keys: " + e.getMessage());
        }

        player1 = new Player1_Sender(keyGenerator1.getPublicKey(), keyGenerator1.getPrivateKey());
        player2 = new Player2_recipient("이성원", keyGenerator2.getPublicKey(), keyGenerator2.getPrivateKey());
        player3 = new Player3_recipient("휴 잭맨", keyGenerator3.getPublicKey(), keyGenerator3.getPrivateKey());
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            System.out.println("어떤 기능을 수행하시겠습니까?\n");
            System.out.println("잔고확인, 송금하기");

            String order = input.nextLine();

            if(order.equals("잔고확인") || order.equals("잔고 확인")) {
                System.out.println("현재 잔고: " + checkBalance());
                finished = true;
            }

            else if (order.equals("송금하기") || order.equals("송금 하기")) {
                transfer(); // 송금 메서드 호출
                finished = true;
            }

            else
                System.out.println("올바른 명령어가 아닙니다.");
        }

        input.close();
    }

    /**
     * 송금 기능
     */
    public void transfer() {
        System.out.println("송금하시려면 공개키 주소를 입력해 주세요.");
        System.out.println(player2.getPublicKey()+" or "+player3.getPublicKey());

        String name = input.nextLine();


        if (name.equals("휴 잭맨")) {
            System.out.println("몇 BTC를 송금하시겠습니까?");
            double btc = input.nextDouble();
            System.out.println("처리 중 입니다...");
            // TODO
            // 개인키를 사용해 공개키를 해독해야함
            // 블록 생성

            System.out.println("블록이 생성되었습니다.");

            // 송금 처리
            System.out.println("휴 잭맨님께 송금이 완료 되었습니다. ");
        }

        else if(name.equals("이성원")) {
            System.out.println("몇 BTC를 송금하시겠습니까?");
            double btc = input.nextDouble();
            System.out.println("처리 중 입니다...");
            // TODO

            // 블록 생성
            System.out.println("블록이 생성되었습니다.");

            System.out.println("이성원님께 송금이 완료 되었습니다. ");
        }
        else
            System.out.println("올바른 이름이 아닙니다.");
    }

    /**
     * 잔고 확인
     * @return balance 잔고
     */
    public double checkBalance() {
        double balance = 0.0; // TODO
        balance = player1.getBalance();
        return balance;
    }

    public void printWelcome() {
        System.out.println("송금인은 Player1이며 수취인은 Player2 혹은 Player3 입니다. ");
        System.out.println("이 프로그램의 기능은 다음과 같습니다. \n 잔고 확인(Player1), 송금 하기");
        System.out.println();

    }
    public static void main(String[] args) {
        (new Main()).play();
    }
}
