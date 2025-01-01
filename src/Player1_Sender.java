import PasswordSystem.PrivateKey;
import PasswordSystem.PublicKey;

import java.sql.Time;

public class Player1_Sender {
    private String name;
    private Time time;
    private double publicKey;
    private double privateKey;

    private double balance;

    public Player1_Sender(double publicKey, double privateKey){
        this.name = "김이든";
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public double getBalance() { return balance;}
    public double getPublicKey() {
        return publicKey;
    }
    public double getPrivateKey() {
        return privateKey;
    }
}
