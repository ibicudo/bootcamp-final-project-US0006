package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Validated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchStockWithDueDateDTO {

    @NotNull(message = "The batchNumber is required")
    private Integer batchNumber;
    @NotNull(message = "The productId is required")
    private String productId;
    @NotNull(message = "The productTypeId is required")
    private String productTypeId;
    @NotNull(message = "The dueDate is required")
    private LocalDate dueDate;
    @NotNull(message = "The quantity is required")
    private Integer quantity;

}
