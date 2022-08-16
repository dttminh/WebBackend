package com.roojai.saleforce

class QuotationForm {
	Driver phaccount=new Driver()
	List<Driver> drivers=new ArrayList<Driver>()
	QuoteLine quoteLine = new QuoteLine()
	Quote quote = new Quote()
	Payment payment = new Payment()
	List<Accessory> accessories = new ArrayList<Accessory>()
	BrokerInfo brokerInfo = new BrokerInfo()
	
	@Override
	public String toString() {
		return "QuotationForm [phaccount=" + phaccount +
				", drivers=" + drivers +
				", quoteLine=" + quoteLine +
				", qoute=" + quote +
				", payment=" + payment +
				", accessories=" + accessories +
				", brokerInfo=" + brokerInfo +
				"]"
	}
}
