package com.roojai.util;

import grails.util.Environment;

public class ConstantUtil {
	public static final String userHome = System.getProperty("user.home")+"/";
	public static final String azureLogPath = System.getProperty("catalina.base")+"/";
	public static final String appLogs = "applogs/";
	public static final String serverLog = "logs/";
	public static String getAppLogPath(){
		if( Environment.isDevelopmentMode() )
			return userHome+appLogs;
		else
			return azureLogPath+appLogs;
	}
	public static String getServerLogPath(){
		if( Environment.isDevelopmentMode() )
			return userHome+serverLog;
		else
			return azureLogPath+serverLog;
	}
}
