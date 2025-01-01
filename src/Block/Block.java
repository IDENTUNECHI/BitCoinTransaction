package Block;

public class Block {
    private Hash hashcode;
    private Nonce nonce;
    private InfoData data;

    public Block(Hash hashcode, Nonce nonce, InfoData data){
        this.hashcode = hashcode;
        this.nonce = nonce;
        this.data = data;
    }
}
