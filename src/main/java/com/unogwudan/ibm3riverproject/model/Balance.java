package com.unogwudan.ibm3riverproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Balance {

    @JsonProperty
    private String accountId;
    @JsonProperty
    private String balanceId;
    @JsonProperty
    private double balance;
}
