package encryption;

import static org.junit.Assert.*;

public class EncryptUtilsTest {
    @org.junit.Test
    public void testDecrypt() throws Exception {
        byte[] text = new byte[]{1, 2, 3, 4, 5};
        String token = "jwEFJIEVN12Gd38g";
        byte[] cipherText = EncryptUtils.encrypt(text, token);
        byte[] plainText = EncryptUtils.decrypt(cipherText, token);
        assertArrayEquals(plainText, text);
    }
}