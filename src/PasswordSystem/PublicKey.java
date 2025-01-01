package PasswordSystem;

/**
 * 사용자의 공개키를 생성한다.
 *
 * 공개키 생성과정은 다음과 같은 방식 입니다.
 * 1. 타원 곡선 암호화 기본 설정 (secp256k1)
 * 2. 개인키 준비
 * 3. 점 곱 연산을 통해 공개키 생성
 * 4. 공개키 형식
 * 5. 해시를 통해 비트코인 주소 생성
 */
public class PublicKey {
    private double publicKey;

    public double getPublicKey() {
        return publicKey;
    }

}
