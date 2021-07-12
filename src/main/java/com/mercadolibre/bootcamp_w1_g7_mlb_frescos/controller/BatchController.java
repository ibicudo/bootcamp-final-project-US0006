package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.controller;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.ExpiringProductsDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Account;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.security.JWTUtil;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.account.AccountService;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.batch.BatchService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for find products with due date less than the specified date in the the logged supervisor warehouse.
 */
@RestController
@RequestMapping("due-date")
public class BatchController {

    private final BatchService batchService;
    private final AccountService accountService;

    public BatchController(BatchService batchService, AccountService accountService) {
        this.batchService = batchService;
        this.accountService = accountService;
    }

    /**
     * Returns a list with the batches, located in the supervisor's warehouse, that have products with due date less than the actual date + the number os days
     * specified in the quantityDays param.
     * @param token
     * @param quantityDays -
     * @return ExpiringProductsDTO
     */
    @GetMapping()
    @Operation(description = "Returns a list with the batches, located in the supervisor's warehouse, that have products with due date less than the actual date + the number os days specified in the quantityDays param.")
    public ResponseEntity<ExpiringProductsDTO> getExpiringProductsDTO(
            @Parameter(description = "The token generated in the authentication") @RequestHeader("Authorization") String token,
            @Parameter(description = "The day amount to plus the actual date for filter the products by their due dates")@RequestParam Integer quantityDays) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(batchService.getBatchesWithExpiringProducts(quantityDays, getAccount(token)));
    }

    /**
     * A list filtered by product category with the batches, located in the supervisor's warehouse, that have products with due date less than the actual date + the number os days
     * specified in the quantityDays param. The list can be sorted by due date in ascending or descending order passed by the typeOrder parameter.
     * @param token
     * @param quantityDays
     * @param category
     * @param typeOrder
     * @return ExpiringProductsDTO
     */
    @GetMapping("/list")
    @Operation(description = "A list filtered by product category with the batches, located in the supervisor's warehouse, that have products with due date less than the actual date + the number os days specified in the quantityDays param. The list can be sorted by due date in ascending or descending order passed by the typeOrder parameter.")
    public ResponseEntity<ExpiringProductsDTO> getExpiringProductsDTO(
            @Parameter(description = "The token generated in the authentication") @RequestHeader("Authorization") String token,
            @Parameter(description = "The day amount to plus the actual date for filter the products by their due dates")@RequestParam Integer quantityDays,
            @Parameter(description = "The category to filter by") @RequestParam String category,
            @Parameter(description = "The order to sort by. Can be 'asc' or 'desc'") @RequestParam (required = false) String typeOrder) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(batchService.getBatchesWithExpiringProductsWithFilters(quantityDays, category, typeOrder, getAccount(token)));
    }

    private  Account getAccount(String token){
        Claims claim = JWTUtil.decodeJWT(token.split(" ")[1]);
        return accountService.getAccountByName(claim.getSubject());
    }
}
