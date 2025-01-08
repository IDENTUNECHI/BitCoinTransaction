package Player;

public class Player1_Sender {
    private String name;
    private String publicKey;
    private String privateKey;
    private double balance;

    public Player1_Sender(String publicKey, String privateKey){
        this.name = "김이든";
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        balance = 0.0;
    }


    public double getBalance() { return balance;}
    public String getPublicKey() {
        return publicKey;
    }
    public String getPrivateKey() {
        return privateKey;
    }
}
