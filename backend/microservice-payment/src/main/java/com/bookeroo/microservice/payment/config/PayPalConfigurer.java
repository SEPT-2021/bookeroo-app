package com.bookeroo.microservice.payment.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfigurer {

    @Value("${paypal.client.id}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;

    @Bean
    public PayPalEnvironment payPalEnvironment() {
        switch (mode) {
            case "live":
                return new PayPalEnvironment.Live(clientId, clientSecret);
            case "sandbox":
            default:
                return new PayPalEnvironment.Sandbox(clientId, clientSecret);
        }
    }

    @Bean
    public PayPalHttpClient payPalClient() {
        return new PayPalHttpClient(payPalEnvironment());
    }

}
