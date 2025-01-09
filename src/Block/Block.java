package Block;

public class Block {

    private String hashcode;
    private String nonce;
    private String data;

    public Block(String hashcode, String nonce, String data){
        this.hashcode = hashcode;
        this.nonce = nonce;
        this.data = data;
    }
}
