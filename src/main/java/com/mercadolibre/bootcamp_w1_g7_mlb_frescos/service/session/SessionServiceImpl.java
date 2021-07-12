package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.session;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.LoginRequestDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.LoginResponseDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.exceptions.LoginFailedException;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.exceptions.NotFoundException;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Account;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.repository.AccountRepository;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws NotFoundException {
        Account account =  accountRepository.findAccount(loginRequestDTO.getUserName());

        if (account == null ||
                ! BCrypt.checkpw(loginRequestDTO.getPassword(), account.getPassword()))
        {
            throw new LoginFailedException("Account User or Password is wrong");
        }
        return new LoginResponseDTO(account.getId(), JWTUtil.getJWT(account));
    }
}