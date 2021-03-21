package com.wefox.paymentverification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorModel {
    private String paymentId;
    private String error;
    private String errorDescription;
}
