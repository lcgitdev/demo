package com.l.demo.util;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by luocheng on 2019/5/30.
 */
public class Decoder {
    public static void main(String[] args) throws IOException {
        System.out.println("请输入需要解密的文件地址：");
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
        String fileName = file.getName();
        String filesPath = file.getPath();
        String decodeFilePath = filesPath.replace(fileName, "") + "decode_" + fileName;
        encode(filesPath, decodeFilePath, key);
    }

    public static void encode(String oldPath, String newPath, String key) {
        String inString = "";
        String[] inArray = null;
        try {
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(oldPath, ',', Charset.forName("GBK"));
            // 创建CSV写对象
            CsvWriter csvWriter = new CsvWriter(newPath, ',', Charset.forName("GBK"));
            while (csvReader.readRecord()) {
                // 读一整行
                inString = csvReader.getRawRecord();
                inArray = inString.split(",");
                for (int i = 0; i < inArray.length; i++) {
                    inArray[i] = decode3Des(key, inArray[i]);
                }
                // 读这行的某一列
                csvWriter.writeRecord(inArray, true);
                csvWriter.flush();//刷新数据
            }
            csvReader.close();
            csvWriter.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    /**
     * 转换成十六进制字符串
     *
     * @return 秘钥字符数组
     */
    public static byte[] hex(String key) {
        //MD5将秘钥加密
        String f = DigestUtils.md5Hex(key);
        byte[] bkeys = new String(f).getBytes();
        byte[] enk = new byte[24];
        for (int i = 0; i < 24; i++) {
            enk[i] = bkeys[i];
        }
        return enk;
    }

    /**
     * 3DES解密
     *
     * @param key    加密密钥(可自定义)，长度为24字节
     * @param desStr 加密后的字符串
     * @return 返回解密后的数据
     */
    public static String decode3Des(String key, String desStr) {
        Base64 base64 = new Base64();
        byte[] keybyte = hex(key);
        byte[] src = base64.decode(desStr);
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
            //解密
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(Cipher.DECRYPT_MODE, deskey);
            String pwd = new String(c1.doFinal(src));
            return pwd;
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
}
