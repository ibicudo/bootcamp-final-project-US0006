package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.unit;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.ProductWarehouseDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.exceptions.BadRequestException;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.exceptions.NotFoundException;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.*;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.repository.*;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.warehouse.WarehouseServiceImpl;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.util.MockitoExtension;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.util.TestUtilsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceImplUnitTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BatchRepository batchRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    private List<Section> sections = new ArrayList<>();
    private InboundOrder inboundOrder;
    private Product product;
    private List<Batch> batches;
    private ProductWarehouseDTO productWarehouseDTO;
    private List<Warehouse> warehouses;


    @BeforeEach
    void setUp() {
        sections.add(TestUtilsGenerator.createSection("OSAF001", "FS", 500, new Warehouse("OSAF", "Fullfillment Osasco")));
        sections.add(TestUtilsGenerator.createSection("CAJF001", "FS", 100, new Warehouse("CAJF", "Fullfillment Cajamar")));
        inboundOrder = TestUtilsGenerator.createInboundOrder();
        product = TestUtilsGenerator.createProduct();
        batches = TestUtilsGenerator.createBatchStockList();
        productWarehouseDTO = TestUtilsGenerator.createProductWarehouseDTO();
        warehouses = TestUtilsGenerator.createWarehouses();

    }

    @Test
    void testGetRightProductInAllWarehouses() {
        //arrange
        batches.add(TestUtilsGenerator.createBatch(10.0, 5.0, 500, 500, LocalDate.of(2021, 06, 10),
                LocalDateTime.of(2021, 06, 03, 00, 00, 00), (LocalDate.of(2021, 8, 15)), 2));

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(warehouseRepository.findAll()).thenReturn(warehouses);
        when(batchRepository.findBatchesByProductAndWarehouse(any(UUID.class), anyString())).thenReturn(batches);

        //act
        ProductWarehouseDTO response = warehouseService.getProductsInAllWarehouses(product.getId());

        //assert
        assertThat(productWarehouseDTO).usingRecursiveComparison().isEqualTo(response);
    }

    @Test
    void testGetProductsInAllWarehousesWhenProductDoesNotExist() {
        //arrange
         when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        //assert
        assertThrows(BadRequestException.class, () -> {
            warehouseService.getProductsInAllWarehouses(product.getId());
        });
    }


    @Test
    void testGetProductsInAllWarehousesWhenBatchesDoesNotContainsAnyProduct() {
        batches.get(0).setCurrentQuantity(0);
        batches.add(TestUtilsGenerator.createBatch(10.0, 5.0, 500, 0, LocalDate.of(2021, 06, 10),
                LocalDateTime.of(2021, 06, 03, 00, 00, 00), (LocalDate.of(2021, 8, 15)), 2));

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(warehouseRepository.findAll()).thenReturn(warehouses);
        when(batchRepository.findBatchesByProductAndWarehouse(any(UUID.class), any())).thenReturn(batches);

        //assert
        assertThrows(NotFoundException.class, () -> {
            warehouseService.getProductsInAllWarehouses(product.getId());
        });
    }
}
