package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class LoginRequestDTO {

    @NotNull(message = "The userName is required")
    private String userName;

    @NotNull(message = "The password is required")
    private String password;
}