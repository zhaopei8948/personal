package online.zhaopei.personal.util;

import online.zhaopei.personal.constant.CommonConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Created by zhaopei on 18/5/10.
 */
public final class AESUtil {

    private static final Log LOGGER = LogFactory.getLog(AESUtil.class);

    public static String sign(String message, String secret) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        byte[] keyBytes = secret.getBytes(CommonConstant.UTF_8);
        sha256_HMAC.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));
        return new String(Base64.getEncoder().encode(sha256_HMAC.doFinal(message.getBytes(CommonConstant.UTF_8))), CommonConstant.UTF_8);
    }

    public static String AESDncode(String encodeRules, String content) {
        LOGGER.info("encodeRules=" + encodeRules + "\ncontent=" + content);
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(encodeRules.getBytes(CommonConstant.UTF_8));
            keygen.init(128, secureRandom);
            SecretKey original_key = keygen.generateKey();
            byte[] raw = original_key.getEncoded();
            SecretKey key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] byte_content = Base64.getDecoder().decode(content);
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, CommonConstant.UTF_8);
            return AES_decode;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("AESDncode error", e);
        }
        return null;
    }
}
