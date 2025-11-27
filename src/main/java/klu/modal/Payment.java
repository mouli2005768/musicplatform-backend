package klu.modal;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email", nullable = false)
    private String userEmail;  // Reference to User.emailid

    @Column(name = "user_name", nullable = false)
    private String userName;   // Reference to User.fullname

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "plan_type", nullable = false)
    private String planType;   // "Premium" or "Gold"

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus; // "Paid" or "Pending"

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    public Payment() {}

    public Payment(String userEmail, String userName, String phoneNumber, String planType, double amount, String paymentStatus) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.planType = planType;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}
