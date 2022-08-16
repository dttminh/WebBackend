package com.roojai.saleforce;

public enum ProductType {
	MotorBike("MotorBike"),
	MotorCar("MotorCar"),
	Health("Health"),
	PersonalAccident("Personal Accident"),
	ExtendedWarranty("Extended Warranty"),
	Covid19("Covid19"),
	CovidInboundTravel("CovidInboundTravel")
	;

	private String text;
	ProductType(String text){
		this.text= text;
	}
	public String text(){
		return this.text;
	}
}
