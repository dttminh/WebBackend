
package com.roojai.radar.stub;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.roojai.radar package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _PofrInformationCollectionDataContract_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "PofrInformationCollectionDataContract");
    private final static QName _PofrInformationDataContract_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "PofrInformationDataContract");
    private final static QName _PofInformationCollectionDataContract_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "PofInformationCollectionDataContract");
    private final static QName _PofInformationDataContract_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "PofInformationDataContract");
    private final static QName _MIDataCollectionDataContract_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "MIDataCollectionDataContract");
    private final static QName _MIDataDataContract_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "MIDataDataContract");
    private final static QName _DataType_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "DataType");
    private final static QName _PofMetadataDataContract_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "PofMetadataDataContract");
    private final static QName _ConfigurationFaultContract_QNAME = new QName("http://towerswatson.com/rto/dpo/types/2010/01", "ConfigurationFaultContract");
    private final static QName _SevereFaultContract_QNAME = new QName("http://towerswatson.com/rto/dpo/types/2010/01", "SevereFaultContract");
    private final static QName _PofrResponse2DataContract_QNAME = new QName("http://towerswatson.com/rto/dpo/types/2010/01", "PofrResponse2DataContract");
    private final static QName _PofRequestPofrCollection_QNAME = new QName("http://towerswatson.com/rto/dpo/services/2010/01", "PofrCollection");
    private final static QName _PofResponseErrorCode_QNAME = new QName("http://towerswatson.com/rto/dpo/services/2010/01", "ErrorCode");
    private final static QName _PofResponseErrorMessage_QNAME = new QName("http://towerswatson.com/rto/dpo/services/2010/01", "ErrorMessage");
    private final static QName _PofResponsePofCollection_QNAME = new QName("http://towerswatson.com/rto/dpo/services/2010/01", "PofCollection");
    private final static QName _PofResponse2MetaData_QNAME = new QName("http://towerswatson.com/rto/dpo/services/2010/01", "MetaData");
    private final static QName _PofMetadataDataContractModelRevisionId_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "ModelRevisionId");
    private final static QName _PofMetadataDataContractRequestedMasterSetId_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "RequestedMasterSetId");
    private final static QName _PofMetadataDataContractScheduleAliasOfId_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "ScheduleAliasOfId");
    private final static QName _PofInformationDataContractPof_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "Pof");
    private final static QName _PofInformationDataContractErrorCode_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "ErrorCode");
    private final static QName _PofInformationDataContractErrorMessage_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "ErrorMessage");
    private final static QName _PofInformationDataContractMIDataCollection_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "MIDataCollection");
    private final static QName _PofInformationDataContractPofMetadata_QNAME = new QName("http://towerswatson.com/rto/smf/types/2010/01", "PofMetadata");
    private final static QName _PofrResponse2DataContractDiagnostics_QNAME = new QName("http://towerswatson.com/rto/dpo/types/2010/01", "Diagnostics");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.roojai.radar
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PofRequest }
     * 
     */
    public PofRequest createPofRequest() {
        return new PofRequest();
    }

    /**
     * Create an instance of {@link PofrInformationCollectionDataContract }
     * 
     */
    public PofrInformationCollectionDataContract createPofrInformationCollectionDataContract() {
        return new PofrInformationCollectionDataContract();
    }

    /**
     * Create an instance of {@link PofResponse }
     * 
     */
    public PofResponse createPofResponse() {
        return new PofResponse();
    }

    /**
     * Create an instance of {@link PofInformationCollectionDataContract }
     * 
     */
    public PofInformationCollectionDataContract createPofInformationCollectionDataContract() {
        return new PofInformationCollectionDataContract();
    }

    /**
     * Create an instance of {@link PofRequestUsingMasterSet }
     * 
     */
    public PofRequestUsingMasterSet createPofRequestUsingMasterSet() {
        return new PofRequestUsingMasterSet();
    }

    /**
     * Create an instance of {@link PofResponse2 }
     * 
     */
    public PofResponse2 createPofResponse2() {
        return new PofResponse2();
    }

    /**
     * Create an instance of {@link PofrResponse2DataContract }
     * 
     */
    public PofrResponse2DataContract createPofrResponse2DataContract() {
        return new PofrResponse2DataContract();
    }

    /**
     * Create an instance of {@link PofrInformationDataContract }
     * 
     */
    public PofrInformationDataContract createPofrInformationDataContract() {
        return new PofrInformationDataContract();
    }

    /**
     * Create an instance of {@link PofInformationDataContract }
     * 
     */
    public PofInformationDataContract createPofInformationDataContract() {
        return new PofInformationDataContract();
    }

    /**
     * Create an instance of {@link MIDataCollectionDataContract }
     * 
     */
    public MIDataCollectionDataContract createMIDataCollectionDataContract() {
        return new MIDataCollectionDataContract();
    }

    /**
     * Create an instance of {@link MIDataDataContract }
     * 
     */
    public MIDataDataContract createMIDataDataContract() {
        return new MIDataDataContract();
    }

    /**
     * Create an instance of {@link PofMetadataDataContract }
     * 
     */
    public PofMetadataDataContract createPofMetadataDataContract() {
        return new PofMetadataDataContract();
    }

    /**
     * Create an instance of {@link ConfigurationFaultContract }
     * 
     */
    public ConfigurationFaultContract createConfigurationFaultContract() {
        return new ConfigurationFaultContract();
    }

    /**
     * Create an instance of {@link SevereFaultContract }
     * 
     */
    public SevereFaultContract createSevereFaultContract() {
        return new SevereFaultContract();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PofrInformationCollectionDataContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "PofrInformationCollectionDataContract")
    public JAXBElement<PofrInformationCollectionDataContract> createPofrInformationCollectionDataContract(PofrInformationCollectionDataContract value) {
        return new JAXBElement<PofrInformationCollectionDataContract>(_PofrInformationCollectionDataContract_QNAME, PofrInformationCollectionDataContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PofrInformationDataContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "PofrInformationDataContract")
    public JAXBElement<PofrInformationDataContract> createPofrInformationDataContract(PofrInformationDataContract value) {
        return new JAXBElement<PofrInformationDataContract>(_PofrInformationDataContract_QNAME, PofrInformationDataContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PofInformationCollectionDataContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "PofInformationCollectionDataContract")
    public JAXBElement<PofInformationCollectionDataContract> createPofInformationCollectionDataContract(PofInformationCollectionDataContract value) {
        return new JAXBElement<PofInformationCollectionDataContract>(_PofInformationCollectionDataContract_QNAME, PofInformationCollectionDataContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PofInformationDataContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "PofInformationDataContract")
    public JAXBElement<PofInformationDataContract> createPofInformationDataContract(PofInformationDataContract value) {
        return new JAXBElement<PofInformationDataContract>(_PofInformationDataContract_QNAME, PofInformationDataContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MIDataCollectionDataContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "MIDataCollectionDataContract")
    public JAXBElement<MIDataCollectionDataContract> createMIDataCollectionDataContract(MIDataCollectionDataContract value) {
        return new JAXBElement<MIDataCollectionDataContract>(_MIDataCollectionDataContract_QNAME, MIDataCollectionDataContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MIDataDataContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "MIDataDataContract")
    public JAXBElement<MIDataDataContract> createMIDataDataContract(MIDataDataContract value) {
        return new JAXBElement<MIDataDataContract>(_MIDataDataContract_QNAME, MIDataDataContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "DataType")
    public JAXBElement<DataType> createDataType(DataType value) {
        return new JAXBElement<DataType>(_DataType_QNAME, DataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PofMetadataDataContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "PofMetadataDataContract")
    public JAXBElement<PofMetadataDataContract> createPofMetadataDataContract(PofMetadataDataContract value) {
        return new JAXBElement<PofMetadataDataContract>(_PofMetadataDataContract_QNAME, PofMetadataDataContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfigurationFaultContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/dpo/types/2010/01", name = "ConfigurationFaultContract")
    public JAXBElement<ConfigurationFaultContract> createConfigurationFaultContract(ConfigurationFaultContract value) {
        return new JAXBElement<ConfigurationFaultContract>(_ConfigurationFaultContract_QNAME, ConfigurationFaultContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SevereFaultContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/dpo/types/2010/01", name = "SevereFaultContract")
    public JAXBElement<SevereFaultContract> createSevereFaultContract(SevereFaultContract value) {
        return new JAXBElement<SevereFaultContract>(_SevereFaultContract_QNAME, SevereFaultContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PofrResponse2DataContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/dpo/types/2010/01", name = "PofrResponse2DataContract")
    public JAXBElement<PofrResponse2DataContract> createPofrResponse2DataContract(PofrResponse2DataContract value) {
        return new JAXBElement<PofrResponse2DataContract>(_PofrResponse2DataContract_QNAME, PofrResponse2DataContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PofrInformationCollectionDataContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/dpo/services/2010/01", name = "PofrCollection", scope = PofRequest.class)
    public JAXBElement<PofrInformationCollectionDataContract> createPofRequestPofrCollection(PofrInformationCollectionDataContract value) {
        return new JAXBElement<PofrInformationCollectionDataContract>(_PofRequestPofrCollection_QNAME, PofrInformationCollectionDataContract.class, PofRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/dpo/services/2010/01", name = "ErrorCode", scope = PofResponse.class)
    public JAXBElement<String> createPofResponseErrorCode(String value) {
        return new JAXBElement<String>(_PofResponseErrorCode_QNAME, String.class, PofResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/dpo/services/2010/01", name = "ErrorMessage", scope = PofResponse.class)
    public JAXBElement<String> createPofResponseErrorMessage(String value) {
        return new JAXBElement<String>(_PofResponseErrorMessage_QNAME, String.class, PofResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PofInformationCollectionDataContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/dpo/services/2010/01", name = "PofCollection", scope = PofResponse.class)
    public JAXBElement<PofInformationCollectionDataContract> createPofResponsePofCollection(PofInformationCollectionDataContract value) {
        return new JAXBElement<PofInformationCollectionDataContract>(_PofResponsePofCollection_QNAME, PofInformationCollectionDataContract.class, PofResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PofrInformationCollectionDataContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/dpo/services/2010/01", name = "PofrCollection", scope = PofRequestUsingMasterSet.class)
    public JAXBElement<PofrInformationCollectionDataContract> createPofRequestUsingMasterSetPofrCollection(PofrInformationCollectionDataContract value) {
        return new JAXBElement<PofrInformationCollectionDataContract>(_PofRequestPofrCollection_QNAME, PofrInformationCollectionDataContract.class, PofRequestUsingMasterSet.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PofrResponse2DataContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/dpo/services/2010/01", name = "MetaData", scope = PofResponse2 .class)
    public JAXBElement<PofrResponse2DataContract> createPofResponse2MetaData(PofrResponse2DataContract value) {
        return new JAXBElement<PofrResponse2DataContract>(_PofResponse2MetaData_QNAME, PofrResponse2DataContract.class, PofResponse2 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PofInformationCollectionDataContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/dpo/services/2010/01", name = "PofCollection", scope = PofResponse2 .class)
    public JAXBElement<PofInformationCollectionDataContract> createPofResponse2PofCollection(PofInformationCollectionDataContract value) {
        return new JAXBElement<PofInformationCollectionDataContract>(_PofResponsePofCollection_QNAME, PofInformationCollectionDataContract.class, PofResponse2 .class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "ModelRevisionId", scope = PofMetadataDataContract.class)
    public JAXBElement<String> createPofMetadataDataContractModelRevisionId(String value) {
        return new JAXBElement<String>(_PofMetadataDataContractModelRevisionId_QNAME, String.class, PofMetadataDataContract.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "RequestedMasterSetId", scope = PofMetadataDataContract.class)
    public JAXBElement<String> createPofMetadataDataContractRequestedMasterSetId(String value) {
        return new JAXBElement<String>(_PofMetadataDataContractRequestedMasterSetId_QNAME, String.class, PofMetadataDataContract.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "ScheduleAliasOfId", scope = PofMetadataDataContract.class)
    public JAXBElement<String> createPofMetadataDataContractScheduleAliasOfId(String value) {
        return new JAXBElement<String>(_PofMetadataDataContractScheduleAliasOfId_QNAME, String.class, PofMetadataDataContract.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "Pof", scope = PofInformationDataContract.class)
    public JAXBElement<String> createPofInformationDataContractPof(String value) {
        return new JAXBElement<String>(_PofInformationDataContractPof_QNAME, String.class, PofInformationDataContract.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "ErrorCode", scope = PofInformationDataContract.class)
    public JAXBElement<String> createPofInformationDataContractErrorCode(String value) {
        return new JAXBElement<String>(_PofInformationDataContractErrorCode_QNAME, String.class, PofInformationDataContract.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "ErrorMessage", scope = PofInformationDataContract.class)
    public JAXBElement<String> createPofInformationDataContractErrorMessage(String value) {
        return new JAXBElement<String>(_PofInformationDataContractErrorMessage_QNAME, String.class, PofInformationDataContract.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MIDataCollectionDataContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "MIDataCollection", scope = PofInformationDataContract.class)
    public JAXBElement<MIDataCollectionDataContract> createPofInformationDataContractMIDataCollection(MIDataCollectionDataContract value) {
        return new JAXBElement<MIDataCollectionDataContract>(_PofInformationDataContractMIDataCollection_QNAME, MIDataCollectionDataContract.class, PofInformationDataContract.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PofMetadataDataContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/smf/types/2010/01", name = "PofMetadata", scope = PofInformationDataContract.class)
    public JAXBElement<PofMetadataDataContract> createPofInformationDataContractPofMetadata(PofMetadataDataContract value) {
        return new JAXBElement<PofMetadataDataContract>(_PofInformationDataContractPofMetadata_QNAME, PofMetadataDataContract.class, PofInformationDataContract.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://towerswatson.com/rto/dpo/types/2010/01", name = "Diagnostics", scope = PofrResponse2DataContract.class)
    public JAXBElement<String> createPofrResponse2DataContractDiagnostics(String value) {
        return new JAXBElement<String>(_PofrResponse2DataContractDiagnostics_QNAME, String.class, PofrResponse2DataContract.class, value);
    }

}
