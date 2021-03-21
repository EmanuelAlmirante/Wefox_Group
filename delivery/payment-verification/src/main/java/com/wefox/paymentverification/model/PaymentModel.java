package com.wefox.paymentverification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class PaymentModel {
    @Id
    @GeneratedValue
    private String paymentId;
    @OneToOne
    @JoinColumn(name = "accountId")
    private AccountModel accountModel;
    private String paymentType;
    private String creditCard;
    private Integer amount;
    @CreationTimestamp
    private Date createdOn;
}
