
package com.roojai.radar.stub;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


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
 *         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PofCollection" type="{http://towerswatson.com/rto/smf/types/2010/01}PofInformationCollectionDataContract" minOccurs="0"/&gt;
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
    "errorCode",
    "errorMessage",
    "pofCollection"
})
@XmlRootElement(name = "PofResponse", namespace = "http://towerswatson.com/rto/dpo/services/2010/01")
public class PofResponse {

    @XmlElementRef(name = "ErrorCode", namespace = "http://towerswatson.com/rto/dpo/services/2010/01", type = JAXBElement.class, required = false)
    protected JAXBElement<String> errorCode;
    @XmlElementRef(name = "ErrorMessage", namespace = "http://towerswatson.com/rto/dpo/services/2010/01", type = JAXBElement.class, required = false)
    protected JAXBElement<String> errorMessage;
    @XmlElementRef(name = "PofCollection", namespace = "http://towerswatson.com/rto/dpo/services/2010/01", type = JAXBElement.class, required = false)
    protected JAXBElement<PofInformationCollectionDataContract> pofCollection;

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setErrorCode(JAXBElement<String> value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setErrorMessage(JAXBElement<String> value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the pofCollection property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PofInformationCollectionDataContract }{@code >}
     *     
     */
    public JAXBElement<PofInformationCollectionDataContract> getPofCollection() {
        return pofCollection;
    }

    /**
     * Sets the value of the pofCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PofInformationCollectionDataContract }{@code >}
     *     
     */
    public void setPofCollection(JAXBElement<PofInformationCollectionDataContract> value) {
        this.pofCollection = value;
    }

}
