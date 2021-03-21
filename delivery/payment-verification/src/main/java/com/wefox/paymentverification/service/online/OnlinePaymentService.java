package com.wefox.paymentverification.service.online;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OnlinePaymentService {
    void savePayment(String message) throws JsonProcessingException;
}
