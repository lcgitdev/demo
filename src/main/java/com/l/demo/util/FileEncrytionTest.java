package com.l.demo.util;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by luocheng on 2019/5/28.
 */
public class FileEncrytionTest {
    //秘钥  可自定义,加解密均需要传入指定秘钥
    private static final String key = "XWbank";

    public static void main(String[] args) {
        //源文件
        File file1 = new File("D:\\a.xlsx");
        //加密文件
        File file2 = new File("D:\\aNot.xlsx");
        //解密文件
        File file3 = new File("D:\\aYes.xlsx");
        //加密方法
        EnFile(file1, file2);
        //解密方法
        DecFile(file2, file3);

    }


    //加密方法
    public static void EnFile(File srcFile, File tarFile) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        //源文件
        File file1 = srcFile;
        //加密文件
        File file2 = tarFile;
        try {
            InputStream is = new FileInputStream(file1);
            OutputStream os = new FileOutputStream(file2);
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(os);

          //  byte[] tempbytes = new byte[1024 * 10];

            byte[] tempbytes = new byte[is.available()];
            is.read(tempbytes);
            os.write(Base64.encodeBase64(tempbytes));
           /* int byteread = 0;
            while ((byteread = bis.read(tempbytes)) != -1) {
                bos.write(Base64.encodeBase64(tempbytes));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //解密方法
    public static void DecFile(File srcFile, File tarFile) {

        Base64 base64 = new Base64();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        //源加密文件
        File file1 = srcFile;
        //解密文件
        File file2 = tarFile;
        try {
            InputStream is = new FileInputStream(file1);
            OutputStream os = new FileOutputStream(file2);

            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(os);

            byte[] tempbytes = new byte[is.available()];
            is.read(tempbytes);
            os.write(base64.decode(tempbytes));

          /*  byte[] tempbytes = new byte[1024 * 10];
            int byteread = 0;
            while ((byteread = bis.read(tempbytes)) != -1) {
                bos.write(base64.decode(tempbytes));

            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
