package com.roojai.radar.stub;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;

/**
 * This class was generated by Apache CXF 3.1.8
 * 2016-11-07T15:46:03.546+07:00
 * Generated source version: 3.1.8
 * 
 */
@WebService(targetNamespace = "http://towerswatson.com/rto/dpo/services/2010/01", name = "DpoService")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface DpoService {

    @WebMethod(operationName = "GetPof", action = "http://towerswatson.com/rto/dpo/services/2010/01/DpoServiceContract/GetPof")
    @Action(input = "http://towerswatson.com/rto/dpo/services/2010/01/DpoServiceContract/GetPof", output = "http://towerswatson.com/rto/dpo/services/2010/01/DpoService/GetPofResponse", fault = {@FaultAction(className = DpoServiceGetPofConfigurationFaultContractFaultFaultMessage.class, value = "http://towerswatson.com/rto/dpo/services/2010/01/DpoService/GetPofConfigurationFaultContractFault"), @FaultAction(className = DpoServiceGetPofSevereFaultContractFaultFaultMessage.class, value = "http://towerswatson.com/rto/dpo/services/2010/01/DpoService/GetPofSevereFaultContractFault")})
    @WebResult(name = "PofResponse", targetNamespace = "http://towerswatson.com/rto/dpo/services/2010/01", partName = "parameters")
    public PofResponse getPof(
        @WebParam(partName = "parameters", name = "PofRequest", targetNamespace = "http://towerswatson.com/rto/dpo/services/2010/01")
        PofRequest parameters
    ) throws DpoServiceGetPofConfigurationFaultContractFaultFaultMessage, DpoServiceGetPofSevereFaultContractFaultFaultMessage;

    @WebMethod(operationName = "GetPofWithId", action = "http://towerswatson.com/rto/dpo/services/2010/01/DpoServiceContract/GetPofWithId")
    @Action(input = "http://towerswatson.com/rto/dpo/services/2010/01/DpoServiceContract/GetPofWithId", output = "http://towerswatson.com/rto/dpo/services/2010/01/DpoService/GetPofWithIdResponse", fault = {@FaultAction(className = DpoServiceGetPofWithIdConfigurationFaultContractFaultFaultMessage.class, value = "http://towerswatson.com/rto/dpo/services/2010/01/DpoService/GetPofWithIdConfigurationFaultContractFault"), @FaultAction(className = DpoServiceGetPofWithIdSevereFaultContractFaultFaultMessage.class, value = "http://towerswatson.com/rto/dpo/services/2010/01/DpoService/GetPofWithIdSevereFaultContractFault")})
    @WebResult(name = "PofResponse2", targetNamespace = "http://towerswatson.com/rto/dpo/services/2010/01", partName = "parameters")
    public PofResponse2 getPofWithId(
        @WebParam(partName = "parameters", name = "PofRequestUsingMasterSet", targetNamespace = "http://towerswatson.com/rto/dpo/services/2010/01")
        PofRequestUsingMasterSet parameters
    ) throws DpoServiceGetPofWithIdConfigurationFaultContractFaultFaultMessage, DpoServiceGetPofWithIdSevereFaultContractFaultFaultMessage;
}