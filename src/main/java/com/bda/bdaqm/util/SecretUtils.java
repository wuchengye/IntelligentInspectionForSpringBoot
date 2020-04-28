package com.bda.bdaqm.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class SecretUtils {
	//定义加密算法，有DES、DESede(即3DES)、Blowfish
    private static final String Algorithm = "DESede";    
//    private static final String PASSWORD_CRYPT_KEY = "123456789012345678901234";
//    private static final String PASSWORD_CRYPT_KEY = "821456489032345678401334";
    //private static final String PASSWORD_CRYPT_KEY = PropertyMgr.getPropertyByKey(PropertyMgr.EMAIL_CONFIG_PROP, PropertyMgr.PASSWORD_CRYPT_KEY);
    /**
     * 加密方法
     * @param src 源数据的字节数组
     * @return 
     */
    public static byte[] encryptMode(byte[] src) {
        /*try {
             SecretKey deskey = new SecretKeySpec(build3DesKey(PASSWORD_CRYPT_KEY), Algorithm);    //生成密钥
             Cipher c1 = Cipher.getInstance(Algorithm);    //实例化负责加密/解密的Cipher工具类
             c1.init(Cipher.ENCRYPT_MODE, deskey);    //初始化为加密模式
             return c1.doFinal(src);
         } catch (java.security.NoSuchAlgorithmException e1) {
             e1.printStackTrace();
         } catch (javax.crypto.NoSuchPaddingException e2) {
             e2.printStackTrace();
         } catch (java.lang.Exception e3) {
             e3.printStackTrace();
         }*/
         return null;
     }
    
    
    /**
     * 解密函数
     * @param src 密文的字节数组
     * @return
     */
    public static byte[] decryptMode(byte[] src) {      
        /*try {
            SecretKey deskey = new SecretKeySpec(build3DesKey(PASSWORD_CRYPT_KEY), Algorithm);
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);    //初始化为解密模式
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }*/
        return null;
     }
    
    
    /*
     * 根据字符串生成密钥字节数组 
     * @param keyStr 密钥字符串
     * @return 
     * @throws UnsupportedEncodingException
     */
    public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException{
        byte[] key = new byte[24];    //声明一个24位的字节数组，默认里面都是0
        byte[] temp = keyStr.getBytes("UTF-8");    //将字符串转成字节数组
        
        /*
         * 执行数组拷贝
         * System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
         */
        if(key.length > temp.length){
            //如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
        }else{
            //如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    } 
    
    
    //getMd5  Start
    protected static MessageDigest messageDigest = null;
    protected static char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};  
	  static{  
	        try{  
	            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
	            messageDigest = MessageDigest.getInstance("MD5");  
	        }catch (NoSuchAlgorithmException e) {  
	            e.printStackTrace();  
	        }  
	    }
    public static String stringMD5(String input) {
        // 输入的字符串转换成字节数组
       byte[] inputByteArray = input.getBytes();
       // inputByteArray是输入字符串转换得到的字节数组
       messageDigest.update(inputByteArray);
       // 转换并返回结果，也是字节数组，包含16个元素
       byte[] resultByteArray = messageDigest.digest();
       // 字符数组转换成字符串返回
       return bufferToHex(resultByteArray);
  }
    private static String bufferToHex(byte bytes[]) {  
        return bufferToHex(bytes, 0, bytes.length);  
    }
    private static String bufferToHex(byte bytes[], int m, int n) {  
        StringBuffer stringbuffer = new StringBuffer(2 * n);  
        int k = m + n;  
        for (int l = m; l < k; l++) {  
         appendHexPair(bytes[l], stringbuffer);  
        }  
        return stringbuffer.toString();  
     } 
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {  
        
        char c0 = hexDigits[(bt & 0xf0) >> 4];  
        char c1 = hexDigits[bt & 0xf];  
        stringbuffer.append(c0);  
        stringbuffer.append(c1);  
     } 
    //getMd5  end
    
    /** 
     * 返回一个定长的随机十进制数字字符串
     */  
    public static String generateString(int length) {  
        StringBuffer sb = new StringBuffer();  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            sb.append(random.nextInt(10)+"");  
        }  
        return sb.toString();  
    }  
}
