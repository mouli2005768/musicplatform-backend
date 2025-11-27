package klu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import klu.modal.Payment;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserEmail(String userEmail);
    Payment findTopByUserEmailOrderByPaymentDateDesc(String userEmail);
    long countByPaymentStatus(String paymentStatus);
}
