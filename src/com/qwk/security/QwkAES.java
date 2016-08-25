package com.qwk.security;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class QwkAES {
	
	
	public static String encryptAES(String src, String mm){
		byte[] result = null;
		//生成KEY
		byte[] keyBytes = mm.getBytes();
		
		//key转换
		Key key = new SecretKeySpec(keyBytes, "AES");
		
		//加密
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			result = cipher.doFinal(src.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return Base64.encodeBase64String(result); //利用Base64将byte数组转换为特殊格式字符串
		
		
	}
	
	public static String dectyptAES(String src, String mm){
		byte[] result = null;
		//生成KEY
		byte[] keyBytes = mm.getBytes();

		//key转换
		Key key = new SecretKeySpec(keyBytes, "AES");

		try {
			//解密
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			result = cipher.doFinal(Base64.decodeBase64(src)); //利用Base64将特殊格式字符串转换为byte数组
		} catch (Exception e) {
			e.printStackTrace();
		} 
		 return new String(result);	
		
	}
	

	/*private static String src = "qwk test code";

	public static void main(String[] args) {
		jdkAES();
	}
	
	public static void jdkAES() {
		try {
			//生成KEY
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();
			
			//key转换
			Key key = new SecretKeySpec(keyBytes, "AES");
			
			//加密
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("jdk aes encrypt : " + Base64.encodeBase64String(result));
			
			//解密
			cipher.init(Cipher.DECRYPT_MODE, key);
			result = cipher.doFinal(result);
			System.out.println("jdk aes desrypt : " + new String(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}
