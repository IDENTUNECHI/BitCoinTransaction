package Player;

public class Player2_recipient {

    private String name;
    private String privateKey;
    private String walletAddress;

    public Player2_recipient(String name, String privateKey,String walletAddress){
        this.name = name;
        this.privateKey = privateKey;
        this.walletAddress = walletAddress;
    }
    public String getPrivateKey() {
        return privateKey;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public String getName() {
        return name;
    }
}

