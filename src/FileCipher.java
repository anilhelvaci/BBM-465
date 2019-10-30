
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class FileCipher {

    public static final String ENCRYPT = "-e";
    public static final String DECRYPTION = "-d";
    public static final String CBC = "CBC";
    public static final String OFB = "OFB";
    public static final String CTR = "CTR";

    private String operation;
    private String inputFilePath;
    private String outputFilePath;
    private String algorithm;
    private String mode;
    private String keyFilePath;
    private int blockSize;

    public FileCipher(String[] args) {
        operation = args[0];
        inputFilePath = args[2];
        outputFilePath = args[4];
        algorithm = args[5];
        mode = args[6];
        keyFilePath = args[7];
        blockSize = algorithmBlockSize(algorithm);
    }

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

    private void writeFile(String fileName, byte[] data) {
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            out.write(data);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, FileNotFoundException {

        FileCipher fileCipher = new FileCipher(args);
        ReadFile readFile = new ReadFile(fileCipher.keyFilePath, fileCipher.inputFilePath, fileCipher.algorithm);

        ECB ecb = new ECB();
        CBC cbc = new CBC(fileCipher.blockSize);
        OFB ofb = new OFB(fileCipher.blockSize);
        CTR ctr = new CTR(fileCipher.blockSize);

        if (fileCipher.operation.equals(FileCipher.ENCRYPT) && fileCipher.mode.equals(FileCipher.CBC)) {
            //Todo CBC ENCODE HERE
            System.out.println("Inside CBC Encode");
            fileCipher.writeFile(fileCipher.outputFilePath,
                    cbc.encrypt(readFile.getPaddedText(), readFile.getKey(), ecb, readFile.getInitVec(), fileCipher.algorithm));
        } else if (fileCipher.operation.equals(FileCipher.ENCRYPT) && fileCipher.mode.equals(FileCipher.OFB)) {
            //Todo OFB ENCODE HERE
            fileCipher.writeFile(fileCipher.outputFilePath,
                    ofb.encrypt(readFile.getPaddedText(), readFile.getKey(), ecb, readFile.getInitVec(), fileCipher.algorithm));
        } else if (fileCipher.operation.equals(FileCipher.ENCRYPT) && fileCipher.mode.equals(FileCipher.CTR)) {
            //Todo CTR ENCODE HERE
            fileCipher.writeFile(fileCipher.outputFilePath,
                    ctr.encrypt(readFile.getPaddedText(), readFile.getKey(), ecb, readFile.getNonce(), fileCipher.algorithm));
        } else if (fileCipher.operation.equals(FileCipher.DECRYPTION) && fileCipher.mode.equals(FileCipher.CBC)) {
            //Todo CBC DECODE HERE
            System.out.println("Inside CBC Decode");
            fileCipher.writeFile(fileCipher.outputFilePath,
                    readFile.deletePadding(cbc.decrypt(readFile.getText(), readFile.getKey(), ecb, readFile.getInitVec(), fileCipher.algorithm)));
        } else if (fileCipher.operation.equals(FileCipher.DECRYPTION) && fileCipher.mode.equals(FileCipher.OFB)) {
            //Todo OFB DECODE HERE
            fileCipher.writeFile(fileCipher.outputFilePath,
                    readFile.deletePadding(ofb.decrypt(readFile.getText(), readFile.getKey(), ecb, readFile.getInitVec(), fileCipher.algorithm)));
        } else if (fileCipher.operation.equals(FileCipher.DECRYPTION) && fileCipher.mode.equals(FileCipher.CTR)) {
            //Todo CTR DECODE HERE
            fileCipher.writeFile(fileCipher.outputFilePath,
                    readFile.deletePadding(ctr.decrypt(readFile.getText(), readFile.getKey(), ecb, readFile.getNonce(), fileCipher.algorithm)));
        }
    }

}
