package bo.gob.mintrabajo.ovt.Util;

import base64.Base64;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/29/13
 * Time: 12:37 PM
 * To change this template use File | Settings | File Templates.
 */

public class Crypt {

    private SecureRandom secureRandom;
    private Base64 base64;

    public Crypt() {
        secureRandom = new SecureRandom();
        base64 = new Base64();
    }

    /**
     * Get a random string
     *
     * @param bits number of bits
     * @return
     */
    public String getRandomString(int bits) {
        byte[] key = new byte[bits];
        secureRandom.nextBytes(key);
        return new Base64(true).encodeAsString(key);
    }

    /**
     * Encryt a textPlain
     *
     * @param textPlain
     * @return
     */
    public String crypt(String textPlain) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            String key = getRandomString(16);
            SecretKeySpec secretKeySpec = new SecretKeySpec(base64.decode(key), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            String ciphered = base64.encodeAsString(cipher.doFinal(textPlain.getBytes("UTF-8")));
            //TODO: enhaced this
            return key + ciphered;
        } catch (GeneralSecurityException gse) {
            throw new RuntimeException(gse);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee);
        }
    }

    /**
     * Decrypt a ciphered in base 64 string
     *
     * @param cipheredInBase64
     * @return
     */
    public String decrypt(String cipheredInBase64) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            // get key and text
            // TODO: a new form of get key and text
            if (cipheredInBase64.length() > 22) {
                String key = cipheredInBase64.substring(0, 22);
                String text = cipheredInBase64.substring(22);
                SecretKeySpec secretKeySpec = new SecretKeySpec(base64.decode(key), "AES");
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                byte[] decode = base64.decode(text);
                byte[] ciphered = cipher.doFinal(decode);
                return new String(ciphered, "UTF-8");
            } else {
                return cipheredInBase64;
            }
        } catch (GeneralSecurityException gse) {
            throw new RuntimeException(gse);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee);
        }
    }
}
