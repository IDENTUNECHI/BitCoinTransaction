package Block;

/**
 * Block 패키지 안에서 거래 데이터를 처리하는 클래스
 */
public class InfoData {
    private String transactionData;

    public InfoData(String transactionData) {
        this.transactionData = transactionData;
    }

    public String getTransactionData() {
        return transactionData;
    }
}
