package Player;

import java.sql.Time;

public class Player3_recipient {

    private String name;
    private Time time;
    private String publicKey;
    private String privateKey;

    public Player3_recipient(String name, String publicKey, String privateKey){
        this.name = name;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }
    public String getPrivateKey() {
        return privateKey;
    }
}

