package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.warehouse;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.ProductWarehouseDTO;

import java.util.UUID;

public interface WarehouseService {

    ProductWarehouseDTO getProductsInAllWarehouses(UUID productId);
}
