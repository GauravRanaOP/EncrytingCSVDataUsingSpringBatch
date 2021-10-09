package com.example.demo.config;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

public class AesEncryptDecrypt {
	private static final String key = "GauravRana$12345";
	private static final String initVector = "0001000100010001"; //16 bytes IV
	public static String encrypt(String value) {
	    try {
	        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	        byte[] encrypted = cipher.doFinal(value.getBytes());
	        return Base64.encodeBase64String(encrypted);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	public static String decrypt(String encrypted) {
		    try {
		        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
		        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		        byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
		        return new String(original);
		    } catch (Exception ex) {
		        try {
		        	 IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
				        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
				        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
				        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
				        byte[] original = cipher.doFinal(Base64.decodeBase64("+"+encrypted));
				        return new String(original);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    return null;
	}	
	
	public static void main(String[] args) {
		System.out.println("decrypting" + decrypt("Xzq6ZMz/h34Ymy2r67zNSA=="));
	}
	
	 public static String encode(String url)  
     {  
               try {  
                    String encodeURL=URLEncoder.encode( url, "UTF-8" );  
                    return encodeURL;  
               } catch (UnsupportedEncodingException e) {  
                    return "Issue while encoding" +e.getMessage();  
               }  
     }
	 public static String decode(String url)  
     {  
               try {  
                    String prevURL="";  
                    String decodeURL=url;  
                    while(!prevURL.equals(decodeURL))  
                    {  
                         prevURL=decodeURL;  
                         decodeURL=URLDecoder.decode( decodeURL, "UTF-8" );  
                    }  
                    return decodeURL;  
               } catch (UnsupportedEncodingException e) {  
                    return "Issue while decoding" +e.getMessage();  
               }  
     }
}
