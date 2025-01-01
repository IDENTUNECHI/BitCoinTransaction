package PasswordSystem;

/**
 * 사용자 고유의 개인키를 생성한다.
 *
 * 개인키 생성과정은 다음과 같은 과정이다.
 *
 * 1. 난수 생성
 * 2. 변환(256bit 정수 -> 16진수)
 * 3. 검증
 */

public class PrivateKey {
    private double privateKey;

    public double getPrivateKey(){
        return privateKey;
    }

}
