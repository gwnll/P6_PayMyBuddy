package com.paymybuddy.paymybuddy.dto;

import lombok.Data;

@Data
public class AddTransactionDTO {

    private String contactName;

    private double amount;

    private String description;

}
