package com.wefox.paymentverification.service.payment.offline;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wefox.paymentverification.model.PaymentModel;
import com.wefox.paymentverification.repository.PaymentRepository;
import com.wefox.paymentverification.service.utils.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OfflinePaymentServiceImpl implements OfflinePaymentService {
    private static final String OFFLINE_TOPIC = "offline";
    private static final String GROUP_ID = "payment";

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Autowired
    public OfflinePaymentServiceImpl(PaymentRepository paymentRepository,
                                     PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    @KafkaListener(topics = OFFLINE_TOPIC, groupId = GROUP_ID)
    public void processOfflinePayment(String message) throws JsonProcessingException {
        PaymentModel paymentModel = paymentMapper.mapPaymentMessageToPaymentModel(message);

        savePaymentInDatabase(paymentModel);
    }

    private void savePaymentInDatabase(PaymentModel paymentModel) {
        paymentRepository.save(paymentModel);
    }
}
