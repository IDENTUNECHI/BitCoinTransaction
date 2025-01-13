package Player;

import java.io.PipedReader;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Player2_recipient {

    private String name;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String walletAddress;

    private double balance;

    public Player2_recipient(String name, PrivateKey privateKey, PublicKey publicKey ,String walletAddress){
        this.name = name;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.walletAddress = walletAddress;
        this.balance = 1000.0;
    }

    public void withdraw(double balance) {
        this.balance = this.balance - balance;
    }
    public void deposit (double balance) {
        this.balance = this.balance + balance;
    }
    public double getBalance() {
        return balance;
    }
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
    public PublicKey getPublicKey() {
        return publicKey;
    }
    public String getWalletAddress() {
        return walletAddress;
    }
    public String getName() {
        return name;
    }
}

