package com.wefox.paymentverification.service.utils.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefox.paymentverification.model.AccountModel;
import com.wefox.paymentverification.model.PaymentModel;
import com.wefox.paymentverification.repository.AccountRepository;
import com.wefox.paymentverification.service.domain.PaymentJson;
import com.wefox.paymentverification.service.utils.PaymentEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class PaymentMapper {
    private final AccountRepository accountRepository;

    @Autowired
    public PaymentMapper(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public PaymentModel mapPaymentMessageToPaymentModel(String message) throws JsonProcessingException {
        return mapPaymentJsonToPaymentModel(mapPaymentMessageToPaymentJson(message));
    }

    public PaymentJson mapPaymentMessageToPaymentJson(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(message, PaymentJson.class);
    }

    public PaymentModel mapPaymentJsonToPaymentModel(PaymentJson paymentJson) {
        PaymentModel paymentModel = new PaymentModel();

        paymentModel.setPaymentId(paymentJson.getPaymentId());
        paymentModel.setAccountModel(getAccountModel(paymentJson));
        paymentModel.setPaymentType(paymentJson.getPaymentType());
        paymentModel.setCreditCard(paymentJson.getCreditCard());
        paymentModel.setAmount(Integer.parseInt(paymentJson.getAmount()));
        paymentModel.setCreatedOn(new Date());

        return paymentModel;
    }

    private AccountModel getAccountModel(PaymentJson paymentJson) {
        if (paymentJson.getPaymentType().equals(PaymentEnum.OFFLINE.getPaymentType())) {
            return null;
        } else {
            Optional<AccountModel> accountModel =
                    accountRepository.findById(Integer.parseInt(paymentJson.getAccountId()));

            return accountModel.orElse(null);
        }
    }
}
