
package com.roojai.radar.stubMotor;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Time" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PofrCollection" type="{http://towerswatson.com/rto/smf/types/2010/01}PofrInformationCollectionDataContract" minOccurs="0"/>
 *         &lt;element name="KeyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KeyRequestTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "time",
    "pofrCollection",
    "keyName",
    "keyRequestTime"
})
@XmlRootElement(name = "PofRequestUsingKey", namespace = "http://towerswatson.com/rto/dpo/services/2010/01")
public class PofRequestUsingKey {

    @XmlElement(name = "Time", namespace = "http://towerswatson.com/rto/dpo/services/2010/01")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar time;
    @XmlElementRef(name = "PofrCollection", namespace = "http://towerswatson.com/rto/dpo/services/2010/01", type = JAXBElement.class, required = false)
    protected JAXBElement<PofrInformationCollectionDataContract> pofrCollection;
    @XmlElementRef(name = "KeyName", namespace = "http://towerswatson.com/rto/dpo/services/2010/01", type = JAXBElement.class, required = false)
    protected JAXBElement<String> keyName;
    @XmlElementRef(name = "KeyRequestTime", namespace = "http://towerswatson.com/rto/dpo/services/2010/01", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> keyRequestTime;

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
     * Gets the value of the keyName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getKeyName() {
        return keyName;
    }

    /**
     * Sets the value of the keyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setKeyName(JAXBElement<String> value) {
        this.keyName = value;
    }

    /**
     * Gets the value of the keyRequestTime property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getKeyRequestTime() {
        return keyRequestTime;
    }

    /**
     * Sets the value of the keyRequestTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setKeyRequestTime(JAXBElement<XMLGregorianCalendar> value) {
        this.keyRequestTime = value;
    }

}
