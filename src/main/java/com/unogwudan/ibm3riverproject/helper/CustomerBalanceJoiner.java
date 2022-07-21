package com.unogwudan.ibm3riverproject.helper;

import com.unogwudan.ibm3riverproject.model.Balance;
import com.unogwudan.ibm3riverproject.model.Customer;
import com.unogwudan.ibm3riverproject.model.CustomerBalance;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class CustomerBalanceJoiner implements ValueJoiner<Customer, Balance, CustomerBalance> {

        public CustomerBalance apply(Customer customer, Balance balance) {

            if (customer.getAccountId() == balance.getAccountId()) {
                return CustomerBalance.builder()
                        .accountId(customer.getAccountId())
                        .customerId(customer.getCustomerId())
                        .balance(balance.getBalance())
                        .phoneNumber(customer.getPhoneNumber())
                        .build();
            } else {
                return null;
            }
        }
}
