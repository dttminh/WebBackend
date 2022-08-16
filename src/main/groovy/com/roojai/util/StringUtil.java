package com.roojai.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class StringUtil {

	static Logger logger = LoggerFactory.getLogger("com.roojai.util.StringUtil");

	public static void main(String[] args) {
		String jsonString = "{\"glossary\":{\"title\": \"example glossary\", \"GlossDiv\":{\"title\": \"S\", \"GlossList\":{\"GlossEntry\":{\"ID\": \"SGML\", \"SortAs\": \"SGML\", \"GlossTerm\": \"Standard Generalized Markup Language\", \"Acronym\": \"SGML\", \"Abbrev\": \"ISO 8879:1986\", \"GlossDef\":{\"para\": \"A meta-markup language, used to create markup languages such as DocBook.\", \"GlossSeeAlso\": [\"GML\", \"XML\"]}, \"GlossSee\": \"markup\"}}}}}";
		JsonParser parser = new JsonParser();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonElement el = parser.parse(jsonString);
		jsonString = gson.toJson(el); // done
		System.out.println(jsonString);
	}

	public static String fomattXml()throws Exception{
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		//initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		/*DOMSource source = new DOMSource(doc);
		transformer.transform(source, result);
		String xmlString = result.getWriter().toString();
		System.out.println(xmlString);*/
		return "";
	}

	public static String getIpAdress(HttpServletRequest request){
		String trueClientIp = request.getHeader("True-Client-IP");
		if( trueClientIp==null || trueClientIp.equals("") ){
			trueClientIp = request.getRemoteAddr();
		}
		return trueClientIp;
	}
	
	public static String decrypt(String qid){
		try{
			String IV = "0000000000000000";
			byte[] key = "1523981214780853".getBytes("UTF-8");
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, secretKey,ivParameterSpec);
            String de = new String(cipher.doFinal(Base64.decodeBase64(qid.getBytes("UTF-8"))));
            return de;
		}catch(IllegalBlockSizeException e){
			return "";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "";
		}
	}

	public static String encrypt(String str){
		try {
			String IV = "0000000000000000";
			byte[] key = "1523981214780853".getBytes("UTF-8");
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes("UTF-8"));//Syntel chanaged for PRIM_THAI-734 on 23 Mar 2016 - Snehal
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,ivParameterSpec);
            //String de = new String(cipher.doFinal(Base64.encodeBase64(str.getBytes("UTF-8"))));
            byte[] encrypted = cipher.doFinal(str.getBytes());

            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
			logger.error(e.getMessage(), e);
            return "";
        }
	}

}
