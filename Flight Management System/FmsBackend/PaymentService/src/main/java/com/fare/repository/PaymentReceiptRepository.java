package com.fare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fare.model.PaymentReceipt;

@Repository
public interface PaymentReceiptRepository extends JpaRepository<PaymentReceipt, String> {

	Optional<List<PaymentReceipt>> findByBookingId(int id);

}
