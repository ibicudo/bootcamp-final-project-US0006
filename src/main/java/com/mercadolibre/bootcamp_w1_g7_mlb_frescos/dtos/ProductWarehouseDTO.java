package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class ProductWarehouseDTO {

    @NotNull(message = "The productId is required")
    private String productId;
    @NotEmpty
    private List<WarehouseQuantityDTO> warehouses;
}
