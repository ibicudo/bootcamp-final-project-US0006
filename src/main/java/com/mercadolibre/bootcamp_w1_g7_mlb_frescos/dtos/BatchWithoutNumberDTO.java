package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchWithoutNumberDTO {

    @NotNull(message = "The productId is required")
    private UUID productId;

    @NotNull(message = "The currentTemperature is required")
    @Digits(integer = 2, fraction = 1, message = "The currentTemperature must have at maximum 2 integers and 1 decimal number")
    private Double currentTemperature;

    @NotNull(message = "The minimumTemperature is required")
    @Digits(integer = 2, fraction = 1, message = "The minimumTemperature must have at maximum 2 integers and 1 decimal number")
    private Double minimumTemperature;

    @NotNull(message = "The initialQuantity is required")
    @Positive(message = "The initialQuantity must be greater than zero")
    private Integer initialQuantity;

    @NotNull(message = "The currentQuantity is required")
    @PositiveOrZero(message = "The currentQuantity must be equal or greater than zero")
    private Integer currentQuantity;

    @NotNull(message = "The manufacturingDate is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate manufacturingDate;

    @NotNull(message = "The manufacturingTime is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime manufacturingTime;

    @NotNull(message = "The dueDate is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

}
