package dininghall.view.payment;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HmacSHA256 implements Serializable {
    public static final String HMAC_SHA256 = "HmacSHA256";

    public static String generateHMAC(final String key, final String data) throws NoSuchAlgorithmException, InvalidKeyException {
        if (key == null || data == null) throw new NullPointerException();

        final Mac hMacSHA256 = Mac.getInstance(HMAC_SHA256);

        byte[] hmacKeyBytes = key.getBytes(StandardCharsets.UTF_8);

        final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, HMAC_SHA256);

        hMacSHA256.init(secretKey);

        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);

        byte[] res = hMacSHA256.doFinal(dataBytes);

        return Base64.getEncoder().encodeToString(res);
    }
}
