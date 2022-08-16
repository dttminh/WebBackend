package com.roojai

class Postal {
	String id
	Integer postalCode
	Integer kpiProvinceCode
	String provinceEn
	String provinceTh
	Integer kpiDistrictCode	
	String districtEn
	String districtTh
	Integer kpiSubDistrictCode
	String subDistrictEn
	String subDistrictTh
	static mapping = {
		id column: 'kpi_code', generator: 'assigned'
		version false
	}
    static constraints = {
    }
}
