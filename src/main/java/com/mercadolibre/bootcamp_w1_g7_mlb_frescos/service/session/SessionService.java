package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.session;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.LoginRequestDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.LoginResponseDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.exceptions.LoginFailedException;

public interface SessionService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws LoginFailedException;
}