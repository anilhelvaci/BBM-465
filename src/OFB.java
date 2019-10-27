import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class OFB {



    public byte[] encrypt(byte[] plainTextBytes, SecretKey secretKey, ECB ecb, byte[] iv, String algorithm) throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {


        int blockSize = Main.algorithmBlockSize(algorithm);
        byte[] tempIV = iv.clone();
        byte[] temp = new byte[blockSize];         // block size
        byte[] cipherText = new byte[plainTextBytes.length];

        for (int i = 0; i< plainTextBytes.length; i += blockSize){
            System.arraycopy(plainTextBytes,i,temp,0,blockSize);     // temp'e blok'u atıyoz.
            System.arraycopy(Main.xorla(ecb.encrypt(tempIV,secretKey,algorithm),temp,algorithm),0,cipherText,i,blockSize);
            tempIV = ecb.encrypt(tempIV,secretKey,algorithm);


        }



        return cipherText;


    }


    public byte[] decrypt(byte[] cipherTextBytes, SecretKey secretKey, ECB ecb, byte[] iv, String algorithm) throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {

        int blockSize = Main.algorithmBlockSize(algorithm);
        byte[] tempIV = iv.clone();
        byte[] temp = new byte[blockSize];         // block size
        byte[] plainText = new byte[cipherTextBytes.length];

        for (int i = 0; i< plainText.length; i += blockSize){
            System.arraycopy(cipherTextBytes,i,temp,0,blockSize);                                   // temp'e cipherText blok'u atıyoz.
            System.arraycopy(Main.xorla(ecb.encrypt(tempIV,secretKey,algorithm),temp,algorithm),0,plainText,i,blockSize);
            tempIV = ecb.encrypt(tempIV,secretKey,algorithm);

        }



        return plainText;


    }




}
