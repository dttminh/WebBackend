package com.roojai

import com.roojai.util.StringUtil
import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.lang.exception.ExceptionUtils
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.grails.web.json.JSONObject

@Secured(['ROLE_API'])
class RateusController {
	static responseFormats = ['json']

	def RateUsService
	def SaleforceService
	def LogService

	def process(){
		def isRateus = params.isRateus
		def oppNumber = params.opportunityNumber
		JSONObject rt = new JSONObject()
		try{
			if(oppNumber != ""){
				def opportunityNumber = isRateus.equals("true") ?  StringUtil.decrypt(oppNumber):oppNumber
				def externalPictureURL = params.externalPictureURL
				def socialNetwork = params.socialNetwork
				def socialMediaFName = params.socialMediaFName
				def socialMediaLName = params.socialMediaLName
				def profileURL = params.profileURL
				def comment = params.commentText
				def overallRating = params.int('ratingOverall')
				def websiteRating = params.ratingWebsite == "" ? null:params.int('ratingWebsite')
				def productRating = params.ratingProductAndPricing  == "" ? null:params.int('ratingProductAndPricing')
				def callCenterRating = params.ratingCallCenter   == "" ? null:params.int('ratingCallCenter')
				def now = new Date()
				def postDate = now.format("yyyy-MM-dd",TimeZone.getTimeZone('Asia/Bangkok'))
				JSONObject jsonToken = SaleforceService.getAccessToken()
				def token = jsonToken.access_token
				def url = jsonToken.instance_url +"/services/apexrest/SocialMediaFeedback"
				def tokenType = jsonToken.token_type

				JSONObject root = new JSONObject()
				JSONObject socialFeedbackField = new JSONObject()
				socialFeedbackField.put("externalPictureURL",externalPictureURL)
				socialFeedbackField.put("contractNumber",opportunityNumber)
				socialFeedbackField.put("socialNetwork",socialNetwork)
				socialFeedbackField.put("socialMediaFName",socialMediaFName)
				socialFeedbackField.put("socialMediaLName",socialMediaLName)
				socialFeedbackField.put("profileURL",profileURL)
				socialFeedbackField.put("comment",comment)
				socialFeedbackField.put("postDate",postDate)
				socialFeedbackField.put("websiteRating",websiteRating)
				socialFeedbackField.put("overallRating",overallRating)
				socialFeedbackField.put("productRating",productRating)
				socialFeedbackField.put("callCenterRating",callCenterRating)
				root.put("socialFeedbackField",socialFeedbackField)

				HttpClient client = HttpClientBuilder.create().build()
				HttpPost post = new HttpPost(url)
				StringEntity params = new StringEntity(root.toString(),"utf-8")
				post.setEntity(params)
				post.addHeader("Authorization",tokenType+" "+token)
				post.addHeader("content-type", "application/json")
				post.addHeader("charset", "UTF-8")
				HttpResponse response = client.execute(post)
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"))
				StringBuffer result = new StringBuffer()
				String line = ""
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				rt.put("success",result.toString());

				RateUsData rs = new RateUsData(opportunityNumber:opportunityNumber,externalPictureURL:externalPictureURL,socialNetwork:socialNetwork,socialMediaFName:socialMediaFName,socialMediaLName:socialMediaLName,profileURL:profileURL,comment:comment,overallRating:overallRating,websiteRating:websiteRating,productRating:productRating,callCenterRating:callCenterRating,postDate:postDate,socialMediaFeedbackResult:result.toString(),created:new Date())
				rs.save(flush:true)
			}else{
				rt.put("success","false")
			}
		}catch(Exception e){
			log.error(e.getMessage(), e)

			rt.put("success","false")
		}
		respond rt
	}		

	def postComment(){
		JSONObject root = new JSONObject()
		def opportunityNumber,externalPictureURL,socialNetwork,socialMediaFName,profileURL,comment,overallRating,websiteRating,productRating,callCenterRating,socialMediaLName
		try {
			opportunityNumber = StringUtil.decrypt(params.opportunityNumber)
			if(!opportunityNumber) opportunityNumber = params.opportunityNumber
			externalPictureURL = params.profilePictureURL
			socialNetwork = 'facebook'
			socialMediaFName = params.socialMediaFirstName
			socialMediaLName = params.socialMediaLastName
			profileURL = params.profileURL
			comment = params.commentText
			overallRating = params.int('overallRating')
			websiteRating = params.ratingWebsite == "" ? null : params.int('websiteRating')
			productRating = params.ratingProductAndPricing  == "" ? null : params.int('productRating')
			callCenterRating = params.ratingCallCenter   == "" ? null : params.int('callCenterRating')
			root = RateUsService.postComment(params)
		} catch (Exception e) {
			log.error(e.getMessage(), e)

			root.put("success", false)
			root.put("errorMessage", "Other Error")

			LogService.sendMail(ExceptionUtils.getStackTrace(e), "RateUs postComment Facebook");
		}finally{
			try{
				RateUsData rs = new RateUsData()
				rs.opportunityNumber = opportunityNumber
				rs.externalPictureURL = externalPictureURL
				rs.socialNetwork = socialNetwork
				rs.socialMediaFName = socialMediaFName
				rs.socialMediaLName = socialMediaLName
				rs.profileURL = profileURL
				rs.comment = comment
				rs.overallRating = overallRating
				rs.websiteRating = websiteRating
				rs.productRating = productRating
				rs.callCenterRating = callCenterRating
				rs.created = new Date()
				rs.postDate = new Date()
				rs.socialMediaFeedbackResult = false
				if( root.has("errorMessage") ) {
					rs.errorMessage = root.get("errorMessage")
					LogService.sendMail(root.get("errorMessage"), "RateUs postComment Facebook");
				}
				rs.save flush:true, failOnError: true
				rs.validate()
				if (rs.hasErrors()) {
					rs.errors.allErrors.each {
						log.error it
					}
					root.put('success', false)
					root.put('errorMessage', rs.errors.allErrors)
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e)

				root.put("success", false)
				root.put("errorMessage", "Other Error")

				LogService.sendMail(ExceptionUtils.getStackTrace(e), "RateUs postComment Facebook");
			}
		}

		respond root
	}

	def getComment(int size, int page) {
		JSONObject root = new JSONObject()
		try {
			root = RateUsService.getComment(size, page)
		} catch (Exception e) {
			log.error(e.getMessage(), e)
			root.put("success", false)
			root.put("errorMessage", "Other Error")
			LogService.sendMail(ExceptionUtils.getStackTrace(e), "RateUs getComment Facebook")
		}
		respond root
	}

	def smartlink(){
		if(request.JSON.name != null && !request.JSON.name.trim().isEmpty() &&
				request.JSON.orderref != null && !request.JSON.orderref.trim().isEmpty() &&
				request.JSON.descriptions != null && request.JSON.descriptions.size() > 0 &&
				request.JSON.tags != null && request.JSON.tags.size() > 0
		) {
			String smartlink = RateUsService.smartlink(request.JSON.name, request.JSON.orderref, request.JSON.descriptions, request.JSON.tags)
			JSONObject jsonResponse = new JSONObject()
			jsonResponse.put("smartlink", smartlink)

			respond(jsonResponse, status: smartlink == null || smartlink == "" ? 500 : 200)
		}else{
			respond([:], status: 400)
		}
	}
}
