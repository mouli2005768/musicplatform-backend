package klu.controller;

import klu.modal.Payment;
import klu.repository.PaymentRepository;
import klu.modal.User;
import klu.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ POST: Make a new payment
    @PostMapping("/pay")
    public ResponseEntity<?> makePayment(@RequestBody Payment paymentRequest) {
        try {
            // ✅ Check if user exists by email
            Optional<User> userOpt = userRepository.findById(paymentRequest.getUserEmail());
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            User user = userOpt.get();

            // ✅ Determine amount
            double amount = 0.0;
            if (paymentRequest.getPlanType().equalsIgnoreCase("Premium")) {
                amount = 499.0;
            } else if (paymentRequest.getPlanType().equalsIgnoreCase("Gold")) {
                amount = 999.0;
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid plan type");
            }

            // ✅ Create payment record
            Payment payment = new Payment();
            payment.setUserEmail(user.getEmailid());
            payment.setUserName(user.getFullname());
            payment.setPhoneNumber(paymentRequest.getPhoneNumber());
            payment.setPlanType(paymentRequest.getPlanType());
            payment.setAmount(amount);
            payment.setPaymentStatus("Paid");
            payment.setPaymentDate(LocalDateTime.now());

            Payment saved = paymentRepository.save(payment);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment failed: " + e.getMessage());
        }
    }

    // ✅ GET: Check payment history for a user
    @GetMapping("/history/{emailid}")
    public List<Payment> getPaymentHistory(@PathVariable String emailid) {
        return paymentRepository.findByUserEmail(emailid);
    }

    // ✅ GET: Check if user has an active premium/gold plan
    @GetMapping("/status/{emailid}")
    public String checkPaymentStatus(@PathVariable String emailid) {
        Payment latestPayment = paymentRepository.findTopByUserEmailOrderByPaymentDateDesc(emailid);
        if (latestPayment != null && "Paid".equalsIgnoreCase(latestPayment.getPaymentStatus())) {
            return "User has an active " + latestPayment.getPlanType() + " plan";
        } else {
            return "User has not purchased any plan";
        }
    }
}
