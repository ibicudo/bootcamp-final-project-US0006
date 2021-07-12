package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batch_number_sequence")
    private Integer batchNumber;

    @Column
    private Double currentTemperature;

    @Column
    private Double minimumTemperature;

    @Column
    private Integer initialQuantity;

    @Column
    private Integer currentQuantity;

    @Column
    private LocalDate manufacturingDate;

    @Column
    private LocalDateTime manufacturingTime;

    @Column
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private InboundOrder inboundOrder;

    @ManyToOne()
    private Product product;

}
