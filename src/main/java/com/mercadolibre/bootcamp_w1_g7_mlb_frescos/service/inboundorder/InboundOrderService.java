
package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.inboundorder;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.BatchStockDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.CreateInboundOrderDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.ProductBatchStockDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.UpdateInboundOrderDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Account;

import java.util.UUID;

public interface InboundOrderService {

    BatchStockDTO createInboundOrder(CreateInboundOrderDTO createInboundOrderDTO, Account account);
    BatchStockDTO updateInboundOrder(UpdateInboundOrderDTO inboundOrderDTO, Account account);
    ProductBatchStockDTO listProductBatchStock(UUID productId, Account account, String sortParam);
}
