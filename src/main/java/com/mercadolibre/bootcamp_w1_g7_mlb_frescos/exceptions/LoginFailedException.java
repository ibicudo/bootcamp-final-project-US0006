package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.exceptions;

public class LoginFailedException extends ApiException{
    public LoginFailedException(String detail){
        super("login_failed", detail, 402 );
    }
}
