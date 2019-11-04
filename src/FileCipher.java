
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class FileCipher {

    private static final String RUN_LOG_FILE = "run.log";

    private static final String ENCRYPT = "-e";
    private static final String DECRYPTION = "-d";
    private static final String CBC = "CBC";
    private static final String OFB = "OFB";
    private static final String CTR = "CTR";

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

    private static int algorithmBlockSize(String algorithm){
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

    private void appendStrToFile(String str) {
        try {
            // Open given file in append mode.
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(RUN_LOG_FILE, true));
            out.write(str);
            out.close();
        } catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }

    private void fillRunLogFile(int timeTaken) {
        File logFile = new File(RUN_LOG_FILE);
        int index = inputFilePath.split("/").length - 1;
        String text = inputFilePath.split("/")[index] + " " + outputFilePath + " " + setUpDisplayFormat(operation) + " " + algorithm + " " + mode + " " + timeTaken + "\n";

        if (logFile.length() != 0) {
            appendStrToFile(text);
        } else {
            try {
                BufferedWriter out = new BufferedWriter(
                        new FileWriter(RUN_LOG_FILE));
                out.write(text);
                out.close();
            } catch (IOException e) {
                System.out.println("Exception Occurred" + e);
            }
        }
    }

    private String setUpDisplayFormat(String originalOperation) {
        switch (originalOperation) {
            case ENCRYPT:
                return "enc";
                
            case DECRYPTION:
                return "dec";

            default:
                return "No mode found.";
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, FileNotFoundException {

        FileCipher fileCipher = new FileCipher(args);
        ReadFile readFile = new ReadFile(fileCipher.keyFilePath, fileCipher.inputFilePath, fileCipher.algorithm, fileCipher.blockSize);

        ECB ecb = new ECB();
        CBC cbc = new CBC(fileCipher.blockSize);
        OFB ofb = new OFB(fileCipher.blockSize);
        CTR ctr = new CTR(fileCipher.blockSize);

        long startTime = -1;

        if (fileCipher.operation.equals(FileCipher.ENCRYPT) && fileCipher.mode.equals(FileCipher.CBC)) {
            //Todo CBC ENCODE HERE
            startTime = System.currentTimeMillis();
            fileCipher.writeFile(fileCipher.outputFilePath,
                    cbc.encrypt(readFile.getPaddedText(), readFile.getKey(), ecb, readFile.getInitVec(), fileCipher.algorithm));
        } else if (fileCipher.operation.equals(FileCipher.ENCRYPT) && fileCipher.mode.equals(FileCipher.OFB)) {
            //Todo OFB ENCODE HERE
            startTime = System.currentTimeMillis();
            fileCipher.writeFile(fileCipher.outputFilePath,
                    ofb.encrypt(readFile.getPaddedText(), readFile.getKey(), ecb, readFile.getInitVec(), fileCipher.algorithm));
        } else if (fileCipher.operation.equals(FileCipher.ENCRYPT) && fileCipher.mode.equals(FileCipher.CTR)) {
            //Todo CTR ENCODE HERE
            startTime = System.currentTimeMillis();
            fileCipher.writeFile(fileCipher.outputFilePath,
                    ctr.encrypt(readFile.getPaddedText(), readFile.getKey(), ecb, readFile.getNonce(), fileCipher.algorithm));
        } else if (fileCipher.operation.equals(FileCipher.DECRYPTION) && fileCipher.mode.equals(FileCipher.CBC)) {
            //Todo CBC DECODE HERE
            startTime = System.currentTimeMillis();
            fileCipher.writeFile(fileCipher.outputFilePath,
                    readFile.deletePadding(cbc.decrypt(readFile.getText(), readFile.getKey(), ecb, readFile.getInitVec(), fileCipher.algorithm)));
        } else if (fileCipher.operation.equals(FileCipher.DECRYPTION) && fileCipher.mode.equals(FileCipher.OFB)) {
            //Todo OFB DECODE HERE
            startTime = System.currentTimeMillis();
            fileCipher.writeFile(fileCipher.outputFilePath,
                    readFile.deletePadding(ofb.decrypt(readFile.getText(), readFile.getKey(), ecb, readFile.getInitVec(), fileCipher.algorithm)));
        } else if (fileCipher.operation.equals(FileCipher.DECRYPTION) && fileCipher.mode.equals(FileCipher.CTR)) {
            //Todo CTR DECODE HERE
            startTime = System.currentTimeMillis();
            fileCipher.writeFile(fileCipher.outputFilePath,
                    readFile.deletePadding(ctr.decrypt(readFile.getText(), readFile.getKey(), ecb, readFile.getNonce(), fileCipher.algorithm)));
        }

        fileCipher.fillRunLogFile((int) (System.currentTimeMillis() - startTime));
    }

}
