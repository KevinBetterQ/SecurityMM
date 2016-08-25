package com.qwk.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class QwkSHA {
	
	public static String jdkSHA1(String src) {
		byte[] sha1byte = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			//md.update(src.getBytes());
			sha1byte = md.digest(src.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Hex.encodeHexString(sha1byte);
	}

	/*private static String src = "qwk test code";
	public static void main(String[] args) {
		jdkSHA1();
		 bcSHA1();
		 bcSHA224();
		 ccSHA1();

	}

	public static void jdkSHA1() {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			//md.update(src.getBytes());
			byte[] sha1byte = md.digest(src.getBytes());
			System.out.println("jdk sha-1 : " + Hex.encodeHexString(sha1byte));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public static void bcSHA1() {
		Digest digest = new SHA1Digest();
		digest.update(src.getBytes(), 0, src.getBytes().length);
		byte[] sha1Bytes = new byte[digest.getDigestSize()];
		digest.doFinal(sha1Bytes, 0);
		System.out.println("bc sha-1 : " + org.bouncycastle.util.encoders.Hex.toHexString(sha1Bytes));
	}
	
	public static void bcSHA224() {
		Digest digest = new SHA224Digest();
		digest.update(src.getBytes(), 0, src.getBytes().length);
		byte[] sha224Bytes = new byte[digest.getDigestSize()];
		digest.doFinal(sha224Bytes, 0);
		System.out.println("bc sha-224 : " + org.bouncycastle.util.encoders.Hex.toHexString(sha224Bytes));
	}
	
	
	
	public static void ccSHA1() {
		System.out.println("cc sha1 - 1 :" + DigestUtils.sha1Hex(src.getBytes()));
		System.out.println("cc sha1 - 2 :" + DigestUtils.sha1Hex(src));
	}*/
}
