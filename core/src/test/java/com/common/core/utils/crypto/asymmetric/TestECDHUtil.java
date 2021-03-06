package com.common.core.utils.crypto.asymmetric;

import org.junit.Test;
import com.common.core.utils.crypto.CryptoUtil;
import com.common.core.utils.crypto.symmetric.AESUtil;

import java.security.KeyPair;

import static org.junit.Assert.assertEquals;

public class TestECDHUtil {
    @Test
    public void exchangeKey() {
        KeyPair alice = ECDSAUtil.generateKey();
        KeyPair bob = ECDSAUtil.generateKey();

        //Alice와 Bob은 상대방의 Public Key와 자신의 Private Key를 이용하여 공통된 Key 값을 추출할 수 있다.
//        System.out.println(HexUtil.encodeString(ECDHUtil.generateSharedSecret(bob.getPrivate(), alice.getPublic())));
//        System.out.println(HexUtil.encodeString(ECDHUtil.generateSharedSecret(alice.getPrivate(), bob.getPublic())));

        byte[] sharedSecret = ECDHUtil.generateSharedSecret(bob.getPrivate(), alice.getPublic());
        String plainText = "하하ㅏ";
        String cipherText = AESUtil.encryptByKey(plainText, sharedSecret, CryptoUtil.CryptoBit.SMALL);
        String originText = AESUtil.decryptByKey(cipherText, sharedSecret, CryptoUtil.CryptoBit.SMALL);
        assertEquals(plainText, originText);
    }
}
