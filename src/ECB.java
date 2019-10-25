import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ECB {



    public byte[] encryptAES(byte[] plainText, SecretKey secretKey) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {


        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");

        cipher.init(Cipher.ENCRYPT_MODE,secretKey);

        return cipher.doFinal(plainText);

    }

    public byte[] decryptAES(byte[] cipherText, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {

        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");

        cipher.init(Cipher.DECRYPT_MODE,secretKey);

        return cipher.doFinal(cipherText);
    }

    public byte[] encryptDES(byte[] plainText, SecretKey secretKey) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");

        cipher.init(Cipher.ENCRYPT_MODE,secretKey);

        return cipher.doFinal(plainText);

    }

    public byte[] decryptDES(byte[] cipherText, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {

        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");

        cipher.init(Cipher.DECRYPT_MODE,secretKey);

        return cipher.doFinal(cipherText);
    }



}
