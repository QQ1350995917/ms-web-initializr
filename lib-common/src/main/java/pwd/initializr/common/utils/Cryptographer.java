package pwd.initializr.common.utils;

import java.security.KeyFactory;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * pwd.initializr.common.utils@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2020-07-21 22:24
 *
 * @author DingPengwei[www.dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
public class Cryptographer {

  public static String decrypt(String cipherText, String salt) {
//    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//    keyGenerator.init(128, new SecureRandom(salt.getBytes()));
//    SecretKey secretKey = keyGenerator.generateKey();
//    byte[] enCodeFormat = secretKey.getEncoded();
//    SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//    Cipher cipher = Cipher.getInstance("AES");
//    cipher.init(Cipher.DECRYPT_MODE, key);
//    byte[] resultByte = cipher.doFinal(cipherText.getBytes("utf-8"));
//    String result = new String(resultByte);
    return cipherText;
  }

  public static String encrypt(String clearText, String salt) {
//    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//    keyGenerator.init(128, new SecureRandom(salt.getBytes()));
//    SecretKey secretKey = keyGenerator.generateKey();
//    byte[] enCodeFormat = secretKey.getEncoded();
//    SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//    Cipher cipher = Cipher.getInstance("AES");
//    byte[] byteContent = clearText.getBytes("utf-8");
//    cipher.init(Cipher.ENCRYPT_MODE, key);
//    byte[] resultByte = cipher.doFinal(byteContent);
//    String result = new String(resultByte, "utf-8");
    return clearText;
  }
}
