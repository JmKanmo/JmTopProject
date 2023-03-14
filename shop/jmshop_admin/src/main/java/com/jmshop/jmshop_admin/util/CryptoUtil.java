package com.jmshop.jmshop_admin.util;

import com.jmshop.jmshop_admin.dto.error.type.CryptoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


/**
 * whatap 개발 시에는 기존에 개발된 라이브러리 사용 여부 결정
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CryptoUtil {
    public byte[] encrypt(final byte[] target, final byte[] key) throws CryptoException {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return Base64.getEncoder().encode(cipher.doFinal(target));
        } catch (Exception e) {
            log.error("CryptoUtil:encrypt: exception:", e);
            throw new CryptoException("암호화 중에 에러가 발생하였습니다.");
        }
    }

    public byte[] decrypt(final byte[] target, final byte[] key) throws CryptoException {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return cipher.doFinal(Base64.getDecoder().decode(target));
        } catch (Exception e) {
            log.error("CryptoUtil:decrypt: exception:", e);
            throw new CryptoException("암호해독 중에 에러가 발생하였습니다.");
        }
    }
}
