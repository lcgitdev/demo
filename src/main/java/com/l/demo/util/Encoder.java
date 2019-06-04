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
public class Encoder {
    public static void main(String[] args) throws IOException {
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
        String fileName = file.getName();
        String filesPath = file.getPath();
        String encodeFilePath = filesPath.replace(fileName, "") + "encode_" + fileName;
        encode(filesPath, encodeFilePath, key);
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
                    inArray[i] = encode3Des(key, inArray[i]);
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
     * 3DES加密
     *
     * @param key    密钥(可自定义)，24位
     * @param srcStr 将加密的字符串
     * @return 返回加密后的字符串
     */
    public static String encode3Des(String key, String srcStr) {
        byte[] keybyte = hex(key);
        byte[] src = srcStr.getBytes();
        try {
            //生成密钥 DESede代表3DES
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
            //加密
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            String pwd = Base64.encodeBase64String(c1.doFinal(src));
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
