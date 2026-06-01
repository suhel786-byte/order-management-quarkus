package com.mintifi.ordermanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreateOrderRequest {

    @NotNull(message = "Customer Id is required")
    private Long customerId;

    @NotBlank(message = "Order Number is required")
    private String orderNumber;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotBlank(message = "Status is required")
    private String status;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
