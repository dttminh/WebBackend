package com.roojai

import org.grails.web.json.JSONObject


class LoggingInterceptor {

    LoggingInterceptor() {
        match(controller: "partner", action: /(callMeBack|buyNow)/)
    }

    boolean before() {
        JSONObject jsonParams = new JSONObject(params);
        JSONObject json = new JSONObject();
        json.put("controller", jsonParams.get("controller"));
        json.put("action", jsonParams.get("action"));
        jsonParams.remove("controller");
        jsonParams.remove("action");
        json.put("JSONRequest", request.JSON);
        json.put("params", jsonParams);
        json.put("origin", request.getHeader("origin"));
        json.put("userAgent", request.getHeader("user-agent"));
        json.put("referer", request.getHeader("referer"));
        log.error(json.toString())
        true
    }

    boolean after() {
        true
    }

    void afterView() {
        // no-op
    }
}
