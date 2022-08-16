package com.roojai.saleforce;

public enum PaymentActionType {
	Receive_Credit_Card,
	Receive_Internet_Banking,
	Receive_Bill_Payment,
	Send_Bill_Payment
	;
	
	public static boolean isReceiveAction(PaymentActionType action){
		if( action.equals(Receive_Credit_Card) || action.equals(Receive_Internet_Banking) || action.equals(Receive_Bill_Payment) ){
			return true;
		}else
			return false;
	}
}
