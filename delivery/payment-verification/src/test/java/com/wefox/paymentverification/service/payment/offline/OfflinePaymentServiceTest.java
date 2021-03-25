package com.wefox.paymentverification.service.payment.offline;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wefox.paymentverification.model.AccountModel;
import com.wefox.paymentverification.model.PaymentModel;
import com.wefox.paymentverification.repository.AccountRepository;
import com.wefox.paymentverification.repository.PaymentRepository;
import com.wefox.paymentverification.service.utils.mapper.PaymentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfflinePaymentServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PaymentMapper paymentMapper;
    @InjectMocks
    private OfflinePaymentServiceImpl offlinePaymentService;

    @Test
    public void processOfflinePaymentSuccessfully() throws JsonProcessingException, URISyntaxException {
        // Arrange
        String message = "{\"payment_id\": \"ca0f7831-e53e-44fd-8645-c686dd2a6d99\", "
                         + "\"account_id\": 984, "
                         + "\"payment_type\": \"offline\", "
                         + "\"credit_card\": \"\", "
                         + "\"amount\": 52, "
                         + "\"delay\": 382}";

        PaymentModel paymentModel = createPaymentModel();
        AccountModel accountModel = createAccountModel();

        when(paymentMapper.mapPaymentMessageToPaymentModel(message)).thenReturn(paymentModel);
        when(paymentRepository.save(any(PaymentModel.class))).thenReturn(paymentModel);
        when(paymentRepository.findById(anyString())).thenReturn(Optional.of(paymentModel));
        when(accountRepository.save(any(AccountModel.class))).thenReturn(accountModel);
        when(accountRepository.findById(any())).thenReturn(Optional.of(accountModel));

        // Act
        offlinePaymentService.processOfflinePayment(message);

        // Assert
        verify(paymentMapper, times(1)).mapPaymentMessageToPaymentModel(message);
        verify(paymentRepository, times(1)).save(paymentModel);
        verify(paymentRepository, times(1)).findById(paymentModel.getPaymentId());
        verify(accountRepository, times(1)).save(any(AccountModel.class));
        verify(accountRepository, times(1)).findById(accountModel.getAccountId());
    }

    private PaymentModel createPaymentModel() {
        PaymentModel paymentModel = new PaymentModel();

        paymentModel.setPaymentId("ca0f7831-e53e-44fd-8645-c686dd2a6d99");
        paymentModel.setAccountModel(createAccountModel());
        paymentModel.setPaymentType("offline");
        paymentModel.setCreditCard("");
        paymentModel.setAmount(52);

        Date createdOn = new GregorianCalendar(2021, Calendar.MARCH, 1, 0, 0, 0).getTime();

        paymentModel.setCreatedOn(createdOn);

        return paymentModel;
    }

    private AccountModel createAccountModel() {
        AccountModel accountModel = new AccountModel();

        accountModel.setAccountId(984);
        accountModel.setEmail("test@email.com");

        Date birthdate = new GregorianCalendar(1990, Calendar.MARCH, 1, 0, 0, 0).getTime();

        accountModel.setBirthdate(birthdate);

        Date createdOn = new GregorianCalendar(2021, Calendar.MARCH, 1, 0, 0, 0).getTime();

        accountModel.setCreatedOn(createdOn);

        return accountModel;
    }
}
