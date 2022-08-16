package com.roojai

class StatReportJob {
	
    static triggers = {
		//cron name:   'StatReportCronTrigger',   startDelay: 10000, cronExpression: '0 0 0 * * ?'
    }

    def execute() {
        // execute job
		//println "StatReportCronTrigger"
		/*def quote = SaleforceData.executeQuery("select count(distinct opportunity_number) from SaleforceData where FORMAT(created,'yyyy-MM-dd') = FORMAT(GetDate()-1, 'yyyy-MM-dd')")[0]
		def creditCard = PaysbuyData.executeQuery("SELECT count(*) as SUMMARY FROM PaysbuyData where pay_type = ? and parameter_type = 'receive' and FORMAT(event_date,'yyyy-MM-dd') = FORMAT(GetDate()-1, 'yyyy-MM-dd') and result = '00'",['credit'])[0]
		def onlineBanking = PaysbuyData.executeQuery("SELECT count(*) as SUMMARY  FROM PaysbuyData where pay_type = ? and parameter_type = 'receive' and FORMAT(event_date,'yyyy-MM-dd') = FORMAT(GetDate()-1, 'yyyy-MM-dd') and result = '00'",['online_bank'])[0]
		def billPayment = PaysbuyData.executeQuery("SELECT count(*) as SUMMARY  FROM PaysbuyData where pay_type = ? and parameter_type = 'receive' and FORMAT(event_date,'yyyy-MM-dd') = FORMAT(GetDate()-1, 'yyyy-MM-dd') and result = '00'",['cash'])[0]

		StatReportData str = new StatReportData(quote:quote,creditCard:creditCard,onlineBanking:onlineBanking,billPayment:billPayment,created:new Date()).save(flush:true)*/
	}
}
