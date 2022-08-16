package com.roojai

import grails.gorm.transactions.Transactional

@Transactional
class AbTestingService {

    String getABTestingValue(String testingVersion, String productType){
        int countConfiguration = ABTestingConfiguration.countByTestingVersionAndProductTypeAndTestingPercentGreaterThan(testingVersion,productType, 0)
        String returnValue = ""
        def config
        def counterData

        if (countConfiguration == 0) {
            countConfiguration = ABTestingConfiguration.countByTestingVersionLikeAndProductTypeAndTestingPercentGreaterThan(testingVersion + "%",productType, 0)

            if (countConfiguration == 0) {
                config = ABTestingConfiguration.findAllByProductTypeAndTestingPercentGreaterThan(productType,0)
            } else {
                config = ABTestingConfiguration.findAllByTestingVersionLikeAndProductTypeAndTestingPercentGreaterThan(testingVersion + "%",productType, 0)
            }

            counterData = ABTestingCounter.executeQuery("from ABTestingCounter a where exists (select 1 from ABTestingConfiguration b where b.testingPercent > 0 and b.testingVersion = a.testingVersion) and a.productType = :productType",[productType: productType])

            BigInteger gcd = 0
            for (ABTestingConfiguration data : config) {
                if (gcd > 0) {
                    gcd = gcd.gcd(BigInteger.valueOf(data.testingPercent))
                } else {
                    gcd = data.testingPercent
                }
            }
            def a = gcd.intValue()
            def totalConfig = 0
            def total = 0
            def status = false
            if (config.size() > 0) {
                for (Object ob : config) {
                    totalConfig += ob.testingPercent / a
                }
                for (Object ob : config) {
                    for (Object obCounter : counterData) {
                        if (ob.testingVersion.equals(obCounter.testingVersion)) {
                            if (obCounter.count < (ob.testingPercent / a)) {
                                returnValue = ob.returnValue
                                status = true
                                total = total + obCounter.count + 1
                                obCounter.count = obCounter.count + 1
                                obCounter.summary = obCounter.summary + 1
                                obCounter.save(flush: true)
                                break
                            } else {
                                total = total + obCounter.count
                            }
                        }
                    }
                    if (total >= totalConfig) {
                        for (Object obCounter : counterData) {
                            obCounter.count = 0
                            obCounter.save(flush: true)
                        }
                    }
                    if (status) {
                        break
                    }
                }
            }
        } else {
            returnValue = testingVersion
        }

        return returnValue
    }
}