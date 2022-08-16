package com.roojai

class Occupation {
	Integer id
	String nameEn
	String nameTh
	Integer occupationGroup
	String insurer
	String productType

	static mapping = {
		table 'occupation'
		version false
	}
}
