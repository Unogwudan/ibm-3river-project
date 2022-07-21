package com.unogwudan.ibm3riverproject.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerBalance {

    private String accountId;
    private String customerId;
    private double balance;
    private String phoneNumber;

}
