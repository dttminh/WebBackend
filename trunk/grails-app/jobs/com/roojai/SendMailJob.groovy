package com.roojai
class SendMailJob {
    static triggers = {
      simple name: 'every10min', repeatInterval: 10*60*1000 // execute job once in 10 minute
    }
	def MailService
	def SystemconfigurationService
    def execute() {
		// execute job
		// sendmail
		// set status send = true
		// remove data older than 1 month
		def subject = ""
		def mailToPayment = SystemconfigurationService.getValueByKey("mail.mailToPayment")//"paysbuy_issue@pimarythailand.com"
		def mailToSaleforce = SystemconfigurationService.getValueByKey("mail.mailToSaleforce")//"saleforce_issue@pimarythailand.com"
		def mailToRadar = SystemconfigurationService.getValueByKey("mail.mailToRadar")
		def mailToPaysbuy = SystemconfigurationService.getValueByKey("mail.mailToPaysbuy")
		def mailFromPayment = SystemconfigurationService.getValueByKey("mail.mailFromPayment")//"paysbuy_alert@roojai.com"
		def mailFromSaleforce = SystemconfigurationService.getValueByKey("mail.mailFromSaleforce")//"saleforce_alert@roojai.com"
		def mailFromRadar = SystemconfigurationService.getValueByKey("mail.mailFromRadar")
		def mailFromPaysbuy = SystemconfigurationService.getValueByKey("mail.mailFromPaysbuy")
		def mailFromSalesforceError = SystemconfigurationService.getValueByKey("mail.mailFromSalesforceError")
		def mailToSalesforceError = SystemconfigurationService.getValueByKey("mail.mailToSalesforceError")
		
		def smd = SendMailData.createCriteria().list(){
			eq("status","N")
			order("created","asc")
		}
		for(SendMailData data: smd){
			StringBuffer content = new StringBuffer()
			if(data.messageType.equals("Payment")){
				subject = SystemconfigurationService.getValueByKey("mail.paymentSubject")
				content.append("<b>Opportunity Number:</b> "+data.opportunityNumber)
				if(data.jsonInput != null){
					content.append("<br>")
					content.append("<b>Json Input:</b>")
					content.append("<br>")
					content.append(data.jsonInput)
				}
				if(data.jsonOutput != null){
					content.append("<br>")
					content.append("<b>Json Output:</b>")
					content.append("<br>")
					content.append(data.jsonOutput)
				}
				content.append("<br>")
				content.append("<b>Created:</b> "+data.created)
				def saleforceData = SaleforceData.createCriteria().list(){
					eq("opportunity_number",data.opportunityNumber)
					maxResults(1)
					order("created","desc")
				}
				if(saleforceData.size()>0){
					if(saleforceData.get(0).json_input != null){
						content.append("<br>")
						content.append("<b>Last json input to saleforce:</b>")
						content.append("<br>")
						content.append(saleforceData.get(0).json_input)
						content.append("<br>")
						content.append("<b>Last json output from saleforce:</b>")
						content.append("<br>")
						content.append(saleforceData.get(0).output)
					}
				}
				MailService.send(mailFromPayment,mailToPayment,subject,content.toString())
			}else if(data.messageType.equals("Saleforce")){
				subject = SystemconfigurationService.getValueByKey("mail.saleforceSubject")
				content.append("<b>Opportunity Number:</b> "+data.opportunityNumber)
				content.append("<br>")
				content.append("<b>Method:</b> "+data.method)
				content.append("<br>")
				content.append("<b>Json Input:</b>")
				content.append("<br>")
				content.append(data.jsonInput)
				content.append("<br>")
				content.append("<b>Json Output:</b>")
				content.append("<br>")
				content.append(data.jsonOutput)
				content.append("<br>")
				content.append("<b>Created:</b> "+data.created)
				MailService.send(mailFromSaleforce,mailToSaleforce,subject,content.toString())
			}else if(data.messageType.equals("Radar")){
				subject = SystemconfigurationService.getValueByKey("mail.radarSubject")
				content.append("<b>Opportunity Number:</b> "+data.opportunityNumber)
				content.append("<br>")
				content.append("<b>Method:</b> "+data.method)
				content.append("<br>")
				content.append("<b>Error Code:</b> "+data.errorMessage)
				content.append("<br>")
				content.append("<b>XML Input:</b>")
				content.append("<br>")
				content.append(data.jsonInput.toString().trim().replaceAll("<","&lt;").replaceAll(">","&gt;"))
				content.append("<br>")
				content.append("<b>XML Output:</b>")
				content.append("<br>")
				content.append(data.jsonOutput.toString().trim().replaceAll("<","&lt;").replaceAll(">","&gt;"))
				content.append("<br>")
				content.append("<b>Created:</b> "+data.created)
				MailService.send(mailFromRadar,mailToRadar,subject,content.toString())
			}else if(data.messageType.equals("Paysbuy")){
				subject = SystemconfigurationService.getValueByKey("mail.paysbuySubject")
				content.append("<b>Opportunity Number:</b> "+data.opportunityNumber)
				content.append("<br>")
				content.append("<b>Payment Type:</b> "+data.method)
				content.append("<br>")
				content.append("<b>Customer Name:</b> "+data.customerName)
				content.append("<br>")
				content.append("<b>Phone Number:</b> "+data.phoneNumber)
				content.append("<br>")
				content.append("<b>Email:</b> "+data.email)
				content.append("<br>")
				content.append("<b>Amount:</b> "+data.amount)
				content.append("<br>")
				content.append("<b>Created:</b> "+data.created)
				MailService.send(mailFromPaysbuy,mailToPaysbuy,subject,content.toString())
			}else if(data.messageType.equals("SalesforceError")){
				subject = SystemconfigurationService.getValueByKey("mail.salesforceErrorSubject")
				content.append("<b>Opportunity Number:</b> "+data.opportunityNumber)
				content.append("<br>")
				content.append("<b>Method:</b> "+data.method)
				content.append("<br>")
				content.append("<b>Json Input:</b>")
				content.append("<br>")
				content.append(data.jsonInput)
				content.append("<br>")
				content.append("<b>Json Output:</b>")
				content.append("<br>")
				content.append(data.jsonOutput)
				content.append("<br>")
				content.append(data.errorMessage ? "<b>Error Message : </b>"+data.errorMessage+"<br>":"")
				content.append("<b>Created:</b> "+data.created)
				MailService.send(mailFromSalesforceError,mailToSalesforceError,subject,content.toString())
			}else if(data.messageType.equals("Log")){
				String _subject = SystemconfigurationService.getValueByKey("mail.errorMonitorSubject")
				_subject = _subject.replace("{method}", data.method)

				String _content = SystemconfigurationService.getValueByKey("mail.errorMonitorEmailBody")
				_content = _content.replace("{error}", data.errorMessage)

				def emailTo = SystemconfigurationService.getValueByKey("mail.errorMonitorEmailTo")
				def emailFrom = SystemconfigurationService.getValueByKey("mail.errorMonitorEmailFrom")

				MailService.send(emailFrom, emailTo, _subject, _content)
			}
			data.status = "true"
			data.save(flush:true)
		}
		SendMailData.executeUpdate("delete from SendMailData where status = ? and created < DATEADD(day,-30,getdate())",["true"]);
    }
}
