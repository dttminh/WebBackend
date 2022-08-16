package com.roojai.saleforce;

public enum PaymentType {
	CreditCard("Credit Card"),
	BillPayment("Bill payment"),
	InternetBanking("Internet banking"),
	promptpay("PromptPay"),
	truemoney("TrueMoney"),
	rabbit_linepay("Rabbit Line Pay")
	;
	
	private String text;
	PaymentType(String text){
		this.text= text;
	}
	public String text(){
		return this.text;
	}
}
