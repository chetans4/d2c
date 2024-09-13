package com.d2c.notification.service.external;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class PaymentServiceCaller {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Circuit Breaker with fallback and open state verification;
     *
     * @param baseUrl
     * @param path
     * @return
     */
    @CircuitBreaker(name = "paymentGetCallCB", fallbackMethod = "fallbackPaymentGetCB")
    public Map getAPITrigger(String baseUrl, String path) {
        log.info("triggering payment service get api");
        Map response = restTemplate.getForObject(baseUrl + path, Map.class);
        log.info("Received response from payment get call : {}", response);
        return response;
    }

    public Map fallbackPaymentGetCB(String baseUrl, String path, Exception exception) {
        log.error("Fallback for Payment Get Request.", exception);
        Map fallbackResponse = new HashMap();
        fallbackResponse.put("status", "Payment Service is not available");
        return fallbackResponse;
    }

}
