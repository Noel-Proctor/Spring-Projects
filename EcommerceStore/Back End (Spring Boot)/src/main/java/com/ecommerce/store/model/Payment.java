package com.ecommerce.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @NotBlank
    @Size(min = 4, message= "Payment method must contain atleast 4 characters")
    private String paymentMethod;

    @OneToOne(mappedBy = "payment", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Order order;

    private String pgPaymentId;
    private String pgStatus;
    private String pgResponseMessage;
    private String pgName;

    public Payment(String pgName, String pgResponseMessage, String pgStatus, String pgPaymentId, Order order, String paymentMethod) {
        this.pgName = pgName;
        this.pgResponseMessage = pgResponseMessage;
        this.pgStatus = pgStatus;
        this.pgPaymentId = pgPaymentId;
        this.order = order;
        this.paymentMethod = paymentMethod;
    }
}
