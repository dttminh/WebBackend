package com.roojai

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import com.roojai.util.ConstantUtil
import com.roojai.util.FileCacheUtil
import com.roojai.util.StringUtil
import grails.plugin.springsecurity.annotation.Secured
import org.slf4j.LoggerFactory

@Secured(['ROLE_ADMIN'])
class BackOfficeController {
	def redisService
    def index() {
     	/*def userinfo = session.user
		if(userinfo == null){
			redirect(action: "login")
		}*/
    }
    
    def login() {
    	//https://grailsrecipes.wordpress.com/grails-user-registration-and-login/
	    /*if (request.get) {
	        return // render the login view
	    }
		println "login" +params.login;
	    def u = Employee.findByLogin(params.login)
	    println u
	    if (u) {
	        if (u.password == params.password) {
	            session.user = u
	            redirect(action: "index")
	        }
	        else {
	            render(view: "login", model: [message: "Password incorrect"])
	        }
	    }
	    else {
	        render(view: "login", model: [message: "User not found"])
	    }*/
	}
	def viewLog(){
		def cmd = params.cmd
		String fileName = params.f
		def userHome = ConstantUtil.getAppLogPath()
			
		if( cmd.equals("download") ){
			response.setContentType("APPLICATION/OCTET-STREAM")
			response.setHeader("Content-Disposition", "Attachment;Filename="+fileName+".txt");
			
			def file = new File(userHome+fileName)
			def fileInputStream = new FileInputStream(file)
			def outputStream = response.getOutputStream()
			byte[] buffer = new byte[4096];
			int len;
			while ((len = fileInputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush()
			outputStream.close()
			fileInputStream.close()
		}
	}
	def clearCache(){
		redisService.flushDB()
		// deletes all keys in the database matching a pattern, this is fairly expensive
		// as it uses the <code>keys</code> operation.  If you're doing this a lot and
		// have many keys in redis, you should be aggregating your own set of keys that
		// you'll later want to delete
		//redisService.deleteKeysWithPattern("key:pattern:*")
		render "Clear all cache completed"
	}
	def clearSalesforceToken(){
		try{
			FileCacheUtil.clearCache()
			flash.message = "Completed"
		}catch(Exception e) {
			flash.message = "Error : "+e.getMessage()
		}
		render view:"index"
	}
	def viewServerLog(){
		def cmd = params.cmd
		String fileName = params.f
		def userHome = ConstantUtil.getServerLogPath()
		
		if( cmd.equals("download") ){
			response.setContentType("APPLICATION/OCTET-STREAM")
			response.setHeader("Content-Disposition", "Attachment;Filename="+fileName+".txt");
			
			def file = new File(userHome+fileName)
			def fileInputStream = new FileInputStream(file)
			def outputStream = response.getOutputStream()
			byte[] buffer = new byte[4096];
			int len;
			while ((len = fileInputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush()
			outputStream.close()
			fileInputStream.close()
		}
	}
	def changeLogLevel(){
		String packageName = params.packageName
		String logLevel = params.logLevel
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		ch.qos.logback.classic.Logger logger = loggerContext.getLogger(packageName);
		String oldLevel = logger.getLevel();
		if( "TRACE".equalsIgnoreCase(logLevel) ){
			logger.setLevel(Level.TRACE);
		}else if ( "DEBUG".equalsIgnoreCase(logLevel) ) {
			logger.setLevel(Level.DEBUG);
		}else if( "INFO".equalsIgnoreCase(logLevel) ) {
			logger.setLevel(Level.INFO);
		}else if( "WARN".equalsIgnoreCase(logLevel) ) {
			logger.setLevel(Level.WARN);
		}else if ("ERROR".equalsIgnoreCase(logLevel) ) {
				logger.setLevel(Level.ERROR);
		}else if ( "ALL".equalsIgnoreCase(logLevel) ) {
			logger.setLevel(Level.ALL);
		}else if ( "OFF".equalsIgnoreCase(logLevel) ) {
			logger.setLevel(Level.OFF);
		}
		render packageName + " current logger level: " + oldLevel +", Now its change to ->"+logLevel
	}
	def generateRetriveUrl(){
		if(request.method == 'POST'){
			String qoutationNumber= params.qoutationNumber
			String method = params.method
			List results = new ArrayList();
			String encyptIdStr = URLEncoder.encode(StringUtil.encrypt(qoutationNumber),"UTF-8");
			if( method.equals("genRetriveUrl") ){
				results.add("http://localhost:8181/#/retrieveQuotation?quotationId="+encyptIdStr)
				results.add("https://insure.uat-roojai.com/#/retrieveQuotation?quotationId="+encyptIdStr)
				results.add("https://insure.roojai.com/#/retrieveQuotation?quotationId="+encyptIdStr)
			}else{
				String encryptId = URLDecoder.decode(qoutationNumber, "UTF-8");
				String url = StringUtil.decrypt(encryptId);
				results.add(url);
			}
			[results:results,method:method]
		}
	}
}
