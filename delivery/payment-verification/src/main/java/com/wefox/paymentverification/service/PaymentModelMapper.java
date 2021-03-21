package com.wefox.paymentverification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefox.paymentverification.model.AccountModel;
import com.wefox.paymentverification.model.PaymentModel;
import com.wefox.paymentverification.model.utils.PaymentEnum;
import com.wefox.paymentverification.repository.AccountRepository;
import com.wefox.paymentverification.service.domain.PaymentJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentModelMapper {
    private final AccountRepository accountRepository;

    @Autowired
    public PaymentModelMapper(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public PaymentModel mapPaymentToPaymentModel(String message) throws JsonProcessingException {
        PaymentJson paymentJson = mapMessageToPayment(message);

        PaymentModel paymentModel = new PaymentModel();

        paymentModel.setAccountModel(getAccountModel(paymentJson));
        paymentModel.setPaymentType(paymentJson.getPaymentType());
        paymentModel.setCreditCard(paymentJson.getCreditCard());
        paymentModel.setAmount(Integer.parseInt(paymentJson.getAmount()));

        return paymentModel;
    }

    private PaymentJson mapMessageToPayment(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(message, PaymentJson.class);
    }

    private AccountModel getAccountModel(PaymentJson paymentJson) {
        if (paymentJson.getPaymentType().equals(PaymentEnum.OFFLINE.getPaymentType())) {
            return null;
        } else {
            Optional<AccountModel> accountModel = accountRepository.findById(Integer.parseInt(paymentJson.getAccountId()));

            return accountModel.orElse(null);
        }
    }
}
