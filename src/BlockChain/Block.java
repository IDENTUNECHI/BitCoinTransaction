package BlockChain;

/**
 * 각 블록에는 한 집단의 유효한 트랜잭션과 이전 블록의 해시, 논스가 포함
 *
 */
// 블록 클래스
public class Block {
    private String previousHash;
    private String transactionData;
    private String hash;

    public Block(String previousHash, String transactionData) {
        this.previousHash = previousHash;
        this.transactionData = transactionData;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return Integer.toHexString((previousHash + transactionData).hashCode());
    }

    public String getHash() {
        return hash;
    }

    public String getTransactionData() {
        return transactionData;
    }
}
