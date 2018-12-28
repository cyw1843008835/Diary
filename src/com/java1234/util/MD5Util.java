package com.java1234.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import java.util.Base64.Encoder;
/*
 * MD5º”√‹
* @author chenyanwei
* @date 2 Dec 2018 11:03:14
* @version 1.0
*/
public class MD5Util {
	
	public static String EncoderPwdByMd5(String str) throws NoSuchAlgorithmException {

		MessageDigest md5=MessageDigest.getInstance("MD5");
		Encoder encoder = Base64.getEncoder();
		String result = encoder.encodeToString(md5.digest());
		return result;
	
		

}
	
	public static void main(String[] args) {
	try {
		System.out.println(MD5Util.EncoderPwdByMd5("1234"));
	} catch (NoSuchAlgorithmException e) {
		System.out.println("error");
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}

