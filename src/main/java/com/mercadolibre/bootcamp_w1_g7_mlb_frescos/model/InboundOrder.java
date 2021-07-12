package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InboundOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_number_sequence")
    private Integer orderNumber;

    @Column
    private LocalDate orderDate;

    @ManyToOne()
    @JoinColumn(name = "section_code", nullable = false)
    private Section section;

    @OneToMany(mappedBy = "inboundOrder", cascade = { CascadeType.ALL })
    private Set<Batch> batchStock;

    @ManyToOne()
    private Supervisor supervisor;

}
