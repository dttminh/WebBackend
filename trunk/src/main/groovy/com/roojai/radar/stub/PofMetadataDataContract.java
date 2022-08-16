
package com.roojai.radar.stub;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for PofMetadataDataContract complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PofMetadataDataContract"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ScheduleId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid"/&gt;
 *         &lt;element name="MasterSetId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid"/&gt;
 *         &lt;element name="StrategyIndex" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ConfigSetId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid"/&gt;
 *         &lt;element name="ModelRevisionId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&gt;
 *         &lt;element name="RequestedMasterSetId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&gt;
 *         &lt;element name="ScheduleAliasOfId" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PofMetadataDataContract", propOrder = {
    "scheduleId",
    "masterSetId",
    "strategyIndex",
    "configSetId",
    "modelRevisionId",
    "requestedMasterSetId",
    "scheduleAliasOfId"
})
public class PofMetadataDataContract {

    @XmlElement(name = "ScheduleId", required = true)
    protected String scheduleId;
    @XmlElement(name = "MasterSetId", required = true)
    protected String masterSetId;
    @XmlElement(name = "StrategyIndex", required = true, type = Integer.class, nillable = true)
    protected Integer strategyIndex;
    @XmlElement(name = "ConfigSetId", required = true, nillable = true)
    protected String configSetId;
    @XmlElementRef(name = "ModelRevisionId", namespace = "http://towerswatson.com/rto/smf/types/2010/01", type = JAXBElement.class, required = false)
    protected JAXBElement<String> modelRevisionId;
    @XmlElementRef(name = "RequestedMasterSetId", namespace = "http://towerswatson.com/rto/smf/types/2010/01", type = JAXBElement.class, required = false)
    protected JAXBElement<String> requestedMasterSetId;
    @XmlElementRef(name = "ScheduleAliasOfId", namespace = "http://towerswatson.com/rto/smf/types/2010/01", type = JAXBElement.class, required = false)
    protected JAXBElement<String> scheduleAliasOfId;

    /**
     * Gets the value of the scheduleId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduleId() {
        return scheduleId;
    }

    /**
     * Sets the value of the scheduleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduleId(String value) {
        this.scheduleId = value;
    }

    /**
     * Gets the value of the masterSetId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMasterSetId() {
        return masterSetId;
    }

    /**
     * Sets the value of the masterSetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMasterSetId(String value) {
        this.masterSetId = value;
    }

    /**
     * Gets the value of the strategyIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStrategyIndex() {
        return strategyIndex;
    }

    /**
     * Sets the value of the strategyIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStrategyIndex(Integer value) {
        this.strategyIndex = value;
    }

    /**
     * Gets the value of the configSetId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigSetId() {
        return configSetId;
    }

    /**
     * Sets the value of the configSetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigSetId(String value) {
        this.configSetId = value;
    }

    /**
     * Gets the value of the modelRevisionId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getModelRevisionId() {
        return modelRevisionId;
    }

    /**
     * Sets the value of the modelRevisionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setModelRevisionId(JAXBElement<String> value) {
        this.modelRevisionId = value;
    }

    /**
     * Gets the value of the requestedMasterSetId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRequestedMasterSetId() {
        return requestedMasterSetId;
    }

    /**
     * Sets the value of the requestedMasterSetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRequestedMasterSetId(JAXBElement<String> value) {
        this.requestedMasterSetId = value;
    }

    /**
     * Gets the value of the scheduleAliasOfId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getScheduleAliasOfId() {
        return scheduleAliasOfId;
    }

    /**
     * Sets the value of the scheduleAliasOfId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setScheduleAliasOfId(JAXBElement<String> value) {
        this.scheduleAliasOfId = value;
    }

}
