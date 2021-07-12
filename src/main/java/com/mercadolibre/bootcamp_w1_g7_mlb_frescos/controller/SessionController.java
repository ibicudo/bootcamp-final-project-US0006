package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.controller;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.LoginRequestDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.LoginResponseDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.session.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller responsible for authenticate a user
 */
@RestController
public class SessionController {
    @Autowired
    private  SessionService service;

    /**
     * Returns a valid token with id and the role of the authenticated user for authorizations
     * @param loginRequest
     * @return LoginResponseDTO
     */
    @PostMapping("/login")
    @Operation(description = "Returns a valid token with id and the role of the authenticated user for authorizations")
    ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody(description = "The user credentials") LoginRequestDTO loginRequest ){
        return  ResponseEntity.status(200).body(service.login(loginRequest));
    }
}