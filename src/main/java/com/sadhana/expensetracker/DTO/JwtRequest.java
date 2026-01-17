package com.sadhana.expensetracker.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class JwtRequest {

    private String username;
    private String password;
}
