package com.wefox.paymentverification.service.payment.online;

import java.io.IOException;
import java.net.URISyntaxException;

public interface OnlinePaymentService {
    void processOnlinePayment(String message) throws IOException, URISyntaxException;
}
