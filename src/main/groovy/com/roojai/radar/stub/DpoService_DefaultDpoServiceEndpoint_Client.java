
package com.roojai.radar.stub;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 3.1.8 2016-11-07T15:46:03.452+07:00
 * Generated source version: 3.1.8
 * 
 */
public final class DpoService_DefaultDpoServiceEndpoint_Client {

	private static final QName SERVICE_NAME = new QName("http://towerswatson.com/rto/dpo/services/2010/01",
			"DpoService");

	private DpoService_DefaultDpoServiceEndpoint_Client() {
	}

	public static void main(String args[]) throws java.lang.Exception {
		/*URL wsdlURL = DpoService_Service.WSDL_LOCATION;
		if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
			File wsdlFile = new File(args[0]);
			try {
				if (wsdlFile.exists()) {
					wsdlURL = wsdlFile.toURI().toURL();
				} else {
					wsdlURL = new URL(args[0]);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}*/

		DpoService_Service ss = new DpoService_Service();
		DpoService port = ss.getDefaultDpoServiceEndpoint();

			System.out.println("Invoking getPof...");
			com.roojai.radar.stub.PofRequest pof = new PofRequest();
			//new ObjectFactory().createMIDataCollectionDataContract();
			//pof.setPofrCollection();
			/*GregorianCalendar c = new GregorianCalendar();
			c.setTime(new Date());
			XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			pof.setTime(date);*/
			
			try {
				com.roojai.radar.stub.PofResponse _getPof__return = port.getPof(pof);
				System.out.println("getPof.result=" + _getPof__return);

			} catch (DpoServiceGetPofConfigurationFaultContractFaultFaultMessage e) {
				System.out.println(
						"Expected exception: DpoService_GetPof_ConfigurationFaultContractFault_FaultMessage has occurred.");
				System.out.println(e.toString());
			} catch (DpoServiceGetPofSevereFaultContractFaultFaultMessage e) {
				System.out.println(
						"Expected exception: DpoService_GetPof_SevereFaultContractFault_FaultMessage has occurred.");
				System.out.println(e.toString());
			}
			
			/*System.out.println("Invoking getPofWithId...");
			com.roojai.radar.PofRequestUsingMasterSet _getPofWithId_parameters = null;
			try {
				com.roojai.radar.PofResponse2 _getPofWithId__return = port.getPofWithId(_getPofWithId_parameters);
				System.out.println("getPofWithId.result=" + _getPofWithId__return);

			} catch (DpoServiceGetPofWithIdConfigurationFaultContractFaultFaultMessage e) {
				System.out.println(
						"Expected exception: DpoService_GetPofWithId_ConfigurationFaultContractFault_FaultMessage has occurred.");
				System.out.println(e.toString());
			} catch (DpoServiceGetPofWithIdSevereFaultContractFaultFaultMessage e) {
				System.out.println(
						"Expected exception: DpoService_GetPofWithId_SevereFaultContractFault_FaultMessage has occurred.");
				System.out.println(e.toString());
			}*/

		System.exit(0);
	}

}
