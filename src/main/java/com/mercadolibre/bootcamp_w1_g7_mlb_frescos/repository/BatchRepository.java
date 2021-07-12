package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.repository;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Batch;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BatchRepository extends JpaRepository<Batch, Integer> {

    @Query(value = "SELECT b FROM Batch b JOIN b.inboundOrder as io JOIN io.section as ios JOIN ios.warehouse WHERE ios.warehouse.code = :warehouse AND b.dueDate <= :date")
    List<Batch> findAllByOrderNumberFilter(String warehouse, LocalDate date);
    
    @Query(value = "SELECT b FROM Batch b JOIN b.inboundOrder as io JOIN io.section as s WHERE b.product.id = :productId and s.warehouse.code = :warehouseCode")
    List<Batch> findBatchesByProductAndWarehouse(UUID productId, String warehouseCode);

    @Query(value = "SELECT b FROM Batch b JOIN b.inboundOrder as io JOIN io.section as s WHERE b.product.id = :productId and s.warehouse.code = :warehouseCode")
    List<Batch> findBatchesByProductAndWarehouse(UUID productId, String warehouseCode, Sort sort);

}
