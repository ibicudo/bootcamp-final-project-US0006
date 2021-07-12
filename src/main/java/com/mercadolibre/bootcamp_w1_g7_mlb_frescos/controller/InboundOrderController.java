package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.controller;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.BatchStockDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.CreateInboundOrderDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.ProductBatchStockDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.UpdateInboundOrderDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Account;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.security.JWTUtil;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.account.AccountService;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.inboundorder.InboundOrderService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.UUID;

/**
 * Controller responsible for create, update and list batches in the warehouse of the logged supervisor
 */
@Validated
@RestController
@RequestMapping
public class InboundOrderController {

    private final InboundOrderService inboundOrderService;

    private final AccountService accountService;

    public InboundOrderController(InboundOrderService inboundOrderService, AccountService accountService) {
        this.inboundOrderService = inboundOrderService;
        this.accountService = accountService;
    }

    /**
     * Create an inbound order and return a list with the batches created.
     * @param token
     * @param createInboundOrderDTO
     * @return BatchStockDTO
     */
    @PostMapping("inboundorder")
    @Operation(description = "Create an inbound order and return a list with the batches created")
    public ResponseEntity<BatchStockDTO> createInboundOrder(
            @Parameter(description = "The token generated in the authentication") @RequestHeader("Authorization") String token,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The object with contains the new inbound order and their products batches")
            @Valid @RequestBody CreateInboundOrderDTO createInboundOrderDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inboundOrderService.createInboundOrder(createInboundOrderDTO, getAccount(token)));
    }

    /**
     * Update an existent inbound order and return a list with the batches updated.
     * @param token
     * @param updateInboundOrderDTO
     * @return BatchStockDTO
     */
    @PutMapping("inboundorder")
    @Operation(description = "Update an existent inbound order and return a list with the batches updated")
    public ResponseEntity<BatchStockDTO> updateInboundOrder(
            @Parameter(description = "The token generated in the authentication") @RequestHeader("Authorization") String token,
            @io.swagger.v3.oas.annotations.parameters.RequestBody @Valid @RequestBody UpdateInboundOrderDTO updateInboundOrderDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inboundOrderService.updateInboundOrder(updateInboundOrderDTO, getAccount(token)));
    }

    /**
     * Returns a location of a specified product in the logged supervisor warehouse.
     * @param token
     * @param productId
     * @param sortParam
     * @return
     */
    @GetMapping("list")
    @Operation(description = "Returns a location of a specified product in the logged supervisor warehouse")
    public ResponseEntity<ProductBatchStockDTO> listProductBatchStock(
            @Parameter(description = "The token generated in the authentication") @RequestHeader("Authorization") String token,
            @Parameter(description = "The product id to be listed") @RequestParam UUID productId,
            @Parameter(description = "The order to filter by. Can be 'F' for due date or 'C' for amount ") @RequestParam(required = false) @Nullable String sortParam) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(inboundOrderService.listProductBatchStock(productId, getAccount(token), sortParam));
    }

    private  Account getAccount(String token){
        Claims claim = JWTUtil.decodeJWT(token.split(" ")[1]);
        return accountService.getAccountByName(claim.getSubject());
    }
}
