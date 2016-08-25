package com.qwk.security;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class QwkPBE {

	private static String src = "qwk test code";

	public static void main(String[] args) {
		jdkPBE();
	}
	
	public static void jdkPBE() {
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
	}
}
