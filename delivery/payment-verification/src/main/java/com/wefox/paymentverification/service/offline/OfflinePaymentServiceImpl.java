package com.wefox.paymentverification.service.offline;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefox.paymentverification.model.PaymentModel;
import com.wefox.paymentverification.repository.PaymentRepository;
import com.wefox.paymentverification.service.PaymentModelMapper;
import com.wefox.paymentverification.service.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OfflinePaymentServiceImpl implements OfflinePaymentService {
    private static final String OFFLINE_TOPIC = "online";
    private static final String GROUP_ID = "payment";

    private final PaymentRepository paymentRepository;
    private final PaymentModelMapper paymentModelMapper;

    @Autowired
    public OfflinePaymentServiceImpl(PaymentRepository paymentRepository,
                                     PaymentModelMapper paymentModelMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentModelMapper = paymentModelMapper;
    }

    @Override
    @KafkaListener(topics = OFFLINE_TOPIC, groupId = GROUP_ID)
    public void savePayment(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Payment payment = mapper.readValue(message, Payment.class);

        PaymentModel paymentModel = paymentModelMapper.mapPaymentToPaymentModel(payment);

        paymentRepository.save(paymentModel);
    }
}
