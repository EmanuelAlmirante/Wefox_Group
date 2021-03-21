package com.wefox.paymentverification.service.online;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OnlinePaymentService {
    void processOnlinePayment(String message) throws JsonProcessingException;
}
