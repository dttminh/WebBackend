package com.roojai

import com.roojai.util.StringUtil
import grails.gorm.transactions.Transactional
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONException
import org.grails.web.json.JSONObject
import java.nio.charset.StandardCharsets;

import java.text.SimpleDateFormat

@Transactional
class RateUsService extends ServiceBase {

    def httpService
    def rateUsApiConfigKey = "rateUs.url"
    def getFeedbackPath = "feedback/getComment"
    def postCommentPath = "feedback/postComment"

    def getComment(int size, int page) {
        def URI = getSystemConfig(rateUsApiConfigKey) + getFeedbackPath + "?size=$size&page=$page"
        def requestRateUsHttpGet
        JSONObject root = new JSONObject()
        try {
            requestRateUsHttpGet = httpService.requestRateUsHttpGet(URI)
            root = new JSONObject(requestRateUsHttpGet)
        } catch (JSONException e) {
            JSONObject jsonException = new JSONObject();
            jsonException.put("size", size);
            jsonException.put("page", page);
            jsonException.put("requestRateUsHttpGet", requestRateUsHttpGet);
            log.error(jsonException.toString(), e);

            root.put("success", false)
            root.put("errorMessage", "Other Error")
        }
        return root
    }

    def postComment(def params) {
        JSONObject root = new JSONObject()
        def requestRateUsHttpPost
        try {
            if (params.opportunityNumber != null && params.opportunityNumber != "") {
                if (StringUtil.decrypt(params.opportunityNumber) == ""){
                    params.opportunityNumber = StringUtil.encrypt(params.opportunityNumber)
                }
                def URI = getSystemConfig(rateUsApiConfigKey) + postCommentPath
                requestRateUsHttpPost = httpService.requestRateUsHttpPost(URI, params)
                root = new JSONObject(requestRateUsHttpPost)
            }else{
                root.put("success", false)
                root.put("errorMessage", "Opportunity No. is required")
            }
        } catch (JSONException e) {
            JSONObject jsonException = new JSONObject();
            jsonException.put("params", params);
            jsonException.put("requestRateUsHttpPost", requestRateUsHttpPost);
            log.error(jsonException.toString(), e);

            root.put("success", false)
            root.put("errorMessage", "Other Error")
        }
        return root
    }

    String smartlink(String name, String orderref, JSONArray descriptions, JSONArray tags){
        String MARKERSTART = "|!"
        JSONObject MARKERS = new JSONObject()
        MARKERS.put("EMAIL", "B")
        MARKERS.put("NAME", "C")
        MARKERS.put("DATE", "D")
        MARKERS.put("FEEDBACKDATE", "E")
        MARKERS.put("DESCRIPTION", "F")
        MARKERS.put("LINK", "G")
        MARKERS.put("PRODUCTREF", "H")
        MARKERS.put("ORDERREF", "I")
        MARKERS.put("AMOUNT", "J")
        MARKERS.put("CURRENCY", "K")
        MARKERS.put("CUSTOMERREF", "M")
        MARKERS.put("MERCHANTIDENTIFIER", "N")
        MARKERS.put("TAG", "O")
        String url = systemconfigurationService.getValueByKey("feefo.SmartLinkAPIEndpoint")
        String sharedKey = systemconfigurationService.getValueByKey("feefo.SmartLinkSharedKey")
        String merchantIdentifier = systemconfigurationService.getValueByKey("feefo.SmartLinkMerchantIdentifier")
        String initialparams = ""
        String pattern = "yyyy-MM-dd"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern)
        String date = simpleDateFormat.format(new Date())

        initialparams += MARKERSTART + MARKERS['MERCHANTIDENTIFIER'] + merchantIdentifier
        initialparams += MARKERSTART + MARKERS['EMAIL'] + orderref.replace('-', '') + "@roojai.com"
        initialparams += MARKERSTART + MARKERS['DATE'] + date
        initialparams += MARKERSTART + MARKERS['NAME'] + name
        initialparams += MARKERSTART + MARKERS['ORDERREF'] + orderref

        for (int i = 0; i < descriptions.size(); i++) {
            initialparams += MARKERSTART + MARKERS['DESCRIPTION'] + (i + 1) + descriptions[i];
        }

        for (int i = 0; i < tags.size(); i++) {
            initialparams += MARKERSTART + MARKERS['TAG'] + (i + 1) + tags[i];
        }

        String key = DigestUtils.sha1Hex(DigestUtils.sha1Hex(initialparams + sharedKey) + sharedKey)

        String smartlink = url + Hex.encodeHexString(initialparams.getBytes(StandardCharsets.UTF_8)) + key

        return smartlink
    }
}
