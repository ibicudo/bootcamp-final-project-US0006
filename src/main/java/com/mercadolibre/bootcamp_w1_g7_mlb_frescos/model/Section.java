package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Section {

    @Id
    private String code;

    private String category;

    private int capacity;

    @ManyToOne()
    private Warehouse warehouse;

    @OneToMany(mappedBy = "section")
    private Set<InboundOrder> inboundOrder;
}
