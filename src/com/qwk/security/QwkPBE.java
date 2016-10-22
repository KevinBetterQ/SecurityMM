package com.qwk.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class QwkPBE {

	static byte[] salt = {1,2,3,4,5,6,7,8};
	
	
	//����
	public static String encryptPBE(String src, String password){
		byte[] result = null;
		try {
			//��������Կ
			PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
			Key key = factory.generateSecret(pbeKeySpec);
			
			//����
			PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
			Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
			cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
			result = cipher.doFinal(src.getBytes());
	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Base64.encodeBase64String(result);//����Base64��byte����ת��Ϊ�����ʽ�ַ���
		
	}
	
	//����
	public static String dectyptPBE(String src, String password){
		byte[] result = null;
		try {
			//��������Կ
			PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
			Key key = factory.generateSecret(pbeKeySpec);
			
			//����
			PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
			Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
			cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
			result = cipher.doFinal(Base64.decodeBase64(src)); //����Base64�������ʽ�ַ���ת��Ϊbyte����
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new String(result);
	}
	

	/*public static void jdkPBE() {
		try {
			//��ʼ����
			SecureRandom random = new SecureRandom();
			byte[] salt = random.generateSeed(8);
			
			
			//��������Կ
			String password = "imooc";
			PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
			Key key = factory.generateSecret(pbeKeySpec);
			
			//����
			PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
			Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
			cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("jdk pbe encrypt : " + Base64.encodeBase64String(result));
			
			
			//����
			cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
			result = cipher.doFinal(result);
			System.out.println("jdk pbe decrypt : " + new String(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
