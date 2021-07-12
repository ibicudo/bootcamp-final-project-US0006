package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderWithoutOrderNumberDTO {

    @NotNull(message = "The orderDate is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    @Valid
    @NotNull(message = "The section is required")
    private SectionDTO section;

    @Valid
    @NotEmpty(message = "The batchStock list is required and cannot be empty")
    private List<BatchWithoutNumberDTO> batchStock;

}
