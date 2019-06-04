package com.l.demo.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 加密程序
 */
public class Encode {
    /**
     * @param key         加密的密码
     * @param zipOutPath  压缩文件输出的路径
     * @param zipFilePath 要压缩的文件
     * @throws Exception
     */
    public static void encryptZip(String key, String zipOutPath, String zipFilePath) throws Exception {


        OutputStream zipOutputPathStream = new FileOutputStream(new File(zipOutPath));
        SecretKey secretKeySpec = new SecretKeySpec(key.getBytes(), "DESede");
        Cipher instance = Cipher.getInstance("DESede");
        instance.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        OutputStream cipherOutputStream = new CipherOutputStream(zipOutputPathStream, instance);
        ZipOutputStream zipOutputStream = new ZipOutputStream(cipherOutputStream);
        zipOutputStream.putNextEntry(new ZipEntry(zipFilePath));

        InputStream fileInputStream = new FileInputStream(new File(zipFilePath));

        BufferedInputStream bis = new BufferedInputStream(fileInputStream);
        BufferedOutputStream bos = new BufferedOutputStream(zipOutputStream);

        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = bis.read(bytes)) != -1) {
            bos.write(bytes, 0, len
            );
        }
        bos.flush();

        bos.close();
        bis.close();
        fileInputStream.close();
        zipOutputStream.close();
        cipherOutputStream.close();
        zipOutputPathStream.close();

        System.out.println("加密成功");

    }

    public static void main(String[] args) throws Exception {
        System.out.println("请输入需要加密文件地址：");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String filePath = br.readLine();
        System.out.println("请输入24位加密密钥：");
        br = new BufferedReader(new InputStreamReader(System.in));
        String key = br.readLine();

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
        String zipPath = filesPath.replace(fileName, "") + "encode_" + fileName;

        //将文件通过base64加密
        String tmpPath = filesPath.replace(fileName, "") + "tmp_encode_" + fileName;

        File file1 = new File(filePath);
        File file2 = new File(tmpPath);
        //base64加密
        EnFile(file1, file2);
        //3des加密
        encryptZip(key, zipPath, tmpPath);
        //删除临时文件
        file2.delete();
    }

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    //加密方法
    public static void EnFile(File srcFile, File tarFile) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        InputStream is = null;
        OutputStream os = null;
                //源文件
        File file1 = srcFile;
        //加密文件
        File file2 = tarFile;
        try {
            is = new FileInputStream(file1);
            os = new FileOutputStream(file2);

            byte[] tempbytes = new byte[is.available()];
            is.read(tempbytes);
            os.write(Base64.encodeBase64(tempbytes));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(null != is){
                    is.close();
                }
                if(null != os){
                    os.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
