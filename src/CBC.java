import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class CBC {


    public byte[] xorla(byte[] iv, byte[] plainTextByte){                                                // Klasik xor.

        byte[] xoredArray = new byte[16];

        int i = 0;
        for (byte b : iv)
            xoredArray[i] = (byte) (b ^ plainTextByte[i++]);


        return xoredArray;
    }

    public byte[] encrypt(byte[] plainTextByte, SecretKey secretKey, ECB ecb, byte[] iv) throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {


        byte[] temp = new byte[16];                                                                     // block size kadar bir temp oluştur.
        byte[] tempIV = iv.clone();                                                                    // tempIV, main'deki iv'nin clone'u olsun yoksa aşağıdaki işlemlerde iv değişiyor, bu da decrypt ederken sorun çıkarıyor.
        byte[] cipher = new byte[plainTextByte.length];                                                // plaintext boyutu kadar boyutu olan ciphertext oluştur.

        for (int i=0; i<plainTextByte.length; i += 16){                                               // 16 byte 16 byte işlem yap, "i" plaintextteki starting position
            System.arraycopy(plainTextByte, i, temp, 0, 16);                                  // plaintext'in 16 byte'ını al temp'e at
            System.arraycopy(ecb.encryptAES(xorla(tempIV,temp),secretKey), 0, cipher, i, 16); // temp'i encrypt et ve cipherText'in "i" ninci pozisyonundan i+16 ıncı pozisyonu arasındaki boşluklara ekle.
            System.arraycopy(cipher,i,tempIV,0,16);                                          // yeni iv, bir önceki şifrelenmiş blok olsun.

        }

        return cipher;

    }


    public byte[] decrypt(byte[] cipherTextByte, SecretKey secretKey, ECB ecb, byte[] iv) throws NoSuchAlgorithmException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException {


        byte[] temp = new byte[16];
        byte[] tempIV = iv.clone();
        byte[] plainText = new byte[cipherTextByte.length];

        for (int i=0; i<cipherTextByte.length; i += 16){                                                    // 16 byte 16 byte işlem yap, i plaintextteki starting position
            System.arraycopy(cipherTextByte, i, temp, 0, 16);                                       // encrypt methodundaki açıklamalara benzer açıklamalar olacak. İkinci defa yazmadım.
            System.arraycopy(xorla(tempIV,ecb.decryptAES(temp,secretKey)), 0, plainText, i, 16);
            System.arraycopy(cipherTextByte,i,tempIV,0,16);

        }

        return plainText;

    }

}