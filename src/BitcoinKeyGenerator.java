import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class BitcoinKeyGenerator {

    public static void main(String[] args) throws Exception {
        // 1. BouncyCastle 프로바이더 등록
        Security.addProvider(new BouncyCastleProvider());

        // 2. 키 쌍 생성 (개인키 및 공개키)
        KeyPair keyPair = generateKeyPair();

        // 3. 개인키와 공개키 추출
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();


        // 4. 개인키를 16진수(Hex)로 출력
        byte[] privateKeyBytes = privateKey.getEncoded();
        System.out.println("Private Key (Hex): " + bytesToHex(privateKeyBytes));

        // 5. 공개키를 비압축 형식으로 출력
        byte[] publicKeyBytes = publicKey.getEncoded();
        System.out.println("Public Key (Uncompressed Hex): " + bytesToHex(publicKeyBytes));

        // 6. 공개키를 압축 형식으로 변환 및 출력
        byte[] compressedPublicKey = compressPublicKey(publicKeyBytes);
        System.out.println("Public Key (Compressed Hex): " + bytesToHex(compressedPublicKey));
    }

    private static KeyPair generateKeyPair() throws Exception {
        // 키 생성 매개변수 설정 (secp256k1 타원곡선)
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
        keyPairGenerator.initialize(ecSpec, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    private static byte[] compressPublicKey(byte[] uncompressedKey) {
        // 공개키에서 x, y 좌표 추출
        BigInteger x = new BigInteger(1, java.util.Arrays.copyOfRange(uncompressedKey, 1, 33));
        BigInteger y = new BigInteger(1, java.util.Arrays.copyOfRange(uncompressedKey, 33, 65));

        // 압축된 공개키 접두사 결정 (y의 짝/홀수에 따라)
        byte prefix = (y.mod(BigInteger.TWO).equals(BigInteger.ZERO)) ? (byte) 0x02 : (byte) 0x03;

        // 압축된 공개키 생성
        byte[] compressedKey = new byte[33];
        compressedKey[0] = prefix; // 접두사 추가
        System.arraycopy(x.toByteArray(), 0, compressedKey, 1, x.toByteArray().length);

        return compressedKey;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
