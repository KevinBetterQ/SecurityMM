package com.qwk.security;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.Base64Encoder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class QwkBase64 {

	//private static String src = "qwk test code";
	
	//加密
	public static String jdkBase64En(String src) {
		//加密：
		BASE64Encoder encoder = new BASE64Encoder();
		String encode = encoder.encode(src.getBytes());
		//System.out.println(encode);
		return encode;
	}
	
	//解密
	public static String jdkBase64De(String encode) {
		//解密
		BASE64Decoder decoder = new BASE64Decoder();
		String decode = null;
		try {
			decode = new String(decoder.decodeBuffer(encode));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(decode);

		return decode;
	}
	
	//CC方法实现加解密例子
	public static void commonsCodesBase64(){
		String src = "qwk test code";
		//加密
		byte[] encodeBytes = Base64.encodeBase64(src.getBytes());
		System.out.println("encode : " + new String(encodeBytes));
		//解密
		byte[] decodeBytes = Base64.decodeBase64(encodeBytes);
		System.out.println("decode : " + new String(decodeBytes));
	}
	
}
