package Player;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Player1_Sender {
    private String name;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String walletAddress;


    private double balance;

    public Player1_Sender(String name, PrivateKey privateKey, PublicKey publicKey ,String walletAddress){
        this.name = name;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.walletAddress = walletAddress;
        this.publicKey = publicKey;
        balance = 1000.0;
    }

    public void withdraw(double balance) {
        this.balance = this.balance - balance;
    }
    public void deposit (double balance) {
        this.balance = this.balance + balance;
    }

    public double getBalance() { return balance;}
    public String getName() {
        return name;
    }
    public String getWalletAddress() {
        return walletAddress;
    }
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
    public PublicKey getPublicKey() {return  publicKey;}
}
