package com.roojai.radar;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.roojai.radar.TestMarshall;
import com.roojai.radar.input.ObjectFactory;
import com.roojai.radar.input.Root;
import com.roojai.radar.input.Root.Quote;
import com.roojai.radar.input.Root.Quote.CarAccessoryAirIntakeExhaust;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMarshall{

	static Logger logger = LoggerFactory.getLogger("com.roojai.radar.TestMarshall");

	public static void main(String[] args) throws Exception{
		TestMarshall t =new TestMarshall();
		//t.marshall();
		//t.unmarshall();
		t.unmarshall2();
	}

	public void marshall() {
		// marshall
		ObjectFactory input = new ObjectFactory();
		Root root = input.createRoot();
		Quote qoute = input.createRootQuote();
		
		// set  CarAccessoryAirIntakeExhaust
		CarAccessoryAirIntakeExhaust car = input.createRootQuoteCarAccessoryAirIntakeExhaust();
		car.setValue("ok");
		qoute.setCarAccessoryAirIntakeExhaust(car);
				
		// set 
		root.setQuote(qoute);
		
		try {
			File file = new File("file.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Root.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(root, file);
			jaxbMarshaller.marshal(root, System.out);

		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void unmarshall() {
		try {
			File file = new File("file.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Root.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Root root = (Root) jaxbUnmarshaller.unmarshal(file);
			System.out.println(root.getQuote().getCarAccessoryAirIntakeExhaust().getValue());

		  } catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		  }
	}
	public void unmarshall2() throws Exception{
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(com.roojai.radar.output.Root.class);
			
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Reader reader = new StringReader(readFile("C:/workspace/roojai/src/main/groovy/com/roojai/radar/output.xml",Charset.forName("utf-16")));
			//System.out.println(readFile("C:/workspace/roojai/src/main/groovy/com/roojai/radar/output.xml",Charset.forName("utf-16")));
			com.roojai.radar.output.Root output = (com.roojai.radar.output.Root) jaxbUnmarshaller.unmarshal(reader);
			System.out.println(output.getQuote().getArrayPrem0XsAnyWorkshopAnyDriverCompType1());

		  } catch (Exception e) {
			logger.error(e.getMessage(), e);
		  }
	}
	private String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
