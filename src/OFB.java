import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class OFB {


    public byte[] xorla(byte[] iv, byte[] plainTextByte){                                                // Klasik xor.

        byte[] xoredArray = new byte[16];

        int i = 0;
        for (byte b : iv)
            xoredArray[i] = (byte) (b ^ plainTextByte[i++]);


        return xoredArray;
    }

    public byte[] encrypt(byte[] plainTextBytes, SecretKey secretKey, ECB ecb, byte[] iv) throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {

        byte[] tempIV = iv.clone();
        byte[] temp = new byte[16];         // block size
        byte[] cipherText = new byte[plainTextBytes.length];

        for (int i = 0; i< plainTextBytes.length; i += 16){
            System.arraycopy(plainTextBytes,i,temp,0,16);     // temp'e blok'u atıyoz.
            System.arraycopy(xorla(ecb.encryptAES(tempIV,secretKey),temp),0,cipherText,i,16);
            tempIV = ecb.encryptAES(tempIV,secretKey);

            //System.arraycopy(ecb.encryptAES(tempIV,secretKey),0,tempIV,0,16);

        }



        return cipherText;


    }


    public byte[] decrypt(byte[] cipherTextBytes, SecretKey secretKey, ECB ecb, byte[] iv) throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {

        byte[] tempIV = iv.clone();
        byte[] temp = new byte[16];         // block size
        byte[] plainText = new byte[cipherTextBytes.length];

        for (int i = 0; i< plainText.length; i += 16){
            System.arraycopy(cipherTextBytes,i,temp,0,16);                                   // temp'e cipherText blok'u atıyoz.
            System.arraycopy(xorla(ecb.encryptAES(tempIV,secretKey),temp),0,plainText,i,16);
            tempIV = ecb.encryptAES(tempIV,secretKey);
            //System.arraycopy(ecb.encryptAES(tempIV,secretKey),0,tempIV,0,16);

        }



        return plainText;


    }




}
