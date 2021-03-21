package com.wefox.paymentverification.service.offline;

import com.wefox.paymentverification.model.PaymentModel;

public interface OfflinePaymentService {
    void savePayment(PaymentModel paymentModel);
}
