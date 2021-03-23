package com.wefox.paymentverification.service.utils.error;

import com.wefox.paymentverification.service.domain.ErrorJson;
import org.springframework.stereotype.Component;

@Component
public class ErrorJsonCreator {
    public ErrorJson createDatabaseError(String paymentId, String errorDescription) {
        ErrorJson errorJson = new ErrorJson();

        errorJson.setPaymentId(paymentId);
        errorJson.setError(ErrorEnum.DATABASE.getError());
        errorJson.setErrorDescription(errorDescription);

        return errorJson;
    }

    public ErrorJson createNetworkError(String paymentId, String errorDescription) {
        ErrorJson errorJson = new ErrorJson();

        errorJson.setPaymentId(paymentId);
        errorJson.setError(ErrorEnum.NETWORK.getError());
        errorJson.setErrorDescription(errorDescription);

        return errorJson;
    }

    public ErrorJson createOtherError(String paymentId, String errorDescription) {
        ErrorJson errorJson = new ErrorJson();

        errorJson.setPaymentId(paymentId);
        errorJson.setError(ErrorEnum.OTHER.getError());
        errorJson.setErrorDescription(errorDescription);

        return errorJson;
    }
}
