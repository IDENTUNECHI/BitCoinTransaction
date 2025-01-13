package PasswordSystem;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 * 블록체인 지갑 주소 생성
 */
public class KeyAndWalletGenerator {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String walletAddress;
    /**
     * RSA 키 쌍 생성
     * @return KeyPair
     */
    private KeyPair createKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048, new SecureRandom());
        return keyPairGen.generateKeyPair();
    }
    /**
     * RSA 키 쌍을 생성.
     */
    public void generateKeyPair() throws Exception {
        KeyPair keyPair = createKeyPair();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    /**
     * 지갑 주소를 생성.
     * @param name 사용자 이름
     */
    public void generateWalletAddress(String name) {
        if (publicKey == null) {
            throw new IllegalStateException("Public key has not been initialized. Call generateKeyPair() first.");
        }
        try {
            // 이름과 를 결합하여 공개키를 수정
            String modifiedPublicKey = publicKey.toString() + name;

            // SHA-256 해시 적용
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(modifiedPublicKey.getBytes("UTF-8"));

            // 해시 값을 16진수 문자열로 변환
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            // 30자리로 제한
            this.walletAddress = hexString.toString().substring(0, 30);
        } catch (Exception e) {
            throw new RuntimeException("Error generating wallet address", e);
        }
    }

    /**
     * 현재 지갑 주소를 반환합니다.
     * @return 지갑 주소 (30자리)
     */
    public String getWalletAddress() {
        if (walletAddress == null) {
            throw new IllegalStateException("Wallet address has not been generated. Call generateWalletAddress() first.");
        }
        return walletAddress;
    }



    /**
     * 개인키를 반환합니다.
     * @return PrivateKey
     */
    public PrivateKey getPrivateKey() {
        if (privateKey == null) {
            throw new IllegalStateException("Private key has not been initialized. Call generateKeyPair() first.");
        }
        return privateKey;
    }

    /**
     * 공개키를 반환합니다.
     * @return PublicKey
     */
    public PublicKey getPublicKey() {
        if (publicKey == null) {
            throw new IllegalStateException("Public key has not been initialized. Call generateKeyPair() first.");
        }
        return publicKey;
    }

    public static void main(String[] args) throws Exception {
        KeyAndWalletGenerator test = new KeyAndWalletGenerator();
        test.generateKeyPair();
        test.generateWalletAddress("김이든");
        System.out.println(test.getWalletAddress());
    }
}
