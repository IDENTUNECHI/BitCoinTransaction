package BlockChain;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {
    private List<Block> chain;

    public BlockChain() {
        // ArrayList로 시간 순서대로 트랜잭션 저장
        chain = new ArrayList<>();
        chain.add(generateGenesisBlock());
    }

    // 첫번째 블록
    private Block generateGenesisBlock() {
        return new Block("8BE849EEFC40E9FCD84C46B2FADC2F3A70AE71618FA46B0965C07A7A7EFAFBA2", "Genesis Block");
    }

    // 가장 최근 블록
    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    // 블록 추가
    public void addBlock(Block newBlock) {
        chain.add(newBlock);
    }

    //
    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);


            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false; // 얻은 해시값과 계산한 해시값이 다를 때
            }
            if (!currentBlock.getHash().startsWith("0")) { // 작업 증명 간단화
                return false; // 해시가 "0"으로 시작하지 않을 때,
            }
        }
        return true;
    }

    public void printBlockchain() {
        for (Block block : chain) {
            System.out.println("Block Hash: " + block.getHash());
            System.out.println("Transaction Data: " + block.getTransactionData());
        }
    }
}
