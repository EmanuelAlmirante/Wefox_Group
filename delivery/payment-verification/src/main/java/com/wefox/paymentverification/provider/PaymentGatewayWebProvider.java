package com.wefox.paymentverification.provider;

import com.wefox.paymentverification.service.domain.PaymentJson;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@AllArgsConstructor
public class PaymentGatewayWebProvider {
    private static final String PAYMENT_GATEWAY_PROVIDER_URL = "http://localhost:9000/payment";

    private final WebClient webClient;

    public HttpStatus validatePayment(PaymentJson paymentJson) throws URISyntaxException {
        return webClient
                .post()
                .uri(new URI(PAYMENT_GATEWAY_PROVIDER_URL))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(paymentJson)
                .exchangeToMono(clientResponse -> Mono.just(clientResponse.statusCode()))
                .blockOptional()
                .get();
    }
}
