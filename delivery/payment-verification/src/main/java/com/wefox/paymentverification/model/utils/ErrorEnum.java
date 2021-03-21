package com.wefox.paymentverification.model.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnum {
    DATABASE("database"),
    NETWORK("network"),
    OTHER("other");

    private final String error;
}
