package com.wefox.paymentverification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class AccountModel {
    @Id
    @GeneratedValue
    private Integer accountId;
    private String email;
    private Date birthdate;
    private String lastPaymentDate;
    @CreationTimestamp
    private Date createdOn;
}
