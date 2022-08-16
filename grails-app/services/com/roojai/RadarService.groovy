package com.roojai

import com.roojai.radar.RadarResult
import com.roojai.radar.input.Root
import com.roojai.radar.stub.*
import com.roojai.saleforce.ProductType
import com.roojai.util.StatusUtil

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller

class RadarService {
	//private final QName SERVICE_NAME = new QName("http://towerswatson.com/rto/dpo/services/2010/01","DpoService");
	//private URL wsdlURL = DpoService_Service.WSDL_LOCATION;
	//private URL wsdlURL =  new URL("file:DpoService.wsdl");
	private DpoService_Service carDpoService = new DpoService_Service();
	private DpoService carService = carDpoService.getDefaultDpoServiceEndpoint2();

	//private URL wsdlMotorCycleURL =  new URL("file:MotorCycleDpoService.wsdl");
	private com.roojai.radar.stubMotor.DpoService_Service motorCycleDpoService = new com.roojai.radar.stubMotor.DpoService_Service();
	private com.roojai.radar.stubMotor.DpoService motorCycleService = motorCycleDpoService.getDefaultDpoServiceEndpoint1();

	private JAXBContext jaxbContextOutput = JAXBContext.newInstance(com.roojai.radar.output.Root.class);
	private JAXBContext jaxbContextMotorOutput = JAXBContext.newInstance(com.roojai.radar.stubMotor.output.Root.class);
	private JAXBContext jaxbContextInput = JAXBContext.newInstance(Root.class);

	def SystemconfigurationService

    RadarResult getPof(Root input,String productType) {
		RadarResult result = new RadarResult();
		if( productType!=null && productType.equalsIgnoreCase(ProductType.MotorBike.text()) ){
			com.roojai.radar.stubMotor.PofRequest pofMotorRequest = prepareMotorInput(input);
			com.roojai.radar.stubMotor.PofResponse wsResult = motorCycleService.getPof(pofMotorRequest);
			if( wsResult.getErrorMessage().getValue()!=null ){
				result.setErrorMessage(wsResult.getErrorMessage().getValue().toString())
				result.setErrorCode(wsResult.getErrorCode().getValue().toString())
			}else{
				com.roojai.radar.stubMotor.PofInformationDataContract pof =  wsResult.getPofCollection().getValue().getPofInformationDataContract().get(0);
				result.setRoot( convertXmlToObject(pof.getPof().getValue(),productType));
				result.setMasterSetId(pof.getPofMetadata().getValue().getMasterSetId())
			}
		}else{// MotorCar
			PofRequest pofRequest = prepareInput(input);
			// note
			com.roojai.radar.stub.PofResponse wsResult = carService.getPof(pofRequest);
			if( wsResult.getErrorMessage().getValue()!=null ){
				result.setErrorMessage(wsResult.getErrorMessage().getValue().toString())
				result.setErrorCode(wsResult.getErrorCode().getValue().toString())
			}else{
				PofInformationDataContract pof =  wsResult.getPofCollection().getValue().getPofInformationDataContract().get(0);
				result.setRoot(convertXmlToObject(pof.getPof().getValue(),productType));
				result.setMasterSetId(pof.getPofMetadata().getValue().getMasterSetId())
			}
		}
		return result;
    }
	def RadarResult getPofWithId(Root input,String masterSetId,String productType){
		RadarResult result = new RadarResult();
		if(  productType!=null && productType.equalsIgnoreCase(ProductType.MotorBike.text()) ){
			com.roojai.radar.stubMotor.PofRequestUsingMasterSet pofRequestUsingMasterSet = new com.roojai.radar.stubMotor.PofRequestUsingMasterSet()
			pofRequestUsingMasterSet.setMasterSetId(masterSetId)
			com.roojai.radar.stubMotor.PofRequest pofRequest = prepareMotorInput(input);
			pofRequestUsingMasterSet.setPofrCollection(pofRequest.getPofrCollection())
			com.roojai.radar.stubMotor.PofResponse2 wsResult = motorCycleService.getPofWithId(pofRequestUsingMasterSet);

			com.roojai.radar.stubMotor.PofInformationDataContract pof =  wsResult.getPofCollection().getValue().getPofInformationDataContract().get(0)
			if( pof.getErrorCode().getValue()!=null ){
				result.setErrorMessage(pof.getErrorMessage().getValue().toString())
				result.setErrorCode(pof.getErrorCode().getValue().toString())
			}else{
				result.setRoot(convertXmlToObject(pof.getPof().getValue(),productType));
				result.setMasterSetId(pof.getPofMetadata().getValue().getMasterSetId())
			}
		}else{
			PofRequestUsingMasterSet pofRequestUsingMasterSet = new PofRequestUsingMasterSet()
			pofRequestUsingMasterSet.setMasterSetId(masterSetId)
			PofRequest pofRequest = prepareInput(input);
			pofRequestUsingMasterSet.setPofrCollection(pofRequest.getPofrCollection())

			PofResponse2 wsResult = carService.getPofWithId(pofRequestUsingMasterSet);

			PofInformationDataContract pof =  wsResult.getPofCollection().getValue().getPofInformationDataContract().get(0)
			if( pof.getErrorCode().getValue()!=null ){
				result.setErrorMessage(pof.getErrorMessage().getValue().toString())
				result.setErrorCode(pof.getErrorCode().getValue().toString())
			}else{
				result.setRoot(convertXmlToObject(pof.getPof().getValue(),productType));
				result.setMasterSetId(pof.getPofMetadata().getValue().getMasterSetId())
			}
		}
		return result;
	}
	private com.roojai.radar.stubMotor.PofRequest prepareMotorInput(Root input){
		com.roojai.radar.stubMotor.PofRequest pofRequest = new com.roojai.radar.stubMotor.PofRequest();
		com.roojai.radar.stubMotor.ObjectFactory inputFactory = new com.roojai.radar.stubMotor.ObjectFactory();

		JAXBElement<com.roojai.radar.stubMotor.PofrInformationCollectionDataContract> jaxbPofrCollectionData = inputFactory.createPofRequestPofrCollection(new com.roojai.radar.stubMotor.PofrInformationCollectionDataContract());
		com.roojai.radar.stubMotor.PofrInformationCollectionDataContract pofrInformationDataContract = jaxbPofrCollectionData.getValue();

		com.roojai.radar.stubMotor.PofrInformationDataContract pofr = new com.roojai.radar.stubMotor.PofrInformationDataContract();
		String xml = convertObjectToXml(input);

		pofr.setPofr(xml);
		pofrInformationDataContract.getPofrInformationDataContract().add(pofr);
		pofRequest.setPofrCollection(jaxbPofrCollectionData);

		return  pofRequest;
	}
	private PofRequest prepareInput(Root input){
		PofRequest pofRequest = new PofRequest();
		ObjectFactory inputFactory = new ObjectFactory();

		JAXBElement<PofrInformationCollectionDataContract> jaxbPofrCollectionData = inputFactory.createPofRequestPofrCollection(new PofrInformationCollectionDataContract());
		PofrInformationCollectionDataContract pofrInformationDataContract = jaxbPofrCollectionData.getValue();

		PofrInformationDataContract pofr = new PofrInformationDataContract();
		String xml = convertObjectToXml(input);

		pofr.setPofr(xml);
		pofrInformationDataContract.getPofrInformationDataContract().add(pofr);
		pofRequest.setPofrCollection(jaxbPofrCollectionData);

		return  pofRequest;
	}
	private String convertObjectToXml(Object input){
		//http://stackoverflow.com/questions/2472155/i-want-to-convert-an-output-stream-into-string-object
		//JAXBContext jaxbContext = JAXBContext.newInstance(Root.class);
		Marshaller jaxbMarshaller = jaxbContextInput.createMarshaller();
		StringWriter sw = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-16");// webservice using utf-16 not utf-8
		jaxbMarshaller.marshal(input,sw);

		return sw.toString();
	}
	private String convertOutputToXml(RadarResult radarResult,String productType){
		StringWriter sw = new StringWriter();
		Marshaller jaxbMarshaller = null
		if(  productType!=null && productType.equalsIgnoreCase(ProductType.MotorBike.text()) ){
			jaxbMarshaller = jaxbContextMotorOutput.createMarshaller();
		}else{
			jaxbMarshaller = jaxbContextOutput.createMarshaller();
		}
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-16");// webservice using utf-16 not utf-8
		jaxbMarshaller.marshal(radarResult.getRoot(),sw);
		return sw.toString()
	}
	private Object convertXmlToObject(String xml,String productType){
		Unmarshaller jaxbUnmarshaller = null
		//JAXBContext jaxbContext = JAXBContext.newInstance(com.roojai.radar.output.Root.class);
		if(  productType!=null && productType.equalsIgnoreCase(ProductType.MotorBike.text()) ){
			jaxbUnmarshaller = jaxbContextMotorOutput.createUnmarshaller();
		}else{
			jaxbUnmarshaller = jaxbContextOutput.createUnmarshaller();
		}
		//http://stackoverflow.com/questions/5458833/use-jaxb-to-create-object-from-xml-string
		Reader reader = new StringReader(xml);
		return jaxbUnmarshaller.unmarshal(reader);
	}
	RadarResult getRadarData(RadarData radarData, Root input,Integer finalValue,String opportunityNumber,String productType,String ipAddress){
		RadarResult radarResult;

		if( radarData.masterSetId!=null && !radarData.masterSetId.equals("") ){
			radarResult = this.getPofWithId(input,radarData.masterSetId,productType);
		}else{
			String defaultMasterSetId = SystemconfigurationService.getValueByKey("radar.defaultMasterSetId")
			radarResult = (defaultMasterSetId!=null && !defaultMasterSetId.equals("")) ? this.getPofWithId(input,defaultMasterSetId,productType) : this.getPof(input,productType)
		}
		radarResult.setfinalValue(finalValue)

		//JAXBContext jaxbContext = JAXBContext.newInstance(com.roojai.radar.output.Root.class);
		/*Marshaller jaxbMarshaller = jaxbContextOutput.createMarshaller();
		StringWriter sw = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-16");// webservice using utf-16 not utf-8
		jaxbMarshaller.marshal(radarResult.getRoot(),sw);*/
		String xmlOutput = convertOutputToXml(radarResult,productType)

		def xmlInput = convertObjectToXml(input)

		if(radarResult.root.quote.defaultCoverVoluntaryType?.value != null && !radarResult.root.quote.defaultCoverVoluntaryType.value.equals("")){
			radarResult.root.quote.defaultCoverVoluntaryType.value = StatusUtil.getCoverTypeConvert(radarResult.root.quote.defaultCoverVoluntaryType.value);
		}
		if(radarResult.root.quote.defaultCoverIncludesCompulsoryFlag?.value != null && !radarResult.root.quote.defaultCoverIncludesCompulsoryFlag.value.equals("")){
			radarResult.root.quote.defaultCoverIncludesCompulsoryFlag.value = StatusUtil.reverseIncludeCompulsory(radarResult.root.quote.defaultCoverIncludesCompulsoryFlag.value);
		}
		if(radarResult.root.quote.defaultCoverDriverPlan?.value != null && !radarResult.root.quote.defaultCoverDriverPlan.value.equals("")){
			radarResult.root.quote.defaultCoverDriverPlan.value = StatusUtil.getCoverDriverPlanFromRadar(radarResult.root.quote.defaultCoverDriverPlan.value);
		}
		if(radarResult.root.quote.defaultCoverWorkshopSelection?.value != null && !radarResult.root.quote.defaultCoverWorkshopSelection.value.equals("")){
			radarResult.root.quote.defaultCoverWorkshopSelection.value = StatusUtil.getWorkshop( radarResult.root.quote.defaultCoverWorkshopSelection.value );
		}

		RadarData radar = new RadarData(
			ownerIs:radarData.ownerIs
			,carBrand:radarData.carBrand
			,carModel:radarData.carModel
			,carYear:radarData.carYear
			,carDesc:radarData.carDesc
			,age:radarData.age
			,drivingExp:radarData.drivingExp
			,lastAccident:radarData.lastAccident
			,carFromHomeToWork:radarData.carFromHomeToWork
			,carInTheCourseOfWork:radarData.carInTheCourseOfWork
			,carUsageType:radarData.carUsageType
		    ,declareNCB:radarData.declareNCB
		    ,howlongbeeninsured:radarData.howlongbeeninsured
			,masterSetId:radarResult.getMasterSetId()
			,xmlData:xmlOutput
			,xmlInput:xmlInput
			,opportunityNumber:opportunityNumber
			,createDate:new Date()
			,remote_addr:ipAddress
			)
		radar.save(flush:true)

		radarResult.setId(radar.id)

		return radarResult
	}

}
