package com.common.core.utils.crypto.asymmetric;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.common.core.utils.crypto.Base64Util;

import javax.crypto.Cipher;
import java.security.*;

import static com.common.core.utils.crypto.CryptoUtil.CHAR_SET;

/**
 * 비대칭키 알고리즘.
 * - 데이터 암복호화 사용
 * - 송신자가 수신자의 공개키로 평문을 암호화하고 수신자는 암호문을 수신자의 개인키로 복호화
 */
@Slf4j
public class RSAUtil {
    private static final String ALGORITHM = "RSA";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static KeyPair generateKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            return keyPair;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static String encrypt(String plainText, PublicKey pubKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", BouncyCastleProvider.PROVIDER_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64Util.encode(cipher.doFinal(plainText.getBytes(CHAR_SET)));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static String decrypt(String base64CipherText, PrivateKey privKey) {
        try {
            byte[] cipherText = Base64Util.decode(base64CipherText);
            // decrypt the text using the private key
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", BouncyCastleProvider.PROVIDER_NAME);
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            return new String(cipher.doFinal(cipherText), "UTF-8");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
