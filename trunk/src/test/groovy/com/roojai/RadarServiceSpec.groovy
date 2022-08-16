package com.roojai


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.roojai.RadarService
import com.roojai.radar.stub.PofRequest
import com.roojai.radar.RadarResult;
import com.roojai.radar.input.Root;

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(RadarService)
class RadarServiceSpec extends Specification {
	Root input = null
	RadarResult outputPof = null;
	RadarResult outputPofWithId = null;
	def setup() {
		// unmashall example input
		JAXBContext jaxbContext = JAXBContext.newInstance(Root.class);
		def file = new File('C:/workspace/roojai/src/main/groovy/com/roojai/radar/stub/input2.xml')
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		input = (Root) jaxbUnmarshaller.unmarshal(file);
		
		// call webservice
		outputPof = getService().getPof(input);
		println outputPof
		outputPofWithId = getService().getPofWithId(input,outputPof.getMasterSetId());
		println outputPofWithId
    }

    def cleanup() {
		
    }
	
   void "testPof"() {
	   
        expect:
			outputPof.getMasterSetId()!=null
    }
	void "testGetPofWithId"(){
		
		expect:
			outputPofWithId.getRoot().getQuote() !=null
	}
}
