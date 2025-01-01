import java.sql.Time;

public class Player2_recipient {

    private String name;
    private Time time;
    private double publicKey;
    private double privateKey;

    public Player2_recipient(double publicKey, double privateKey){
        this.name = "서연주";
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

