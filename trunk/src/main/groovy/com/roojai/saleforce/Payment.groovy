package com.roojai.saleforce

class Payment{

	String paymentStatus = ""
	String paymentID = ""
	String versionId = ""

	@Override
	public String toString() {
		return "Payment [paymentStatus=" + paymentStatus +
				", paymentID=" + paymentID +
				", versionId=" + versionId +
				"]"
	}
}