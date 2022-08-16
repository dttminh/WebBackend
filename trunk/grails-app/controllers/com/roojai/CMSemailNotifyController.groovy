package com.roojai

import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONObject

@Secured(['ROLE_API'])
class CMSemailNotifyController {
    static responseFormats = ['json']
    def CMSemailNotificationService

    def index() {
        JSONObject result = new JSONObject();

        if( !params.token || !params.email || !params.month ){
            result.put( "success",false);
            result.put( "message","invalid token");

            respond result
        }

        try{
            def token = AuthenticationToken.findWhere(tokenValue: params.token)

            if( !token ){
                result.put( "success",false);
                result.put( "message","token not found");

                respond result
            }

            token.delete(flush: true);

        }catch( Exception e ){
            result.put( "success",false);
            result.put( "message", e.getMessage() );

            respond result
        }


        try{
            JSONObject data = new JSONObject();
            data.put("email", params.email )
            data.put("month", params.month )
            data.put("lastname", params.lastname ? params.lastname : params.email )

            JSONObject response = CMSemailNotificationService.saveSubmission( data )

            respond response

        }catch( Exception e ){
            result.put( "success",false);
            result.put( "message", e.getMessage() );

            respond result
        }
    }
}
