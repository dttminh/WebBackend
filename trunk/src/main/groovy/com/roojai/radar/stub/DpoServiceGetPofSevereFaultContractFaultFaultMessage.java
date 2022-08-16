
package com.roojai.radar.stub;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.8
 * 2016-11-07T15:46:03.529+07:00
 * Generated source version: 3.1.8
 */

@WebFault(name = "SevereFaultContract", targetNamespace = "http://towerswatson.com/rto/dpo/types/2010/01")
public class DpoServiceGetPofSevereFaultContractFaultFaultMessage extends Exception {
    
    private com.roojai.radar.stub.SevereFaultContract severeFaultContract;

    public DpoServiceGetPofSevereFaultContractFaultFaultMessage() {
        super();
    }
    
    public DpoServiceGetPofSevereFaultContractFaultFaultMessage(String message) {
        super(message);
    }
    
    public DpoServiceGetPofSevereFaultContractFaultFaultMessage(String message, Throwable cause) {
        super(message, cause);
    }

    public DpoServiceGetPofSevereFaultContractFaultFaultMessage(String message, com.roojai.radar.stub.SevereFaultContract severeFaultContract) {
        super(message);
        this.severeFaultContract = severeFaultContract;
    }

    public DpoServiceGetPofSevereFaultContractFaultFaultMessage(String message, com.roojai.radar.stub.SevereFaultContract severeFaultContract, Throwable cause) {
        super(message, cause);
        this.severeFaultContract = severeFaultContract;
    }

    public com.roojai.radar.stub.SevereFaultContract getFaultInfo() {
        return this.severeFaultContract;
    }
}
