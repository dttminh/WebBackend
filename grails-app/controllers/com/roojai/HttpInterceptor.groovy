package com.roojai


class HttpInterceptor {
    public HttpInterceptor() {
        matchAll()
    }

    boolean before() {
        response.addHeader("Strict-Transport-Security", "max-age=15552000")
        response.addHeader("X-Content-Type-Options", "nosniff")
        response.addHeader("Permissions-Policy", "accelerometer=(), camera=(), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), payment=(), usb=(), speaker=()")
        response.addHeader("Referrer-Policy", "no-referrer")
        response.addHeader("X-XSS-Protection", "1; mode=block")
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
