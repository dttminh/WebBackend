package com.roojai

import com.roojai.saleforce.ProductType
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

@Transactional
class OccupationService {

    JSONArray getOccupations(String productType, String insurer) {
        if(productType == null){
            productType = ProductType.MotorCar
        }
        if(insurer == null){
            insurer = "KPI"
        }

        List occupations =  Occupation.executeQuery("select occupationGroup, nameEn, nameTh from Occupation o " +
                "where o.productType = :productType and o.insurer = :insurer",
                [productType: productType, insurer: insurer])

        JSONArray items = new JSONArray()
        for(int i=0; i<occupations.size(); i++){
            JSONObject item = new JSONObject()
            item.put("occupationGroup", occupations[i][0])
            item.put("nameEn", occupations[i][1])
            item.put("nameTh", occupations[i][2])
            item.put("id", i)
            items.add(item)
        }

        return items
    }

    JSONObject getOccupation(String nameEn, String productType, String insurer) {
        JSONObject response = new JSONObject()
        if (nameEn != null) {

            if(productType == null){
                productType = ProductType.MotorCar
            }
            if(insurer == null){
                insurer = "KPI"
            }

            List occupations =  Occupation.executeQuery("select occupationGroup, nameEn, nameTh from Occupation o " +
                    "where o.nameEn = :nameEn and o.productType = :productType and o.insurer = :insurer",
                    [nameEn: nameEn, productType: productType, insurer: insurer, max: 1])

            if(occupations.size()>0){
                response.put("occupationGroup", occupations[0][0])
                response.put("nameEn", occupations[0][1])
                response.put("nameTh", occupations[0][2])
            }

        }

        return response
    }
}
