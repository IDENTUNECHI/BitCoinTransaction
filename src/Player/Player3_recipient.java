package Player;

import java.sql.Time;

public class Player3_recipient {

    private String name;
    private String privateKey;
    private String walletAddress;

    public Player3_recipient(String name, String privateKey,String walletAddress){
        this.name = name;
        this.privateKey = privateKey;
        this.walletAddress = walletAddress;
    }

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

