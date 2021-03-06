package com.wefox.paymentverification.service.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentEnum {
    ONLINE("online"),
    OFFLINE("offline");

    private final String paymentType;
}
