package Player;

public class Player1_Sender {
    private String name;
    private String privateKey;
    private String walletAddress;
    private double balance;

    public Player1_Sender(String name, String privateKey,String walletAddress){
        this.name = name;
        this.privateKey = privateKey;
        this.walletAddress = walletAddress;
        balance = 0.0;
    }


    public double getBalance() { return balance;}

    public String getWalletAddress() {
        return walletAddress;
    }
    public String getPrivateKey() {
        return privateKey;
    }

    public String getName() {
        return name;
    }
}
