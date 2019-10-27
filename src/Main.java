
import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;


public class Main {

    public static byte[] xorla(byte[] iv, byte[] plainTextByte, String algorithm){                                                // Klasik xor.

        int blockSize = algorithmBlockSize(algorithm);
        byte[] xoredArray = new byte[blockSize];

        int i = 0;
        for (byte b : iv)
            xoredArray[i] = (byte) (b ^ plainTextByte[i++]);


        return xoredArray;
    }

    public static int algorithmBlockSize(String algorithm){
        int blockSize;                    // 8 for DES, 16 for AES (byte)
        if (algorithm.equals("DES")){
            blockSize = 8;
        }
        else if (algorithm.equals("AES")){
            blockSize = 16;
        }
        else {
            System.out.println("Algorithm does not found!");
            return 0;
        }

        return blockSize;

    }


    public static byte[] padding(byte[] plainTextBytes, String algorithm){

        int blockSize = algorithmBlockSize(algorithm);

        float boy = (float) plainTextBytes.length/blockSize;
        int boyWithPadding = (int) Math.ceil(boy)*blockSize;


        byte[] plainTextBytesWithPadding = new byte[boyWithPadding];
        System.arraycopy(plainTextBytes,0,plainTextBytesWithPadding,0,plainTextBytes.length);


        byte paddedValue = 32;             //  Mesajın sonunda boşluk ekleme (16 byte ve katı olması için padding) (0x32 = whitespace ASCII kodu)

        for (int i = plainTextBytes.length; i<boyWithPadding; i++){

            plainTextBytesWithPadding[i] = paddedValue;

        }

        return plainTextBytesWithPadding;


    }

    public static byte[] deletePadding(byte[] plainTextBytes){


        byte paddedValue = 32;
        int j = plainTextBytes.length;
        for (int i = plainTextBytes.length-1; i>=0; i--){
            byte[] temp = new byte[i+1];
            if (plainTextBytes[i] == paddedValue){
                j--;
            }
            else{
                System.arraycopy(plainTextBytes,0,temp,0,j);
                return temp;
            }

        }

        return "Only Whitespaces can not be send.".getBytes();

    }



    public static void main(String[] args) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {


        // Key üretimi şimdilik böyle olacak. Example txt dosyaları geldiğinde değişecek.

        //AES key generation
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();

        //DES key generation
        KeyGenerator keyGen1 = KeyGenerator.getInstance("DES");
        keyGen1.init(56);
        SecretKey secretKey1 = keyGen1.generateKey();




        String algorithm = args[3];                         // 3. argüman algoritmayı söyleyecek.
        int blockSize = algorithmBlockSize(algorithm);


        Random rd = new Random(); // creating Random object
        byte[] iv = new byte[blockSize];
        for (int i = 0; i < iv.length; i++) {
            rd.nextBytes(iv); // storing random integers in an array
        }

        String nonce = "1234";



        String plainText = "anılın aq";
        byte[] plainTextByte = plainText.getBytes(StandardCharsets.UTF_8);



        /*

        ÇOK ÖNEMLİ NOT

        Aşağıdaki denemelerde DES için "secretKey1", AES için "secretKey" parametresi kullanılmalıdır.
        Hocanın vereceği key algoritmayla uyumlu olaracağı için sadece 1 variabla tutulacaktır. Şu an için hoca input output paylaşmadı.
        Bu sebepten key'ler farklı variable'larda tutuluyor.

        */







        //@test ECB


//        ECB ecb = new ECB();
//        System.out.println("gönderilecek mesaj->                            "+plainText);
//        System.out.println("şifreli mesaj->                                 "+new String(ecb.encrypt(padding(plainTextByte,algorithm),secretKey,algorithm)));
//        System.out.println("şifreli mesajı açınca görülen->                 "+new String(ecb.decrypt(ecb.encrypt(padding(plainTextByte,algorithm),secretKey,algorithm),secretKey,algorithm)));




        // @test CBC

//        CBC cbc = new CBC();
//        System.out.println("CBC gönderilecek mesaj->               "+new String(plainTextByte));
//        byte[] sifrelenmisCBC = cbc.encrypt(padding(plainTextByte,algorithm), secretKey, ecb, iv,algorithm);
//        System.out.println("CBC şifrelenmiş mesaj->                "+new String(sifrelenmisCBC));
//        System.out.println("CBC şifreli mesajı açınca görülen->    "+new String(cbc.decrypt(sifrelenmisCBC,secretKey,ecb,iv,algorithm)));




        // @test OFB


//        OFB ofb = new OFB();
//        byte[] sifrelenmisOFB = ofb.encrypt(padding(plainTextByte,algorithm),secretKey,ecb,iv,algorithm);
//        System.out.println("OFB gönderilecek mesaj                     "+new String(plainTextByte));
//        System.out.println("OFB şifrelenmiş mesaj->                    "+new String(sifrelenmisOFB));
//        System.out.println("OFB şifreli mesajı açınca görülen->        "+new String(ofb.decrypt(sifrelenmisOFB,secretKey,ecb,iv,algorithm)));



        //@test CTR


//        CTR ctr = new CTR();
//        String cipherText = new String(ctr.encrypt(padding(plainTextByte,algorithm), secretKey, ecb, nonce,algorithm));
//        System.out.println(cipherText);
//        byte[] cipherTextByte = ctr.encrypt(padding(plainTextByte,algorithm), secretKey, ecb, nonce,algorithm);
//        String plainText1 = new String(deletePadding(ctr.decrypt(cipherTextByte,secretKey,ecb,nonce,algorithm)));
//        System.out.println(plainText1);


    }





}
