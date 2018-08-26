package com.batrom.budgetcalculator.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    private String email;
    private String login;
    private String password;
}
