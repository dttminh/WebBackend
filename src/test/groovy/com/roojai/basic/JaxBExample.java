package com.roojai.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxBExample {

	static Logger logger = LoggerFactory.getLogger("com.roojai.basic.JaxBExample");

	//https://www.mkyong.com/java/jaxb-hello-world-example/
	public static void main(String[] args) {
		JaxBExample jaxb = new JaxBExample();
		jaxb.convertObjToXml();
		jaxb.convertXmlToObj();
	}
	public void convertObjToXml(){
		Customer customer = new Customer();
		customer.setId(100);
		customer.setName("mkyong");
		customer.setAge(29);

		try {
			File file = new File("file.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(customer, file);
			jaxbMarshaller.marshal(customer, System.out);

		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		}
	}
	public void convertXmlToObj(){
		try {

			File file = new File("file.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Customer customer = (Customer) jaxbUnmarshaller.unmarshal(file);
			System.out.println(customer);

		  } catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		  }

	}
}
