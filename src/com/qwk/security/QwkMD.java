package com.qwk.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD4Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.print.resources.serviceui;

public class QwkMD {
	
	public static String jdkMD5(String src){
		byte[] md5Bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md5Bytes = md.digest(src.getBytes());
			//把byte数组转换为字符串
			//System.out.println("jdkmd5 : " + Hex.encodeHexString(md5Bytes));//CC实现转换
			//System.out.println("jdkmd5String : " + new String(md5Bytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Hex.encodeHexString(md5Bytes);
	}
	

	/*private static String src = "qwk test code";
	public static void main(String[] args) {
		 jdkMD5();
		 jdkMD2();
		 bcMD4();
		 bcMD5();
		 ccMD5();
		 ccMD2();

	}

	public static void jdkMD5(){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] md5Bytes = md.digest(src.getBytes());
			//把byte数组转换为字符串
			System.out.println("jdkmd5 : " + Hex.encodeHexString(md5Bytes));//CC实现转换
			//System.out.println("jdkmd5String : " + new String(md5Bytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static void jdkMD2() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD2");
			byte[] md2Bytes = md.digest(src.getBytes());
			System.out.println("JDK MD2 : " + Hex.encodeHexString(md2Bytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static void bcMD5() {
		Digest digest = new MD5Digest();
		digest.update(src.getBytes(), 0, src.getBytes().length);
		byte[] md5Bytes = new byte[digest.getDigestSize()];
		digest.doFinal(md5Bytes, 0);
		System.out.println("BC MD5 : " + org.bouncycastle.util.encoders.Hex.toHexString(md5Bytes));
	}
	
	public static void bcMD4() {
		try {
			Security.addProvider(new BouncyCastleProvider());
			MessageDigest md = MessageDigest.getInstance("MD4");
			byte[] md5Bytes = md.digest(src.getBytes());
			System.out.println("BC MD4 : " + Hex.encodeHexString(md5Bytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static void ccMD5() {
		System.out.println("CC MD5 : " + DigestUtils.md5Hex(src.getBytes()));
	}
	
	public static void ccMD2() {
		System.out.println("CC MD2 : " + DigestUtils.md2Hex(src.getBytes()));
	}*/
}
