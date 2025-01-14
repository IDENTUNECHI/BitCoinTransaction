package Test;
import BlockChain.Block;
import BlockChain.BlockChain;
import Node.Computer1;
import Node.Computer2;
import PasswordSystem.KeyAndWalletGenerator;
import PasswordSystem.DigitalSignature;
import Player.Player1_Sender;
import Player.Player2_recipient;
import Player.Player3_recipient;
import java.util.*;
/**
 * 실행 클래스
 */
public class Main {

    // 플레이어 3명 생성
    private Player1_Sender player1;
    private Player2_recipient player2;
    private Player3_recipient player3;

    // 키 생성기 생성
    KeyAndWalletGenerator wallet1 = new KeyAndWalletGenerator();
    KeyAndWalletGenerator wallet2 = new KeyAndWalletGenerator();
    KeyAndWalletGenerator wallet3 = new KeyAndWalletGenerator();

    // 블록 체인 생성
    private BlockChain blockchain = new BlockChain();

    private Scanner input = new Scanner(System.in);
    /**
     * 생성자
     */
    public Main() {
        try {
            wallet1.generateKeyPair();
            wallet2.generateKeyPair();
            wallet3.generateKeyPair();

            wallet1.generateWalletAddress("김이든");
            wallet2.generateWalletAddress("이성원");
            wallet3.generateWalletAddress("휴 잭맨");
        } catch (Exception e) {
            System.err.println("Error initializing wallet: " + e.getMessage());
        }

        // 플레이어들에게 지갑 주소 할당
        player1 = new Player1_Sender("김이든",wallet1.getPrivateKey(),wallet1.getPublicKey(), wallet1.getWalletAddress());
        player2 = new Player2_recipient("이성원", wallet2.getPrivateKey(), wallet2.getPublicKey(), wallet2.getWalletAddress());
        player3 = new Player3_recipient("휴 잭맨", wallet3.getPrivateKey(), wallet3.getPublicKey(),wallet3.getWalletAddress());
    }
    /**
     * FRONT
     *
     * 1. Player1의 입력을 받는다.
     * 2. 입력 프롬프트는 다음과 같다.
     *     1. 어떤 도움이 필요한가요? (잔고 확인, 송금하기)
     *     2. 누구에게 송금할까요? (Player2와 Player3 둘 중 하나만 가능하다.)
     *     3. 몇 BTC 를 송금할까요?
     */
    public void play() throws Exception {

        printWelcome();

        boolean finished = false;
        while (!finished) {
            System.out.println("어떤 기능을 수행하시겠습니까?\n");
            System.out.println("잔고확인, 송금하기, 끝내기");

            String order = input.nextLine();

            if(order.equals("잔고확인") || order.equals("잔고 확인")) {
                System.out.println("현재 잔고: " + checkBalance());
            }

            else if (order.equals("송금하기") || order.equals("송금 하기")) {
                transfer(); // 송금 메서드 호출
            }

            else if (order.equals("끝내기")) {
                finished = true;
                input.close();
            }

            else
                System.out.println("올바른 명령어가 아닙니다.");
        }
    }

    /**
     * 송금 기능
     *
     * 순서도:
     * 1. 송금인의 비밀키와 수령인의 비트코인 주소 확인
     * 2. "누가 누구에게 몇 BTC를 송금했다." 라는 '거래 데이터'를 비밀키로 변환하여 '서명 데이터' 만듦
     * 3. 서명데이터와 공개키를 비트코인 네트워크 상의 컴퓨터(채굴자)에게 공개한다.
     * 4. 채굴자는 공개키를 사용해서 서명데이터를 검증할 수 있으며 검증을 완료하면 거래 유효성 증명되고 송금이 완료된다.
     * 5. 거래가 완료되면 그 거래데이터에 대한 블록이 생성된다.
     */
    public void transfer() throws Exception {
        System.out.println("송금하시려면 송금인의 비밀키와 수취인의 전자 지갑 주소를 입력해 주세요.");
        System.out.println(player2.getWalletAddress()+ " or " + player3.getWalletAddress());

        String senderPrivateKey = player1.getPrivateKey().toString(); // 송금인의 비밀키 입력이 되었다고 가정
        // 왜 가정을 하냐? 실행할 때 마다 비밀키가 바뀜(프로그램 한계)
        // 왜 바뀌냐? 지금 사용하는 라이브러리는 하드웨어 상태 혹은 타임 스탬프 등을 가중치로 설정한 후,
        // 비밀키로 만들고 있어 계속 바뀌는 것으로 예상
        System.out.println("비밀키 입력 완료(편의상 입력됐다 가정)");

        String walletAddress = input.nextLine(); // 수취인의 지갑 주소 입력
        System.out.println("수취인의 지갑 주소 입력 완료");

        // 송금인의 비밀키와 수취인의 지갑 주소 확인
        if (senderPrivateKey.equals(player1.getPrivateKey().toString()) && walletAddress.equals(player2.getWalletAddress())) {
            System.out.println("송금인의 비밀키와 수취인 "+player2.getName()+"님의 비트코인 지갑 주소가 일치합니다.");
            System.out.println("몇 BTC를 송금하시겠습니까?");
            double btc = input.nextDouble();

            // 서명 데이터
            String transactionData1 = player1.getName()+"님이 "+player2.getName()+"님에게 "+ btc +"BTC를 송금했습니다.";

            // 전자 서명
            DigitalSignature digitalSignature1 =
                    new DigitalSignature(transactionData1, player1.getPrivateKey(), player1.getPublicKey());
            digitalSignature1.processTransaction();

            // 전자 서명 검증
            if(digitalSignature1.isVerified()) {
                // UTXO 생성
                UTXO utxo1 = new UTXO("UTXO_"+player2.getName(),
                        player2.getName(), player2.getBalance(), player2.getPrivateKey(), player2.getPublicKey());

                // 송금인이 수취인의 공개키로 거래 정보 암호화
                utxo1.encrypt();
                // 수취인이 수취인의 비밀키로 거래 정보 복호화
                String decrypt = utxo1.decrypt();
                // 첫 UTXO 정보가 수취인에 의해 복호화된 UTXO와 같고, player1잔고가 유효할 때,
                if(utxo1.getDetails().equals(decrypt) && player1.getBalance() >= btc){


                    System.out.println(utxo1.getDetails() + " == " + decrypt);
                    System.out.println("거래 정보 유효 \n");

                    // UTXO를 노드에 전파
                    nodeNetWork(decrypt, digitalSignature1);
                    player1.withdraw(btc);
                    player2.deposit(btc);
                    System.out.println(player2.getName()+"님께 송금이 완료 되었습니다. \n");
                }
                else
                    System.out.println("거래 정보 불일치");

            }
            else
                System.out.println("유효하지 않은 거래 입니다.");
        }

        // 송금인의 비밀키와 수취인의 지갑 주소 확인
        else if (senderPrivateKey.equals(player1.getPrivateKey().toString()) && walletAddress.equals(player3.getWalletAddress())) {
            System.out.println("송금인의 비밀키와 수취인 " + player3.getName() + "님의 비트코인 지갑 주소가 일치합니다.");
            System.out.println("몇 BTC를 송금하시겠습니까?");
            double btc = input.nextDouble();

            // 서명 데이터
            String transactionData2 = player1.getName() + "님이 " + player3.getName() + "님에게 " + btc + "BTC를 송금했습니다.";

            // 전자 서명
            DigitalSignature digitalSignature2 =
                    new DigitalSignature(transactionData2, player1.getPrivateKey(), player1.getPublicKey());
            digitalSignature2.processTransaction();

            // 전자 서명 검증
            if (digitalSignature2.isVerified()) {
                // UTXO 생성
                UTXO utxo2 = new UTXO("UTXO_" + player3.getName(),
                        player3.getName(), player3.getBalance(), player3.getPrivateKey(), player3.getPublicKey());

                // 송금인이 수취인의 공개키로 거래 정보 암호화
                utxo2.encrypt();
                // 수취인이 수취인의 비밀키로 거래 정보 복호화
                String decrypt = utxo2.decrypt();

                // 첫 UTXO 정보가 수취인에 의해 복호화된 UTXO와 같고, player1 잔고가 유효할 때
                if (utxo2.getDetails().equals(decrypt) && player1.getBalance() >= btc) {
                    System.out.println(utxo2.getDetails() + " == " + decrypt);
                    System.out.println("거래 정보 유효 \n");

                    // UTXO를 노드에 전파
                    nodeNetWork(decrypt, digitalSignature2);
                    player1.withdraw(btc);
                    player3.deposit(btc);
                    System.out.println(player3.getName() + "님께 송금이 완료되었습니다. \n");
                } else {
                    System.out.println("거래 정보 불일치");
                }
            } else {
                System.out.println("유효하지 않은 거래입니다.");
            }
        }

        else
            System.out.println("올바른 이름이 아닙니다.");
    }

    private void nodeNetWork(String transactionData, DigitalSignature digitalSignature) {
        System.out.println("노드 네트워크에 거래 정보를 전파 중...");

        // Computer1: 거래 검증 및 블록 채굴
        Computer1 computer1 = new Computer1(transactionData);
        boolean isVerifiedByC1 = computer1.verifyTransaction(player1.getPublicKey().toString(), digitalSignature.getDigitalSignature());

        // Computer2: 거래 검증 및 블록 채굴
        Computer2 computer2 = new Computer2(transactionData);
        boolean isVerifiedByC2 = computer2.verifyTransaction(player1.getPublicKey().toString(), digitalSignature.getDigitalSignature());

        if (isVerifiedByC1 && isVerifiedByC2) {
            System.out.println("모든 노드에서 거래 검증 완료.");

            // 블록 채굴 (네트워크 다룰 줄 몰라서 임의의 노드를 클래스로 생성함)
            String previousHash = blockchain.getLatestBlock().getHash();
            String hashFromC1 = computer1.mineBlock(previousHash);
            String hashFromC2 = computer2.mineBlock(previousHash);

            // 블록 생성 및 추가
            if (blockchain.isChainValid() && hashFromC1.equals(hashFromC2)) {
                Block newBlock = new Block(previousHash, transactionData);
                blockchain.addBlock(newBlock);
                System.out.println("새로운 블록이 블록체인에 추가되었습니다.");
            } else {
                System.out.println("블록 해시 불일치: 블록체인에 추가 실패.");
            }

            // 블록체인 출력
            blockchain.printBlockchain();
        } else {
            System.out.println("검증 실패: 블록 생성 중단.");
        }
    }

    /**
     * 잔고 확인
     * @return balance 잔고
     */
    public double checkBalance() {
        return player1.getBalance();
    }
    public void printWelcome() {
        System.out.println("송금인은 Player1이며 수취인은 Player2 혹은 Player3 입니다. ");
        System.out.println("이 프로그램의 기능은 다음과 같습니다. \n 잔고 확인(Player1), 송금 하기");
        System.out.println();

    }
    public static void main(String[] args) throws Exception {
        (new Main()).play();
    }
}
