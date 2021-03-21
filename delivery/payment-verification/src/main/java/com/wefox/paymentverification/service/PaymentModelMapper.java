package com.wefox.paymentverification.service;

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

    public PaymentModel mapPaymentToPaymentModel(Payment payment) {
        PaymentModel paymentModel = new PaymentModel();

        paymentModel.setAccountModel(getAccountModel(payment));
        paymentModel.setPaymentType(payment.getPaymentType());
        paymentModel.setCreditCard(payment.getCreditCard());
        paymentModel.setAmount(Integer.parseInt(payment.getAmount()));

        return paymentModel;
    }

    private AccountModel getAccountModel(Payment payment) {
        Optional<AccountModel> accountModel = accountRepository.findById(Integer.parseInt(payment.getAccountId()));

        return accountModel.orElse(null);
    }
}
