import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class CTR {


    public byte[] xorla(byte[] iv, byte[] plainTextByte) {                                                // Klasik xor.

        byte[] xoredArray = new byte[16];

        int i = 0;
        for (byte b : iv)
            xoredArray[i] = (byte) (b ^ plainTextByte[i++]);


        return xoredArray;
    }

    public byte[] encrypt(byte[] plainTextBytes, SecretKey secretKey, ECB ecb, String nonce) throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {

        int counter = 1;
        byte[] input = new byte[64];

        byte[] counterBytes = ByteBuffer.allocate(4).putInt(counter).array();
        byte[] nonceBytes = nonce.getBytes();


        byte[] temp = new byte[16];         // block size
        byte[] cipherTextBytes = new byte[plainTextBytes.length];

        for (int i = 0; i < plainTextBytes.length; i += 16) {
            System.arraycopy(counterBytes, 0, input, 0, counterBytes.length);
            System.arraycopy(nonceBytes, 0, input, counterBytes.length, nonceBytes.length);
            System.arraycopy(plainTextBytes, i, temp, 0, 16);     // temp'e blok'u atıyoz.
            System.arraycopy(xorla(temp,ecb.encryptAES(input, secretKey)), 0, cipherTextBytes, i, 16);
            counter++;

        }

        return cipherTextBytes;
    }


    public byte[] decrypt(byte[] cipherTextBytes, SecretKey secretKey, ECB ecb, String nonce) throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {

        int counter = 1;
        byte[] input = new byte[64];

        byte[] counterBytes = ByteBuffer.allocate(4).putInt(counter).array();
        byte[] nonceBytes = nonce.getBytes();


        byte[] temp = new byte[16];         // block size
        byte[] plainTextBytes = new byte[cipherTextBytes.length];


        for (int i = 0; i < cipherTextBytes.length; i += 16) {
            System.arraycopy(counterBytes, 0, input, 0, counterBytes.length);
            System.arraycopy(nonceBytes, 0, input, counterBytes.length, nonceBytes.length);
            System.arraycopy(cipherTextBytes, i, temp, 0, 16);     // temp'e blok'u atıyoz.
            System.arraycopy(xorla(temp,ecb.encryptAES(input, secretKey)), 0, plainTextBytes, i, 16);
            counter++;

        }

        return plainTextBytes;
    }

}
