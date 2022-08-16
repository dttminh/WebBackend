package com.roojai

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller:"home")
        "500"(view:"/error")
        "404"(view:"/notFound")

		"/api/about"(controller: "home", action: "about")
		"/api/quotations/$action?/"(controller: "quotation")
		"/api/policies/$action/$id?"(controller: "policy")
		"/api/payments/$action/$id?"(controller: "payment")
		"/api/rateus/$action/$id?"(controller: "rateus")
		"/api/promotion/$action/$id?"(controller: "Promotion")
		"/api/OTP/generateOTP"(controller:"OTP", action: "generateOTP", method: "POST")
		"/api/OTP/verifyOTP"(controller:"OTP", action: "verifyOTP", method: "POST")
		"/api/cms-emailsubmission"(controller: "CMSemailNotify")
		"/api/partner/getBrokerDetails"(controller: "Partner", action: "getBrokerDetails", method: "POST")
		"/api/customer/$action"(controller: "Customer")
		"/api/eventLog"(controller: "eventLog")
		"/api/ocr/$action"(controller: "OCR")
		"/api/configurations"(resources:"configuration", includes:["index"])
		"/api/agentInfo/$action"(controller: "agentInfo")

		// dropdown data
		"/api/postals"(resources:"postal",includes:["index"])
		"/api/redbooks"(resources:"redBook",includes:["index"])
		"/api/occupations"(resources:"occupation",includes:["index"])
		"/api/insurers"(resources:"insurer",includes:["index"])
		"/api/cover-types"(resources:"coverType",includes:["index"])
		"/api/countries"(resources:"country",includes:["index"])
		"/api/log"(controller:"log", action: "index", method: "POST")
    }
}