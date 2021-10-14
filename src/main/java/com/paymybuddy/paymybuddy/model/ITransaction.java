package com.paymybuddy.paymybuddy.model;

import java.time.LocalDateTime;

public interface ITransaction {

    LocalDateTime getDate();

    boolean isInternal();

}
