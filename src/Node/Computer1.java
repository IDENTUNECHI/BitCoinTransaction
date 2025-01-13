package Node;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Computer1 {
    private String transactionData;

    public Computer1(String transactionData) {
        this.transactionData = transactionData;
        System.out.println("Computer1: 거래 데이터를 수신했습니다.");
    }

    public boolean verifyTransaction(String publicKey, String signature) {
        System.out.println("Computer1: 거래를 검증 중...");
        boolean isValid = !transactionData.isEmpty() && signature != null && publicKey != null;
        if (isValid) {
            System.out.println("Computer1: 거래가 유효합니다.");
        } else {
            System.out.println("Computer1: 거래가 유효하지 않습니다.");
        }
        return isValid;
    }

    public String mineBlock(String previousHash) {
        System.out.println("Computer1: 블록 채굴을 시작합니다...");
        String hash;
        int nonce = 0;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 작업 증명 (Proof of Work)
            do {
                String input = previousHash + transactionData + nonce;
                byte[] hashBytes = digest.digest(input.getBytes());
                hash = bytesToHex(hashBytes);
                nonce++;
            } while (!hash.startsWith("0")); // 난이도를 적절히 설정

            System.out.println("Computer1: 블록 채굴 완료! 해시값: " + hash);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    // 바이트 배열을 16진수 문자열로 변환하는 헬퍼 메서드
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
