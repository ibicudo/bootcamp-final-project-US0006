package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.batch;


import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.ExpiringProductsDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Account;

import java.util.UUID;

public interface BatchService {
    ExpiringProductsDTO getBatchesWithExpiringProducts(Integer quantityDays, Account account);
    ExpiringProductsDTO getBatchesWithExpiringProductsWithFilters(Integer quantityDays, String category, String asc,  Account account);
}
