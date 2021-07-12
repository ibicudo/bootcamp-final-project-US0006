package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.InboundOrder;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionUpdateDTO {

    private String code;

    private String category;

    private int capacity;

    private String warehouseCode;

}
