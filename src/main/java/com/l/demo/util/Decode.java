package com.l.demo.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.zip.ZipInputStream;

/**
 * 解密程序
 */
public class Decode {
    /**
     * @param key         解密的密码
     * @param unZipPath   要解密的压缩文件路径
     * @param outFilePath 解密解压缩之后的文件路径
     * @throws Exception
     */
    public static void decryptZip(String key, String unZipPath, String outFilePath) throws Exception {
        InputStream zipInputStream = new FileInputStream(new File(unZipPath));
        SecretKey secretKeySpec = new SecretKeySpec(key.getBytes(), "DESede");
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        CipherInputStream cipherInputStream = new CipherInputStream(zipInputStream, cipher);
        ZipInputStream decryptZipInputStream = new ZipInputStream(cipherInputStream);
        if (decryptZipInputStream.getNextEntry() == null) {
            System.out.println("解密失败");
            return;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(new File(outFilePath));

        BufferedInputStream bis = new BufferedInputStream(decryptZipInputStream);
        BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = bis.read(bytes)) != -1) {
            bos.write(bytes, 0, len);
        }
        bos.flush();

        bos.close();
        bis.close();
        fileOutputStream.close();
        decryptZipInputStream.close();
        cipherInputStream.close();
        zipInputStream.close();

        System.out.println("解密成功");
    }


    public static void main(String[] args) throws Exception {
        System.out.println("请输入需要解密的文件地址：");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String filePath = br.readLine().trim();
        System.out.println("请输入24位加密密钥：");
        br = new BufferedReader(new InputStreamReader(System.in));
        String key = br.readLine().trim();
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("文件不存在");
            return;
        }
        if (isEmpty(key)) {
            System.out.println("密钥不能为空");
            return;
        }
        String zipFile = filePath;
        String fileName = file.getName();
        String filesPath = file.getPath();
        String unZipOutPath = filesPath.replace(fileName, "") + "tmp_decode_" + fileName;
        //解密
        decryptZip(key, filePath, unZipOutPath);

        //解密方法
        //加密文件
         File file2 = new File(unZipOutPath);
        //解密文件
        String dePath = filesPath.replace(fileName, "") + "decode_" + fileName;
        File file3 = new File(dePath);
        DecFile(file2, file3);
        file2.delete();
    }

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }


    //解密方法
    public static void DecFile(File srcFile, File tarFile) {
        Base64 base64 = new Base64();
        InputStream is = null;
        OutputStream os = null;
        //源加密文件
        File file1 = srcFile;
        //解密文件
        File file2 = tarFile;
        try {
            is = new FileInputStream(file1);
            os = new FileOutputStream(file2);
            byte[] tempbytes = new byte[is.available()];
            is.read(tempbytes);
            os.write(base64.decode(tempbytes));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
                if (null != os) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
