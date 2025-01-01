import PasswordSystem.PrivateKey;
import PasswordSystem.PublicKey;

import java.util.*;

/**
 * 1. Player1의 입력을 받는다.
 * 2. 입력 프롬프트는 다음과 같다.
 *     1. 어떤 도움이 필요한가요? (잔고 확인, 송금하기)
 *     2. 누구에게 송금할까요? (Player2와 Player3 둘 중 하나만 가능하다.)
 *     3. 몇 BTC 를 송금할까요?
 */
public class Main {

    private Player1_Sender player1;
    private Player2_recipient player2;
    private Player3_recipient player3;

    private Scanner input = new Scanner(System.in);

    public Main() {
        player1 = new Player1_Sender(new PublicKey().getPublicKey(), new PrivateKey().getPrivateKey());
        player2 = new Player2_recipient(new PublicKey().getPublicKey(), new PrivateKey().getPrivateKey());
        player3 = new Player3_recipient(new PublicKey().getPublicKey(), new PrivateKey().getPrivateKey());
    }

    public void play() {

        printWelcome();

        boolean finished = false;
        while (!finished) {
            System.out.println("어떤 기능을 수행하시겠습니까?");
            System.out.println("잔고확인, 송금하기");

            String order = input.nextLine();

            if(order.equals("잔고확인") || order.equals("잔고 확인")) {
                System.out.println("현재 잔고: " + checkBalance());
                finished = true;
            }

            else if (order.equals("송금하기") || order.equals("송금 하기")) {
                transfer();
                finished = true;
            }

            else
                System.out.println("올바른 명령어가 아닙니다.");
        }

        input.close();
    }

    public void transfer() {
        System.out.println("누구에게 송금하시겠습니까?");
        System.out.println("서연주 or 이성원");

        String name = input.nextLine();


        if (name.equals("서연주")) {
            System.out.println("몇 BTC를 송금하시겠습니까?");
            double btc = input.nextDouble();
            System.out.println("처리 중 입니다...");

        }

        else if(name.equals("이성원")) {
            System.out.println("몇 BTC를 송금하시겠습니까?");
            double btc = input.nextDouble();
            System.out.println("처리 중 입니다...");

        }

        else
            System.out.println("올바른 이름이 아닙니다.");


    }

    public double checkBalance() {
        double balance = 0.0;
        balance = player1.getBalance();
        return balance;
    }

    public void printWelcome() {
        System.out.println("송금인은 Player1이며 수령인은 Player2 혹은 Player3 입니다. ");
        System.out.println("이 프로그램의 기능은 다음과 같습니다. \n 잔고 확인(Player1), 송금 하기");

    }
    public static void main(String[] args) {
        (new Main()).play();
    }
}
