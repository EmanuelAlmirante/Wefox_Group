package com.wefox.paymentverification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefox.paymentverification.model.AccountModel;
import com.wefox.paymentverification.model.PaymentModel;
import com.wefox.paymentverification.repository.AccountRepository;
import com.wefox.paymentverification.service.domain.Payment;
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
        Payment payment = mapMessageToPayment(message);

        PaymentModel paymentModel = new PaymentModel();

        paymentModel.setAccountModel(getAccountModel(payment));
        paymentModel.setPaymentType(payment.getPaymentType());
        paymentModel.setCreditCard(payment.getCreditCard());
        paymentModel.setAmount(Integer.parseInt(payment.getAmount()));

        return paymentModel;
    }

    private Payment mapMessageToPayment(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Payment payment = mapper.readValue(message, Payment.class);

        return payment;
    }

    private AccountModel getAccountModel(Payment payment) {
        Optional<AccountModel> accountModel = accountRepository.findById(Integer.parseInt(payment.getAccountId()));

        return accountModel.orElse(null);
    }
}
