package com.wefox.paymentverification.consumer;

import com.wefox.paymentverification.model.PaymentModel;
import com.wefox.paymentverification.service.online.OnlinePaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OnlineTopicConsumer {
    private static final String ONLINE_TOPIC = "online";
    private static final String GROUP_ID = "payment";

    private final OnlinePaymentService onlinePaymentService;

    public OnlineTopicConsumer(OnlinePaymentService onlinePaymentService) {
        this.onlinePaymentService = onlinePaymentService;
    }

    @KafkaListener(topics = ONLINE_TOPIC, groupId = GROUP_ID)
    private void listen(PaymentModel message) {

    }
}
