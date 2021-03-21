package com.wefox.paymentverification.service.online;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wefox.paymentverification.model.AccountModel;
import com.wefox.paymentverification.model.PaymentModel;
import com.wefox.paymentverification.repository.AccountRepository;
import com.wefox.paymentverification.repository.PaymentRepository;
import com.wefox.paymentverification.service.PaymentModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OnlinePaymentServiceImpl implements OnlinePaymentService {
    private static final String ONLINE_TOPIC = "online";
    private static final String GROUP_ID = "payment";

    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentModelMapper paymentModelMapper;

    @Autowired
    public OnlinePaymentServiceImpl(AccountRepository accountRepository,
                                    PaymentRepository paymentRepository,
                                    PaymentModelMapper paymentModelMapper) {
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
        this.paymentModelMapper = paymentModelMapper;
    }

    @Override
    @KafkaListener(topics = ONLINE_TOPIC, groupId = GROUP_ID)
    public void processOnlinePayment(String message) throws JsonProcessingException {
        PaymentModel paymentModel = paymentModelMapper.mapPaymentToPaymentModel(message);

        if (isPaymentValid()) {
            paymentRepository.save(paymentModel);

            updateAccount(paymentModel);
        } else {
            // error log
        }
    }

    private boolean isPaymentValid() {


        return false;
    }

    private void updateAccount(PaymentModel paymentModel) {
        Optional<AccountModel> accountModel = accountRepository.findById(paymentModel.getAccountModel().getAccountId());

        accountModel.ifPresent(model -> model.setLastPaymentDate(paymentModel.getCreatedOn().toString()));
    }
}
