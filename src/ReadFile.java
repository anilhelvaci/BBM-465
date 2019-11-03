import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFile {
    private byte[] text;
    private byte[] paddedText;
    private byte[] initVec;
    private SecretKey key;
    private byte[] nonce;
    private int blockSize;
    private byte[] decodedKey;


    public ReadFile(String keyFilePath, String inputFilePath, String algorithm, int blockSize) {
        readInputFile(inputFilePath);
        this.blockSize = blockSize;
        paddedText = padding(text, blockSize);
        initVec = new byte[blockSize];
        decodedKey = new byte[blockSize];
        readKeyFile(keyFilePath, algorithm);
    }

    public String readFileAsString(String fileName) {
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public byte[] readByteFromFile(String fileName) {
        File file = new File(fileName);
        byte[] result = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            fileInputStream.read(result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void readKeyFile(String filePath, String algorithm) {
        String[] data = readFileAsString(filePath).split(" - ");
        switch (algorithm) {
            case "AES":
                initVec = data[0].getBytes(StandardCharsets.UTF_8);
                decodedKey = data[1].getBytes(StandardCharsets.UTF_8);
                key = new SecretKeySpec(decodedKey, algorithm);
                nonce = data[2].getBytes(StandardCharsets.UTF_8);
                break;

            case "DES":
                byte[] tempInitVec = data[0].getBytes(StandardCharsets.UTF_8);
                byte[] tempDecodedKey = data[1].getBytes(StandardCharsets.UTF_8);
                byte[] tempNonce = data[2].getBytes(StandardCharsets.UTF_8);
                nonce = new byte[blockSize / 2];
                System.arraycopy(tempInitVec, tempInitVec.length - 1 - blockSize, initVec, 0, blockSize);
                System.arraycopy(tempDecodedKey, tempDecodedKey.length - 1 - blockSize, decodedKey, 0, blockSize);
                System.arraycopy(tempNonce, tempNonce.length - 1 - blockSize / 2, nonce, 0, blockSize / 2);
                key = new SecretKeySpec(decodedKey, algorithm);
                break;
        }

    }

    public void readInputFile(String filePath) {
        text = readByteFromFile(filePath);
    }

    public byte[] getPaddedText() {
        return paddedText;
    }

    public static byte[] padding(byte[] plainTextBytes, int blockSize){

        float boy = (float) plainTextBytes.length/blockSize;
        int boyWithPadding = (int) Math.ceil(boy)*blockSize;


        byte[] plainTextBytesWithPadding = new byte[boyWithPadding];
        System.out.println("PlainTextBytes Size: " + plainTextBytes.length);
        System.out.println("BoyWithPadding: " + boyWithPadding);
        System.arraycopy(plainTextBytes,0,plainTextBytesWithPadding,0,plainTextBytes.length);


        byte paddedValue = 32;             //  Mesajın sonunda boşluk ekleme (16 byte ve katı olması için padding) (0x32 = whitespace ASCII kodu)

        for (int i = plainTextBytes.length; i<boyWithPadding; i++){

            plainTextBytesWithPadding[i] = paddedValue;

        }

        return plainTextBytesWithPadding;


    }

    public byte[] deletePadding(byte[] plainTextBytes){


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


    public byte[] getText() {
        return text;
    }

    public byte[] getInitVec() {
        return initVec;
    }

    public SecretKey getKey() {
        return key;
    }

    public byte[] getNonce() {
        return nonce;
    }
}
