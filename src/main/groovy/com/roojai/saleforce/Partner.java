package com.roojai.saleforce;

public enum Partner {
	Gobear("Gobear"),
	Rabbit("Rabbit"),
	Priceza("Priceza"),
	MrKumka("MrKumka"),
	MrKumkaB("MrKumkaLead"),
	Masii("Masii"),
	ShopeeNew("ShopeeNew"),
	ShopeeExisting("ShopeeExisting"),
	Hub360("Hub360 Co-reg"),
	Hub360_EDM("Hub360_EDM"),
	Shopee99("Shopee99"),
	toyotapattaya("toyotapattaya"),
	susco("susco"),
	wasuthagroup("wasuthagroup"),
	KTC("KTC"),
	KTCCard("KTCCard"),
	True("True"),
	Tyreplus("Tyreplus"),
	TyreplusSpecial("TyreplusSpecial"),
	Uber("Uber"),
	ADB("ADB"),
	accesstrade("accesstrade"),
	carro("carro"),
	PTG("PTG"),
	chinesean("chinesean"),
	amot("amot"),
	SoutheastTransfer("Southeast Transfer"),
	Cars24("Cars24")
	;

	private String text;
	Partner(String text){
		this.text= text;
	}
	public String text(){
		return this.text;
	}
}
