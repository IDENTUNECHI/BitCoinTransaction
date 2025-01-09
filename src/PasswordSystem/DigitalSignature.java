package PasswordSystem;

import java.security.*;
import java.util.Base64;


/**
 * 거래 데이터를 전자 서명으로 변환합니다
 */
public class DigitalSignature {

    private final String transactionData; // 거래 데이터
    private boolean isVerified = false; // 검증 결과
    private byte[] digitalSignature;

    public DigitalSignature (String transactionData) {
        this.transactionData = transactionData;

    }

    public void processTransaction(String transactionData) throws Exception {
        // 1. 키 쌍 생성
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // 2. 데이터 해시 생성
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(transactionData.getBytes());

        // 3. 디지털 서명 생성
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(hash);
        digitalSignature = signature.sign();

        // 4. 디지털 서명 검증 (수정 필요: 채굴을 통해 검증이 된다는 것을 명시적으로 보여줘야할 필요가 있음)
        // 현재는 라이브러리를 통해서 검증 처리되고 있기 때문에 채굴을 통해 여러 네트워크가 검증을 한다는 것이 눈에 안들어옴
        Signature verifySignature = Signature.getInstance("SHA256withRSA");
        verifySignature.initVerify(publicKey);
        verifySignature.update(hash);
        isVerified = verifySignature.verify(digitalSignature);
    }

    public String getDigitalSignature() {
        return digitalSignature.toString();
    }

    public String getTransactionData() {
        return transactionData;
    }

    public boolean isVerified() {
        return isVerified;
    }
}
