package com.wefox.paymentverification.service.payment.offline;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.URISyntaxException;

public interface OfflinePaymentService {
    void processOfflinePayment(String message) throws JsonProcessingException, URISyntaxException;
}
