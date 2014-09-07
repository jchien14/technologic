package encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {
    public static byte[] encrypt(byte[] plainText, String token) {
        return cipher(plainText, token, Cipher.ENCRYPT_MODE);
    }

    public static byte[] decrypt(byte[] cipherText, String token) {
        return cipher(cipherText, token, Cipher.DECRYPT_MODE);
    }

    private static byte[] cipher(byte[] text, String token, int mode) {
        try {
            // TODO figure out what algorithm we want here and change it, may require changing the secret key spec
            Cipher cipher = Cipher.getInstance("AES");
            long[] hash = MurmurHash3.hash(token.getBytes(StandardCharsets.US_ASCII));
            byte[] keyBytes = ByteBuffer.allocate(16).putLong(hash[0]).putLong(hash[1]).array();
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            cipher.init(mode, key);
            return cipher.doFinal(text);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new AssertionError("No such cipher option", e);
        } catch (InvalidKeyException e) {
            throw new AssertionError("Key is invalid", e);
        } catch (BadPaddingException e) {
            throw new AssertionError("Bad Padding", e);
        } catch (IllegalBlockSizeException e) {
            throw new AssertionError("Illegal block size", e);
        }
    }
}
