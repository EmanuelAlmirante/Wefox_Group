package com.wefox.paymentverification.service.offline;

import com.wefox.paymentverification.model.AccountModel;
import com.wefox.paymentverification.model.PaymentModel;
import com.wefox.paymentverification.repository.AccountRepository;
import com.wefox.paymentverification.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OfflinePaymentServiceImpl implements OfflinePaymentService {
    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public OfflinePaymentServiceImpl(AccountRepository accountRepository, PaymentRepository paymentRepository) {
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void savePayment(PaymentModel paymentModel) {
        paymentRepository.save(paymentModel);

        updateAccount(paymentModel);
    }

    private void updateAccount(PaymentModel paymentModel) {
        Optional<AccountModel> accountModel = accountRepository.findById(paymentModel.getAccountModel().getAccountId());

        accountModel.ifPresent(model -> model.setLastPaymentDate(paymentModel.getCreatedDate().toString()));
    }
}
