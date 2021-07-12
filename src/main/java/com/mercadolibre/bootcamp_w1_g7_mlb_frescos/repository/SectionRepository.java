package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.repository;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

public interface SectionRepository  extends JpaRepository<Section, String> {

    List<Section> findAllByWarehouseCode(String warehouseCode);

    Optional<Section> findByWarehouseCodeAndCategory(String warehouse, String category);
}
