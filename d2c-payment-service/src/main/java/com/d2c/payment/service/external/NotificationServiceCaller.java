package com.d2c.payment.service.external;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class NotificationServiceCaller {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Circuit Breaker with fallback and open state verification;
     * @param baseUrl
     * @param path
     * @return
     */
    @CircuitBreaker(name = "notificationGetCallCB", fallbackMethod = "fallbackNotificationGetCB")
    @Retry(name = "notificationGetCallCB", fallbackMethod = "fallbackRetryNotificationGetCB")
//    @RateLimiter(name = "notificationGetCallCB", fallbackMethod = "fallbackRateLimiterNotificationGetCB")
    public Map getAPITrigger(String baseUrl, String path) {
        log.info("triggering notification service get api");
        Map response = restTemplate.getForObject(baseUrl+path, Map.class);
        log.info("Received response from payment get call : {}", response);
        return response;
    }

    public Map fallbackNotificationGetCB(String baseUrl, String path, Exception exception) {
        log.error("Fallback for Payment Get Request.", exception);
        Map fallbackResponse = new HashMap();
        fallbackResponse.put("status", "Notification Service is not available");
        return fallbackResponse;
    }

    public Map fallbackRetryNotificationGetCB(String baseUrl, String path, Exception exception) {
        log.error("Fallback for Retry Payment Get Request.", exception);
        Map fallbackResponse = new HashMap();
        fallbackResponse.put("status", "Payment Service retrying Notification Service.");
        return fallbackResponse;
    }

    public Map fallbackRateLimiterNotificationGetCB(String baseUrl, String path, Exception exception) {
        log.error("Fallback for RateLimiter Payment Get Request.", exception);
        Map fallbackResponse = new HashMap();
        fallbackResponse.put("status", "Payment Service throwing excessive requests RateLimiter enabled Notification Service.");
        return fallbackResponse;
    }

}
