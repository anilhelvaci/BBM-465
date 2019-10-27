import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ECB {



    public byte[] encrypt(byte[] plainText, SecretKey secretKey, String algorithm) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {


        Cipher cipher = Cipher.getInstance(algorithm+"/ECB/NoPadding");

        cipher.init(Cipher.ENCRYPT_MODE,secretKey);

        return cipher.doFinal(plainText);

    }

    public byte[] decrypt(byte[] cipherText, SecretKey secretKey, String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {

        Cipher cipher = Cipher.getInstance(algorithm+"/ECB/NoPadding");

        cipher.init(Cipher.DECRYPT_MODE,secretKey);

        return cipher.doFinal(cipherText);
    }




}
