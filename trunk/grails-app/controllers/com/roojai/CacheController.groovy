package com.roojai

import grails.gorm.transactions.Transactional;
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class CacheController {
    def grailsCacheAdminService
    def grailsCacheManager

    def index() {
        render grailsCacheManager.getCacheNames()
    }

    def view(){
        def cacheName = params.cacheName
        render grailsCacheManager.getCache(cacheName).getAllKeys()
    }

    def delete(){
        def cacheName = params.cacheName
        if( cacheName.equals("all") ){
            grailsCacheAdminService.clearAllCaches()
        }else{
            grailsCacheAdminService.clearCache(cacheName)
        }
        render "clear success"
    }
}
