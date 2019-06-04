package com.l.demo.util;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;

/**
 * @desc 3DES+BASE64加解密
 **/
public class DESEncryptUtil {

    /**
     * @desc 主程序调用
     **/
    public static void main(String[] args) {
        File file = new File("D:\\MyTs.xlsx");
        String fileName = file.getName();
        System.out.println(fileName);

        //秘钥  可自定义,加解密均需要传入指定秘钥
        String key = "XWbank";
        //需要加密的数据
        String idcard = "123456";
        //加密后的数据
        String encode = encode3Des(key, idcard);
        //解密后的数据
        String decode = decode3Des(key, encode);

        System.out.println("原数据：" + idcard);
        System.out.println("加密后的数据：" + encode);
        System.out.println("解密后的数据：" + decode);
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
