import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class CBC {
    private int blockSize;

    public CBC(int blockSize) {
        this.blockSize = blockSize;
    }

    public byte[] encrypt(byte[] plainTextByte, SecretKey secretKey, ECB ecb, byte[] iv, String algorithm) throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {

//        int blockSize = FileCipher.algorithmBlockSize(algorithm);
        byte[] temp = new byte[blockSize];                                                                                 // block size kadar bir temp oluştur.
        byte[] tempIV = iv.clone();                                                                                  // tempIV, main'deki iv'nin clone'u olsun yoksa aşağıdaki işlemlerde iv değişiyor, bu da decrypt ederken sorun çıkarıyor.
        byte[] cipher = new byte[plainTextByte.length];                                                                 // plaintext boyutu kadar boyutu olan ciphertext oluştur.

        for (int i=0; i<plainTextByte.length; i += blockSize){                                                                // 16 byte 16 byte işlem yap, "i" plaintextteki starting position
            System.arraycopy(plainTextByte, i, temp, 0, blockSize);                                                       // plaintext'in 16 byte'ını al temp'e at
            System.arraycopy(ecb.encrypt(FileCipher.xorla(tempIV,temp,algorithm),secretKey,algorithm), 0, cipher, i, blockSize);        // temp'i encrypt et ve cipherText'in "i" ninci pozisyonundan i+16 ıncı pozisyonu arasındaki boşluklara ekle.
            System.arraycopy(cipher,i,tempIV,0,blockSize);                                                                 // yeni iv, bir önceki şifrelenmiş blok olsun.
        }

        return cipher;

    }


    public byte[] decrypt(byte[] cipherTextByte, SecretKey secretKey, ECB ecb, byte[] iv, String algorithm) throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {

//        int blockSize = FileCipher.algorithmBlockSize(algorithm);
        byte[] temp = new byte[blockSize];
        byte[] tempIV = iv.clone();
        byte[] plainText = new byte[cipherTextByte.length];

        for (int i=0; i<cipherTextByte.length; i += blockSize){                                                    // 16 byte 16 byte işlem yap, i plaintextteki starting position
            System.arraycopy(cipherTextByte, i, temp, 0, blockSize);                                       // encrypt methodundaki açıklamalara benzer açıklamalar olacak. İkinci defa yazmadım.
            System.arraycopy(FileCipher.xorla(tempIV,ecb.decrypt(temp,secretKey,algorithm),algorithm), 0, plainText, i, blockSize);
            System.arraycopy(cipherTextByte,i,tempIV,0,blockSize);

        }

        return plainText;

    }

}