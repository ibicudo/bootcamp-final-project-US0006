package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.unit;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.LoginRequestDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.exceptions.LoginFailedException;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Account;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.repository.AccountRepository;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.session.SessionService;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.session.SessionServiceImpl;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.unit.factory.LoginRequestDTOFactory;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.util.MockitoExtension;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {
    @Mock
    private  AccountRepository accountRepository;

    @InjectMocks
    private SessionServiceImpl service;


    @Test
    public void loginUserNotExistsTest(){
        LoginRequestDTO loginRequest = LoginRequestDTOFactory.getLoginMock();
        when(accountRepository.findAccount("123445")).thenReturn(null);
        assertThrows(LoginFailedException.class, () -> service.login(loginRequest));
    }
}
