package Test;

import java.security.*;
import javax.crypto.Cipher;
import java.util.Base64;

public class UTXO {
    private final String id;
    private final String owner;
    private final double amount;
    private PrivateKey receivePrivateKey;
    private PublicKey receivePublicKey;


    public UTXO(String id, String owner, double amount, PrivateKey receivePrivateKey, PublicKey receivePublicKey) {
        this.id = id;
        this.owner = owner;
        this.amount = amount;
        this.receivePrivateKey = receivePrivateKey;
        this.receivePublicKey = receivePublicKey;
    }


    @Override
    public String toString() {
        return "UTXO{id='" + id + "', owner='" + owner + "', amount=" + amount + "}";
    }

    public String getDetails() {
        return id + "," + owner + "," + amount;
    }

    // 송금인이 수취인의 공개키로 암호화
    public String encrypt() throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, receivePublicKey);
        byte[] encryptedBytes = cipher.doFinal(getDetails().getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 수취인이 수취인의 비밀키로 복호화
    public String decrypt() throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, receivePrivateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encrypt()));
        return new String(decryptedBytes);
    }
//    public static void main(String[] args) {
//        try {
//            // 1. B의 키 쌍 생성 (공개키와 개인키)
//
//            // 2. A가 생성한 UTXO
//            UTXO utxo = new UTXO("utxo1", "Alice", 10.0);
//            String utxoDetails = utxo.getDetails();
//            System.out.println("A가 송금하는 UTXO: " + utxoDetails);
//
//            // 3. A가 B의 공개키로 UTXO 암호화
//            String encryptedUTXO = encrypt(utxoDetails, bPublicKey);
//            System.out.println("암호화된 UTXO: " + encryptedUTXO);
//
//            // 4. B가 자신의 개인키로 UTXO 복호화
//            String decryptedUTXO = decrypt(encryptedUTXO, bPrivateKey);
//            System.out.println("B가 복호화한 UTXO: " + decryptedUTXO);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}


