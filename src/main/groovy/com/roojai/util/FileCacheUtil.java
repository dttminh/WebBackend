package com.roojai.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;

public class FileCacheUtil {
	public static final String cachePath = System.getProperty("catalina.base")+"/cache";//System.getProperty("user.home")
	static Logger logger = LoggerFactory.getLogger("com.roojai.util.FileCacheUtil");
	public synchronized static Object getCache(String key,int sec){
		String cacheFile = cachePath+"/"+key;
		if( key!=null ){
			try{
				File file = new File(cacheFile);
				Long now = (new Date()).getTime();
				if( file.exists() && now-file.lastModified()<=sec*1000 ){
					// desesialize
					InputStream streamIn = new FileInputStream(cacheFile);
				    ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
				    Object obj = objectinputstream.readObject();
				    objectinputstream.close();
				    return obj;
				}else
					return null;
			}catch(Exception e){
				logger.error(e.getMessage(), e);
				return null;
			}
		}else
			return null;
	}
	public synchronized static boolean putCache(String key,Object data){
		if( data!=null ){
			ObjectOutputStream oos = null;
			try{
				String cacheFile = cachePath+"/"+key;
				File file = new File(cacheFile);
				if( !file.exists() ){
					// create file
					file.getParentFile().mkdirs();
					//file.createNewFile();
				}
				// store data
				FileOutputStream fout = new FileOutputStream(cacheFile);
				oos = new ObjectOutputStream(fout);
				oos.writeObject(data);
				oos.close();
				return true;
			}catch(Exception e){
				logger.error(e.getMessage(), e);
				return false;
			}
		}else
			return false;
	}
	public synchronized static boolean clearCache(){
		try {
			FileUtils.cleanDirectory(new File(FileCacheUtil.cachePath));
			return true;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	public static void main(String[] args) {
		/*List<String> a=new ArrayList<String>();
		a.add("cache");
		System.out.println(FileCacheUtil.putCache("tet",a));
		for(int i=0;i<10000000;i++){
			System.out.println(FileCacheUtil.getCache("tet",10));
			if( i==100000 )
				System.out.println(FileCacheUtil.putCache("tet",a));
		}*/
		try {
			FileUtils.cleanDirectory(new File(FileCacheUtil.cachePath+"/dasdf"));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
