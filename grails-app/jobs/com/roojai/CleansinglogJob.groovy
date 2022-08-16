package com.roojai

import com.roojai.util.ConstantUtil
import grails.util.Environment

import java.text.SimpleDateFormat

class CleansinglogJob {
    static triggers = {
      //simple repeatInterval: 5000l // execute job once in 5 seconds
	  cron name:   'CleansingLogCronTrigger',   startDelay: 10000, cronExpression: '0 0 0 * * ?'
    }

    def execute() {
        // execute job
		//println "Cleansing Log"
		def config = Systemconfiguration.createCriteria().list(){
			eq("category","log")
		}
		for(Object cf:config){
			int parameter = ("-"+cf.value).toInteger()
			if(cf.name.equals("PaymentDataTTL")){
				PaymentData.executeUpdate("delete from PaymentData where created < DATEADD(day,?,getdate())",[parameter])
			}else if(cf.name.equals("PaysbuyDataTTL")){
				PaysbuyData.executeUpdate("delete from PaysbuyData where event_date < DATEADD(day,?,getdate())",[parameter])
			}else if(cf.name.equals("CallMeBackDataTTL")){
				CallMeBackData.executeUpdate("delete from CallMeBackData where created < DATEADD(day,?,getdate())",[parameter])
			}else if(cf.name.equals("RadarDataTTL")){
				RadarData.executeUpdate("delete from RadarData where createDate < DATEADD(day,?,getdate())",[parameter])
			}else if(cf.name.equals("SaleforceDataTTL")){
				SaleforceData.executeUpdate("delete from SaleforceData where created < DATEADD(day,?,getdate())",[parameter])
			}else if(cf.name.equals("SendMailDataTTL")){
				SendMailData.executeUpdate("delete from SendMailData where created < DATEADD(day,?,getdate())",[parameter])
			}else if(cf.name.equals("RateUsDataTTL")){
				RateUsData.executeUpdate("delete from RateUsData where created < DATEADD(day,?,getdate())",[parameter])
			}else if(cf.name.equals("StackTraceTTL")){
				int value = cf.value.toInteger()
				def startDate = new Date()-value
				def USER_HOME = ""
				if (Environment.isDevelopmentMode()){
					USER_HOME = System.getProperty('user.home');
				}else{
					USER_HOME = ConstantUtil.azureLogPath
				}
				new File(USER_HOME+"/applogs/").eachFileMatch(~/.*.txt/) { file ->
					int start = file.getName().indexOf("-")+1
					int end = file.getName().indexOf(".txt")
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
					Date fileDate = sdf.parse(file.getName().substring(start,end))
					if(fileDate < startDate){
						file.delete()
					}
				}
			}else if(cf.name.equals("RetrieveDataTTL")){
				RetrieveData.executeUpdate("delete from RetrieveData where created < DATEADD(day,?,getdate())",[parameter])
			}else if(cf.name.equals("LeadDataTTL")){
				LeadData.executeUpdate("delete from LeadData where created < DATEADD(day,?,getdate())",[parameter])
			}else if(cf.name.equals("OmiseDataTTL")){
				OmiseData.executeUpdate("delete from OmiseData where created < DATEADD(day,?,getdate())",[parameter])
			}else if(cf.name.equals("OmiseWebHooksLogTTL")){
				OmiseWebHooksLog.executeUpdate("delete from OmiseWebHooksLog where created < DATEADD(day,?,getdate())",[parameter])
			}else if(cf.name.equals("OTPTTL")){
				OTP.executeUpdate("delete from OTP where created < DATEADD(day,?,getdate())",[parameter])
			}else if(cf.name.equals("EventLogTTL")){
				EventLog.executeUpdate("delete from EventLog where created < DATEADD(day,?,getdate())",[parameter])
			}
		}
    }
}
