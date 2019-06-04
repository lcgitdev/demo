package com.l.demo.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by luocheng on 2019/5/28.
 */
public class DESUtils {
    private final static String KEY_ALGORITHM = "DES";
    private final static String CIPER_ALGORITHM = "DES";

    public static void encrypt(File inFile, String outFilePath, String outFileName, String key){
        FileInputStream fis = null;
        FileOutputStream fos = null;

        if(! inFile.exists()){
            System.out.println("要加密的文件不存在");
            return;
        }

        File dir = new File(outFilePath);
        if(!dir.exists()){
            dir.mkdir();
        }

        File outFile = new File(dir, outFileName);

        byte[] buffer = new byte[1024*10];
        int length;

        SecureRandom random = new SecureRandom();

        try {
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
            SecretKey secretKey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(CIPER_ALGORITHM);
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);

            fis = new FileInputStream(inFile);
            fos = new FileOutputStream(outFile);

            //开始加密操作
            while((length = fis.read(buffer)) != -1){
                buffer = Arrays.copyOf(buffer, length);
                System.out.println(buffer.length);
                byte[] byteArray = cipher.doFinal(buffer);
                byte[] base64Array = Base64.encodeBase64(byteArray);
                fos.write(base64Array, 0, base64Array.length);
            }

        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static void decrypt(File inFile, String outFilePath, String outFileName, String key){
        FileInputStream fis = null;
        FileOutputStream fos = null;

        if(! inFile.exists()){
            System.out.println("要解密的文件不存在");
            return;
        }

        File dir = new File(outFilePath);
        if(!dir.exists()){
            dir.mkdir();
        }

        File outFile = new File(dir, outFileName);

        byte[] buffer = new byte[1024*10];
        int length;

        SecureRandom random = new SecureRandom();

        try {
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
            SecretKey secretKey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(CIPER_ALGORITHM);
            //用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secretKey, random);

            fis = new FileInputStream(inFile);
            fos = new FileOutputStream(outFile);

            //开始解密操作
            while((length = fis.read(buffer)) != -1){
                buffer = Arrays.copyOf(buffer, length);
                System.out.println(buffer.length);
                byte[] base64Array = Base64.decodeBase64(buffer);
                byte[] byteArray = cipher.doFinal(base64Array);
                fos.write(byteArray, 0, byteArray.length);
            }

        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        DESUtils des = new DESUtils();
        File file = new File("D:\\MyTs.txt");
        String filePath = "D:\\";
        String key = "12345678";
        DESUtils.encrypt(file, "D:\\", "MyTsNOT.txt", key);
        DESUtils.decrypt(new File("D:\\MyTsNOT.txt"), "D:\\", "MyTsYes.txt", key);
    }
}
