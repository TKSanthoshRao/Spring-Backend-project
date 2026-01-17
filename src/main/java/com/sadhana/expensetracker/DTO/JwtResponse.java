package com.sadhana.expensetracker.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class JwtResponse {

    private String token;
    private String username;
}
