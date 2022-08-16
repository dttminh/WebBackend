package com.roojai.util;

import java.util.Random;

public class OtpUtil {
	 private static char[] geek_Password(int len,int mode){
	        // A strong password has Cap_chars, Lower_chars,
	        // numeric value and symbols. So we are using all of
	        // them to generate our password
	        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
	        String numbers = "0123456789";
	        String symbols = "!@#$%^&*_=+-/.?<>)";
	        
	        String values ="";
	        if( mode==1 )
	        	values = Capital_chars + Small_chars;
	        else if( mode==2)
	            values = numbers;
	 
	        // Using random method
	        Random rndm_method = new Random();
	 
	        char[] password = new char[len];
	 
	        for (int i = 0; i < len; i++)
	        {
	            // Use of charAt() method : to get character value
	            // Use of nextInt() as it is scanning the value as int
	            password[i] =
	              values.charAt(rndm_method.nextInt(values.length()));
	 
	        }
	        return password;
	    }
	public static String generateRefNum(int len){
		return new String(geek_Password(len,1));
	}
	public static String generateOTP(int len){
		return new String(geek_Password(len,2));
	}
	public static void main(String[] args) {
		System.out.println(generateRefNum(6));
		System.out.println(generateOTP(4));
		
	}
}
