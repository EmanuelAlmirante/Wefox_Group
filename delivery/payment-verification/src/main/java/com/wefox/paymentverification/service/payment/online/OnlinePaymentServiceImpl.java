package com.wefox.paymentverification.service.payment.online;

import com.wefox.paymentverification.model.AccountModel;
import com.wefox.paymentverification.model.PaymentModel;
import com.wefox.paymentverification.provider.ErrorLogWebProvider;
import com.wefox.paymentverification.provider.PaymentGatewayWebProvider;
import com.wefox.paymentverification.repository.AccountRepository;
import com.wefox.paymentverification.repository.PaymentRepository;
import com.wefox.paymentverification.service.domain.ErrorJson;
import com.wefox.paymentverification.service.domain.PaymentJson;
import com.wefox.paymentverification.service.utils.error.ErrorJsonCreator;
import com.wefox.paymentverification.service.utils.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

@Service
public class OnlinePaymentServiceImpl implements OnlinePaymentService {
    private static final String ONLINE_TOPIC = "online";
    private static final String GROUP_ID = "payment";

    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentGatewayWebProvider paymentGatewayWebProvider;
    private final ErrorLogWebProvider errorLogWebProvider;
    private final ErrorJsonCreator errorJsonCreator;

    @Autowired
    public OnlinePaymentServiceImpl(AccountRepository accountRepository,
                                    PaymentRepository paymentRepository,
                                    PaymentMapper paymentMapper,
                                    PaymentGatewayWebProvider paymentGatewayWebProvider,
                                    ErrorLogWebProvider errorLogWebProvider,
                                    ErrorJsonCreator errorJsonCreator) {
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.paymentGatewayWebProvider = paymentGatewayWebProvider;
        this.errorLogWebProvider = errorLogWebProvider;
        this.errorJsonCreator = errorJsonCreator;
    }

    @Override
    @KafkaListener(topics = ONLINE_TOPIC, groupId = GROUP_ID)
    public void processOnlinePayment(String message) throws IOException, URISyntaxException {
        PaymentJson paymentJson = paymentMapper.mapPaymentMessageToPaymentJson(message);

        if (isPaymentValid(paymentJson)) {
            PaymentModel paymentModel = paymentMapper.mapPaymentJsonToPaymentModel(paymentJson);

            savePaymentInDatabase(paymentModel);

            updateAccount(paymentModel);
        } else {
            ErrorJson errorJson =
                    errorJsonCreator.createNetworkError(paymentJson.getPaymentId(), "Payment is not valid.");

            logErrorInLogSystem(errorJson);
        }
    }

    private boolean isPaymentValid(PaymentJson paymentJson) throws URISyntaxException {
        HttpStatus isValidPaymentHttpStatus = paymentGatewayWebProvider.validatePayment(paymentJson);

        return isValidPaymentHttpStatus.is2xxSuccessful();
    }

    private void savePaymentInDatabase(PaymentModel paymentModel) {
        paymentRepository.save(paymentModel);
    }

    private void updateAccount(PaymentModel paymentModel) throws URISyntaxException {
        AccountModel accountModel = paymentModel.getAccountModel();

        if (accountModel != null) {
            Date updatedLastPaymentDate = getPaymentCreatedOnStringDate(paymentModel);

            accountModel.setLastPaymentDate(updatedLastPaymentDate);

            saveAccountInDatabase(accountModel);

            accountRepository.findById(accountModel.getAccountId());
        } else {
            ErrorJson errorJson =
                    errorJsonCreator.createDatabaseError(paymentModel.getPaymentId(),
                                                         "There is no account associated with the payment.");

            logErrorInLogSystem(errorJson);
        }
    }

    private Date getPaymentCreatedOnStringDate(PaymentModel paymentModel) {
        return paymentRepository.findById(paymentModel.getPaymentId()).get().getCreatedOn();
    }

    private void saveAccountInDatabase(AccountModel accountModel) {
        accountRepository.save(accountModel);
    }

    private void logErrorInLogSystem(ErrorJson errorJson) throws URISyntaxException {
        errorLogWebProvider.logError(errorJson);
    }
}
