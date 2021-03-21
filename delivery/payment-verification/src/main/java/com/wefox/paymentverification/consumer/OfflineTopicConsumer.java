package com.wefox.paymentverification.consumer;

import com.wefox.paymentverification.model.PaymentModel;
import com.wefox.paymentverification.service.offline.OfflinePaymentService;
import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Getter
@Component
public class OfflineTopicConsumer {
    private static final String ONLINE_TOPIC = "offline";
    private static final String GROUP_ID = "payment";

    private final OfflinePaymentService offlinePaymentService;

    public OfflineTopicConsumer(OfflinePaymentService offlinePaymentService) {
        this.offlinePaymentService = offlinePaymentService;
    }

    @KafkaListener(topics = ONLINE_TOPIC, groupId = GROUP_ID)
    public void listen(PaymentModel message) {
        offlinePaymentService.savePayment(message);
    }
}
