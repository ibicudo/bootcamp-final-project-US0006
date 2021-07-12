package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionDTO{

    @NotNull(message = "The sectionCode is required")
    private String sectionCode;

    @NotNull(message = "The warehouseCode is required")
    private String warehouseCode;

}
