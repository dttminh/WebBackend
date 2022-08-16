
package com.roojai.radar.stubMotor;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "SevereFaultContract", targetNamespace = "http://towerswatson.com/rto/dpo/types/2010/01")
public class DpoServiceGetPofWithIdSevereFaultContractFaultFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private SevereFaultContract faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public DpoServiceGetPofWithIdSevereFaultContractFaultFaultMessage(String message, SevereFaultContract faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public DpoServiceGetPofWithIdSevereFaultContractFaultFaultMessage(String message, SevereFaultContract faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.roojai.SevereFaultContract
     */
    public SevereFaultContract getFaultInfo() {
        return faultInfo;
    }

}
