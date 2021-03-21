package com.wefox.paymentverification.repository;

import com.wefox.paymentverification.model.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, String> {
}
