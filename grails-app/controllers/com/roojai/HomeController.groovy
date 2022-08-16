package com.roojai

import com.roojai.util.StringUtil
import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONObject

import java.text.SimpleDateFormat

@Secured(['permitAll'])
class HomeController {
	def HttpService

    def index() {
		//redirect(controller: "backOffice")
    }

	def quotation(){
		
	}

	def version(){
		
	}

	def stat(){
		// today
		SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd")
		String dateString = formattedDate.format(new Date())
		
		def sqlTBAQuote = "select count(*) from SaleforceData where 1=1 and opportunity_number='TBA' "
		
		def sqlQuote ="select count(distinct opportunity_number) from SaleforceData where 1=1 and opportunity_number<>'TBA' "
		
		def sqlPayment = "SELECT count(distinct opportunityNumber) as count FROM PaymentData where 1=1 ";
		
		sqlQuote += " and created > '"+dateString+"'";
		sqlPayment  += " and created > '"+dateString+"'";
		
		def result = new HashMap();
		
		def todayTBAQuote = SaleforceData.executeQuery(sqlTBAQuote)[0];
		result.put("todayTBAQuote", todayTBAQuote);
		
		def todayQuote = SaleforceData.executeQuery(sqlQuote)[0];
		result.put("todayQuote", todayQuote);
		
		def creditSql = sqlPayment+ " and payment_type='Credit Card' ";
		def successCreditSql = creditSql + " and errorCode='00' ";
		def num = PaymentData.executeQuery(successCreditSql)[0];
		result.put("successCredit", num)
		
		def failedCreditSql = creditSql + " and errorCode='99' ";
		num = PaymentData.executeQuery(failedCreditSql)[0];
		result.put("failedCredit", num)
		
		def inetBankingSql = sqlPayment+ " and payment_type='Internet banking' ";
		
		def successInetBankSql = inetBankingSql + " and errorCode='00' ";
		num = PaymentData.executeQuery(successInetBankSql)[0];
		result.put("successInetBank", num)
		
		def failedInetBankSql = inetBankingSql + " and errorCode='99' ";
		num = PaymentData.executeQuery(failedInetBankSql)[0];
		result.put("failedInetBank", num)
		
		def cashSql = sqlPayment+ " and payment_type='Bill payment' ";
		
		def requestCashSql = cashSql +" and errorCode='00' and barcodeNumber is not null ";
		num = PaymentData.executeQuery(requestCashSql)[0];
		result.put("requestCash", num)
		
		def successCashSql = cashSql +" and errorCode='00' and barcodeNumber is null ";
		num = PaymentData.executeQuery(successCashSql)[0];
		result.put("successCash", num)
		
		result
	}
	
	def statreport(){
		def todayQuote
		def creditLastToday
		def onlineBankLastToday
		def cashLastToday
		def last7dayQuote
		def creditLast7day
		def onlineBankLast7day
		def cashLast7day
		def overAllQuote
		def overAllCredit
		def overAllOnlineBank
		def overAllCash
		def result = new HashMap();
		
		def srdToday = StatReportData.executeQuery("from StatReportData where FORMAT(created,'yyyy-MM-dd') = FORMAT(GetDate(), 'yyyy-MM-dd')")
		for(Object data:srdToday){
			todayQuote = data.quote
			creditLastToday = data.creditCard
			onlineBankLastToday = data.onlineBanking
			cashLastToday = data.billPayment
		}
		
		def srdLast7day = StatReportData.executeQuery("select sum(billPayment) as billPayment,sum(creditCard) as creditCard,sum(onlineBanking) as onlineBanking,sum(quote) as a from StatReportData where created between  GETDATE()-7 and  GETDATE()")[0]
		last7dayQuote = srdLast7day[3]
		creditLast7day = srdLast7day[1]
		onlineBankLast7day = srdLast7day[2]
		cashLast7day = srdLast7day[0]
		
		def srdOverAll = StatReportData.executeQuery("select sum(billPayment) as billPayment,sum(creditCard) as creditCard,sum(onlineBanking) as onlineBanking,sum(quote) as a from StatReportData")[0]
		overAllQuote = srdOverAll[3]
		overAllCredit = srdOverAll[1]
		overAllOnlineBank = srdOverAll[2]
		overAllCash = srdOverAll[0]
		
		result.put("overAllQuote", overAllQuote)
		result.put("overAllCredit", overAllCredit)
		result.put("overAllOnlineBank", overAllOnlineBank)
		result.put("overAllCash", overAllCash)
		result.put("last7dayQuote", last7dayQuote)
		result.put("creditLast7day", creditLast7day)
		result.put("onlineBankLast7day", onlineBankLast7day)
		result.put("cashLast7day", cashLast7day)
		result.put("todayQuote", todayQuote)
		result.put("creditLastToday", creditLastToday)
		result.put("onlineBankLastToday", onlineBankLastToday)
		result.put("cashLastToday", cashLastToday)
		
		result
	}

	def about(){
		JSONObject returnValue = new JSONObject()
		if(HttpService.testConnection()){
			returnValue.put("Version", grailsApplication.config.getProperty('version'))
			returnValue.put("Release date", grailsApplication.config.getProperty('releaseDate'))
		}
		render returnValue
	}
}
