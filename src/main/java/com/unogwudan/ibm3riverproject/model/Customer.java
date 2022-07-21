package com.unogwudan.ibm3riverproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {

     @JsonProperty
     private String customerId;
     @JsonProperty
     private String name;
     @JsonProperty
     private String phoneNumber;
     @JsonProperty
     private String accountId;
}
