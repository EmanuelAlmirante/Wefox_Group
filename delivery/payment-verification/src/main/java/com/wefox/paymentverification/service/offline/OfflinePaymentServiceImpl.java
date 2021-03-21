package com.wefox.paymentverification.service.offline;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wefox.paymentverification.model.PaymentModel;
import com.wefox.paymentverification.repository.PaymentRepository;
import com.wefox.paymentverification.service.PaymentModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OfflinePaymentServiceImpl implements OfflinePaymentService {
    private static final String OFFLINE_TOPIC = "offline";
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
    public void processOfflinePayment(String message) throws JsonProcessingException {
        PaymentModel paymentModel = paymentModelMapper.mapPaymentToPaymentModel(message);

        paymentRepository.save(paymentModel);
    }
}
