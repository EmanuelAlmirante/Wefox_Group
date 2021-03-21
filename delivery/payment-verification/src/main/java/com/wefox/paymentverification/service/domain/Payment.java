package com.wefox.paymentverification.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment {
    @JsonProperty(value = "payment_id")
    private String paymentId;
    @JsonProperty(value = "account_id")
    private String accountId;
    @JsonProperty(value = "payment_type")
    private String paymentType;
    @JsonProperty(value = "credit_card")
    private String creditCard;
    @JsonProperty(value = "amount")
    private String amount;
}
