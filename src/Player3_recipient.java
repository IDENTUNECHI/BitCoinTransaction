import java.sql.Time;

public class Player3_recipient {

    private String name;
    private Time time;
    private double publicKey;
    private double privateKey;

    public Player3_recipient(double publicKey, double privateKey){
        this.name = "이성원";
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public double getPublicKey() {
        return publicKey;
    }
    public double getPrivateKey() {
        return privateKey;
    }
}

