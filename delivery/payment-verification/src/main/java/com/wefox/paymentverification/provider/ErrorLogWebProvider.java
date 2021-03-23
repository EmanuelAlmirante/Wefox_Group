package com.wefox.paymentverification.provider;

import com.wefox.paymentverification.service.domain.ErrorJson;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@AllArgsConstructor
public class ErrorLogWebProvider {
    private static final String ERROR_LOG_PROVIDER_URL = "http://localhost:9000/log";

    private final WebClient webClient;

    public void logError(ErrorJson errorJson) throws URISyntaxException {
        webClient.post()
                 .uri(new URI(ERROR_LOG_PROVIDER_URL))
                 .contentType(MediaType.APPLICATION_JSON)
                 .bodyValue(errorJson);
    }
}
