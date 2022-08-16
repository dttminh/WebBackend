
package com.roojai.radar.stub;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PofrCollection" type="{http://towerswatson.com/rto/smf/types/2010/01}PofrInformationCollectionDataContract" minOccurs="0"/&gt;
 *         &lt;element name="Time" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pofrCollection",
    "time"
})
@XmlRootElement(name = "PofRequest", namespace = "http://towerswatson.com/rto/dpo/services/2010/01")
public class PofRequest {

    @XmlElementRef(name = "PofrCollection", namespace = "http://towerswatson.com/rto/dpo/services/2010/01", type = JAXBElement.class, required = false)
    protected JAXBElement<PofrInformationCollectionDataContract> pofrCollection;
    @XmlElement(name = "Time", namespace = "http://towerswatson.com/rto/dpo/services/2010/01")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar time;

    /**
     * Gets the value of the pofrCollection property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PofrInformationCollectionDataContract }{@code >}
     *     
     */
    public JAXBElement<PofrInformationCollectionDataContract> getPofrCollection() {
        return pofrCollection;
    }

    /**
     * Sets the value of the pofrCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PofrInformationCollectionDataContract }{@code >}
     *     
     */
    public void setPofrCollection(JAXBElement<PofrInformationCollectionDataContract> value) {
        this.pofrCollection = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTime(XMLGregorianCalendar value) {
        this.time = value;
    }

}
