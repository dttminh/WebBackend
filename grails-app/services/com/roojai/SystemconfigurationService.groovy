package com.roojai

import com.roojai.saleforce.ProductType
import grails.gorm.transactions.Transactional

@Transactional
class SystemconfigurationService {

	def getValueByKey(key){
		try{
			def data = key.split("\\.")
			def category = data[0]
			def name = data[1]
			def configlist = Systemconfiguration.createCriteria().list(){
				eq("category",category)
				eq("name",name)
			}
			return configlist.get(0).value
		}catch(IndexOutOfBoundsException e){
			return null
		}
	}

	def getValueByCategory(category){
		Map<String, String> systemConfig = new HashMap<>();
		try{
			def configlist = Systemconfiguration.createCriteria().list(){
				eq("category",category)
			}

			for(Systemconfiguration sys in configlist){
				systemConfig.put(sys.getName(), sys.getValue());
			}

			return systemConfig
		}catch(IndexOutOfBoundsException e){
			return null
		}
	}

	Systemconfiguration[] getSystemconfigurationsByProductType(String productType) {
		ArrayList<String> arrayList = new ArrayList<String>()
		arrayList.add("omisePublicKey")

		if(productType == ProductType.Health.text()){
			arrayList.add("isTeleselling")
			arrayList.add("enableCoverList")
		}else if(productType == ProductType.MotorBike.text()){
			arrayList.add("isNewInsurer")
		}else if(productType == ProductType.Covid19.text()){
			arrayList.add("allowAgentDirectLink")
		}

		return Systemconfiguration.findAllByNameInList(arrayList)
	}

	String getOmisePublicKey(){
		Systemconfiguration systemconfiguration = Systemconfiguration.findByName("omisePublicKey")
		return systemconfiguration == null ? "" : systemconfiguration.value;
	}
}
