package com.paymybuddy.paymybuddy.controller.dto;

import com.paymybuddy.paymybuddy.model.User;
import lombok.Data;

@Data
public class AddTransactionDTO {

    private String contactName;

    private double amount;

    private String description;

}
