import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class CTR {
    private  int blockSize;

    public CTR(int blockSize) {
        this.blockSize = blockSize;
    }

    public byte[] encrypt(byte[] plainTextBytes, SecretKey secretKey, ECB ecb, byte[] nonceBytes, String algorithm) throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {


//        int blockSize = FileCipher.algorithmBlockSize(algorithm);
        int counter = 1;
        byte[] input = new byte[blockSize];

        byte[] counterBytes = ByteBuffer.allocate(blockSize/2).putInt(counter).array();

        byte[] temp = new byte[blockSize];         // block size
        byte[] cipherTextBytes = new byte[plainTextBytes.length];

        for (int i = 0; i < plainTextBytes.length; i += blockSize) {
            System.arraycopy(counterBytes, 0, input, 0, counterBytes.length);
            System.arraycopy(nonceBytes, 0, input, counterBytes.length, nonceBytes.length);
            System.arraycopy(plainTextBytes, i, temp, 0, blockSize);     // temp'e blok'u atıyoruz.
            System.arraycopy(FileCipher.xorla(temp,ecb.encrypt(input, secretKey,algorithm),algorithm), 0, cipherTextBytes, i, blockSize);
            counter++;

        }

        return cipherTextBytes;
    }


    public byte[] decrypt(byte[] cipherTextBytes, SecretKey secretKey, ECB ecb, byte[] nonceBytes, String algorithm) throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {

//        int blockSize = FileCipher.algorithmBlockSize(algorithm);
        int counter = 1;
        byte[] input = new byte[blockSize];

        byte[] counterBytes = ByteBuffer.allocate(blockSize/2).putInt(counter).array();


        byte[] temp = new byte[blockSize];         // block size
        byte[] plainTextBytes = new byte[cipherTextBytes.length];


        for (int i = 0; i < cipherTextBytes.length; i += blockSize) {
            System.arraycopy(counterBytes, 0, input, 0, counterBytes.length);
            System.arraycopy(nonceBytes, 0, input, counterBytes.length, nonceBytes.length);
            System.arraycopy(cipherTextBytes, i, temp, 0, blockSize);     // temp'e blok'u atıyoz.
            System.arraycopy(FileCipher.xorla(temp,ecb.encrypt(input, secretKey,algorithm),algorithm), 0, plainTextBytes, i, blockSize);
            counter++;

        }

        return plainTextBytes;
    }

}
