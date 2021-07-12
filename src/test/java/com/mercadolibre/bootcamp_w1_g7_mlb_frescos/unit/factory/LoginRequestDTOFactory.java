package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.unit.factory;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.LoginRequestDTO;

public class LoginRequestDTOFactory {
    static public LoginRequestDTO getLoginMock(){
        return new LoginRequestDTO("ton", "123456");
    }
}
