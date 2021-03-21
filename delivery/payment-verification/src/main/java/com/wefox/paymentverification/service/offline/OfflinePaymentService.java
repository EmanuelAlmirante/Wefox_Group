package com.wefox.paymentverification.service.offline;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OfflinePaymentService {
    void processOfflinePayment(String message) throws JsonProcessingException;
}
