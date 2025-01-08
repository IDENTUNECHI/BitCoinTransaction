package PasswordSystem;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;


/**
 * 키 생성
 * SHA-256을 직접 구현한 것이 아닌 라이브러리를 가져다 썼기 때문에 따로 이론 공부 필요
 */
public class UniqueHexGenerator {

    private String name;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public UniqueHexGenerator(String name) {
        this.name = name;
    }

    /**
     * 공개키와 개인키를 초기화합니다.
     * @throws Exception
     */
    public void initializeKeys() throws Exception {
        KeyPair keyPair = generateKeyPair();
        this.privateKey = keyPair.getPrivate(); // 개인키
        this.publicKey = keyPair.getPublic(); // 공개키
    }

    /**
     * RSA 키 쌍을 생성합니다.
     * @return KeyPair
     * @throws Exception
     */
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048, new SecureRandom());
        return keyPairGen.generateKeyPair();
    }

    /**
     * 고유한 64자리 HEX 값을 생성합니다.
     * @param input 입력 문자열
     * @param publicKey 공개 키
     * @return 64자리 HEX 문자열
     */
    public static String generateUniqueHex(String input, String publicKey) {
        try {
            // 이름 길이와 공개키를 결합
            String combinedInput = input.length() + publicKey;

            // SHA-256을 사용해 결합된 입력값을 해시
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(combinedInput.getBytes("UTF-8"));

            // 16진수 64자리로 변환
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            // 64자리 보장
            return hexString.toString().substring(0, 64);

        } catch (Exception e) {
            throw new RuntimeException("Error generating unique hex", e);
        }
    }

    /**
     * 키의 바이트 배열을 HEX 문자열로 변환합니다.
     * @param keyBytes 키의 바이트 배열
     * @return HEX 문자열
     */
    private static String convertKeyToHex(byte[] keyBytes) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha256.digest(keyBytes);

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error converting key to hex", e);
        }
    }

    /**
     * 공개 키를 HEX 문자열로 반환합니다.
     * @return 공개 키 HEX 문자열
     */
    public String getPublicKey() {
        if (publicKey == null) {
            throw new IllegalStateException("Public key has not been initialized. Call initializeKeys() first.");
        }
        return convertKeyToHex(publicKey.getEncoded());
    }

    /**
     * 개인 키를 HEX 문자열로 반환합니다.
     * @return 개인 키 HEX 문자열
     */
    public String getPrivateKey() {
        if (privateKey == null) {
            throw new IllegalStateException("Private key has not been initialized. Call initializeKeys() first.");
        }
        return convertKeyToHex(privateKey.getEncoded());
    }
}
