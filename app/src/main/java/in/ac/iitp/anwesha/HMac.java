package in.ac.iitp.anwesha;

import android.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by gagan on 12/1/16.
 */
public class HMac {

    private String message = "Message";

    String getHash()
   {
       try {
           String secret = AllIDS.USER_key;
           if(secret==null) return null;

           Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
           SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
           sha256_HMAC.init(secret_key);

           String hash = Base64.encodeToString(sha256_HMAC.doFinal(message.getBytes()), Base64.DEFAULT);
           return hash;
       }
       catch (Exception e){
           return null;
       }
   }
    String getMessage()
    {
        return message;
    }
}
