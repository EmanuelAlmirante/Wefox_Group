package com.wefox.paymentverification.service.payment.online;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wefox.paymentverification.model.AccountModel;
import com.wefox.paymentverification.model.PaymentModel;
import com.wefox.paymentverification.provider.PaymentGatewayWebProvider;
import com.wefox.paymentverification.repository.AccountRepository;
import com.wefox.paymentverification.repository.PaymentRepository;
import com.wefox.paymentverification.service.domain.PaymentJson;
import com.wefox.paymentverification.service.utils.mapper.PaymentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.io.IOException;
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
public class OnlinePaymentServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PaymentMapper paymentMapper;
    @Mock
    private PaymentGatewayWebProvider paymentGatewayWebProvider;
    @InjectMocks
    private OnlinePaymentServiceImpl onlinePaymentService;

    @Test
    public void processOnlinePaymentSuccessfully() throws IOException, URISyntaxException {
        // Arrange
        String message = "{\"payment_id\": \"eae43e1d-4f78-4e6c-89cd-f3e6e449d968\", "
                         + "\"account_id\": 1946, "
                         + "\"payment_type\": \"online\", "
                         + "\"credit_card\": \"4143086865437619\", "
                         + "\"amount\": 23, "
                         + "\"delay\": 141}";

        PaymentModel paymentModel = createPaymentModel();
        PaymentJson paymentJson = createPaymentJson();
        AccountModel accountModel = createAccountModel();

        when(paymentMapper.mapPaymentMessageToPaymentJson(message)).thenReturn(paymentJson);
        when(paymentMapper.mapPaymentJsonToPaymentModel(paymentJson)).thenReturn(paymentModel);
        when(paymentGatewayWebProvider.validatePayment(any())).thenReturn(HttpStatus.OK);
        when(paymentRepository.save(any(PaymentModel.class))).thenReturn(paymentModel);
        when(paymentRepository.findById(anyString())).thenReturn(Optional.of(paymentModel));
        when(accountRepository.save(any(AccountModel.class))).thenReturn(accountModel);
        when(accountRepository.findById(any())).thenReturn(Optional.of(accountModel));

        // Act
        onlinePaymentService.processOnlinePayment(message);

        // Assert
        verify(paymentMapper, times(1)).mapPaymentMessageToPaymentJson(message);
        verify(paymentMapper, times(1)).mapPaymentJsonToPaymentModel(paymentJson);
        verify(paymentGatewayWebProvider, times(1)).validatePayment(any());
        verify(paymentRepository, times(1)).save(paymentModel);
        verify(paymentRepository, times(1)).findById(paymentModel.getPaymentId());
        verify(accountRepository, times(1)).save(any(AccountModel.class));
        verify(accountRepository, times(1)).findById(accountModel.getAccountId());
    }

    private PaymentModel createPaymentModel() {
        PaymentModel paymentModel = new PaymentModel();

        paymentModel.setPaymentId("eae43e1d-4f78-4e6c-89cd-f3e6e449d968");
        paymentModel.setAccountModel(createAccountModel());
        paymentModel.setPaymentType("online");
        paymentModel.setCreditCard("4143086865437619");
        paymentModel.setAmount(23);

        Date createdOn = new GregorianCalendar(2021, Calendar.MARCH, 1, 0, 0, 0).getTime();

        paymentModel.setCreatedOn(createdOn);

        return paymentModel;
    }

    private PaymentJson createPaymentJson() {
        PaymentJson paymentJson = new PaymentJson();

        paymentJson.setPaymentId("eae43e1d-4f78-4e6c-89cd-f3e6e449d968");
        paymentJson.setAccountId("1946");
        paymentJson.setPaymentType("online");
        paymentJson.setCreditCard("4143086865437619");
        paymentJson.setAmount("23");

        return paymentJson;
    }

    private AccountModel createAccountModel() {
        AccountModel accountModel = new AccountModel();

        accountModel.setAccountId(1946);
        accountModel.setEmail("test@email.com");

        Date birthdate = new GregorianCalendar(1990, Calendar.MARCH, 1, 0, 0, 0).getTime();

        accountModel.setBirthdate(birthdate);

        Date createdOn = new GregorianCalendar(2021, Calendar.MARCH, 1, 0, 0, 0).getTime();

        accountModel.setCreatedOn(createdOn);

        return accountModel;
    }

}