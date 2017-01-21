package in.ac.iitp.anwesha2k17;

import android.util.Base64;

import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by gagan on 12/1/16.
 */
public class HMac {

    Random ran = new Random();
    private String message = "Message";

    private char getRandChar() {
        if (ran.nextBoolean())
            return (char) ('a' + ran.nextInt(26) - 1);
        return (char) ('A' + ran.nextInt(26) - 1);


    }

    private String getRandomString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++)
            sb.append(getRandChar());
        return sb.toString();
    }

    String getHash(String secret) {
        message = getRandomString();
        try {
            if (secret == null) return null;

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String hash = Base64.encodeToString(sha256_HMAC.doFinal(message.getBytes()), Base64.DEFAULT);
            return hash;
        } catch (Exception e) {
            return null;
        }
    }

    String getMessage() {
        return message;
    }
}
