import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException {


        // Key üretimi şimdilik böyle olacak. Example txt dosyaları geldiğinde değişecek.

        //AES key generation
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();

        //DES key generation
        KeyGenerator keyGen1 = KeyGenerator.getInstance("DES");
        keyGen1.init(56);
        SecretKey secretKey1 = keyGen1.generateKey();

        Random rd = new Random(); // creating Random object
        byte[] iv = new byte[16];
        for (int i = 0; i < iv.length; i++) {
            rd.nextBytes(iv); // storing random integers in an array
        }

        String nonce = "1234";



        // TODO plaintext boyutu init vektörün boyundan küçükse, plaintext extend edilecek.

        String plainText = "1234567812345678abcdefghabcdefghzxcvbnmkzxcvbnm1";
        byte[] plainTextByte = plainText.getBytes(StandardCharsets.UTF_8);


        ECB ecb = new ECB();
        CBC cbc = new CBC();

          // @test ECB

//        // AES encrypt and decrypt (ECB)
//
//
//        System.out.println("gönderilecek mesaj->                            "+plainText);
//        System.out.println("şifreli mesaj->                                 "+new String(ecb.encryptAES(plainTextByte,secretKey)));
//        System.out.println("şifreli mesajı açınca görülen->                 "+new String(ecb.decryptAES(ecb.encryptAES(plainTextByte,secretKey),secretKey)));
//
//
//        // DES encrypt and decrypt (ECB)
//
//
//        System.out.println("ECB gönderilecek mesaj->                            "+plainText);
//        System.out.println("ECB şifreli mesaj->                                 "+new String(ecb.encryptDES(plainTextByte,secretKey1)));
//        System.out.println("ECB şifreli mesajı açınca görülen->                 "+new String(ecb.decryptDES(ecb.encryptDES(plainTextByte,secretKey1),secretKey1)));






        // @test CBC
        // TODO DES
        // AES encrypt and decrypt

//        System.out.println("CBC gönderilecek mesaj->               "+new String(plainTextByte));
//        byte[] sifrelenmisCBC = cbc.encrypt(plainTextByte, secretKey, ecb, iv);
//        System.out.println("CBC şifrelenmiş mesaj->                "+new String(sifrelenmisCBC));
//        System.out.println("CBC şifreli mesajı açınca görülen->    "+new String(cbc.decrypt(sifrelenmisCBC,secretKey,ecb,iv)));
//



        // @test OFB
        // TODO DES
        // OFB encrypt and decrypt

//        OFB ofb = new OFB();
//        byte[] sifrelenmisOFB = ofb.encrypt(plainTextByte,secretKey,ecb,iv);
//        System.out.println("OFB gönderilecek mesaj                     "+new String(plainTextByte));
//        System.out.println("OFB şifrelenmiş mesaj->                    "+new String(sifrelenmisOFB));
//        System.out.println("OFB şifreli mesajı açınca görülen->        "+new String(ofb.decrypt(sifrelenmisOFB,secretKey,ecb,iv)));



        //CTR ctr = new CTR();



    }





}
