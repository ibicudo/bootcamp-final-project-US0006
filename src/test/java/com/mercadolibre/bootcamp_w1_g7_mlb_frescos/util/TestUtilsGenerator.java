package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.util;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.*;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.*;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.security.JWTUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class TestUtilsGenerator {

    private static final UUID supervisorId = UUID.fromString("cdd7bfff-1eeb-4fe8-b3ed-7fb2c0304020");


    public static CreateInboundOrderDTO getInboundOrderDto (){
        CreateInboundOrderDTO createInboundOrderDTO = new CreateInboundOrderDTO();
        InboundOrderWithoutOrderNumberDTO inboundOrderWithoutOrderNumberDTO = new InboundOrderWithoutOrderNumberDTO();

        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setSectionCode("OSAF001");
        sectionDTO.setWarehouseCode("OSAF");

        Product product = new Product();
        product.setId(UUID.fromString("51b3b287-0b78-484c-90c3-606c4bae9401"));
        product.setName("Alface");
        product.setCategory("FS");

        List<BatchWithoutNumberDTO> batches = new ArrayList<>();
        BatchWithoutNumberDTO batchDTO1 = new BatchWithoutNumberDTO();
        batchDTO1.setProductId(product.getId());
        batchDTO1.setCurrentTemperature(10.0);
        batchDTO1.setMinimumTemperature(5.0);
        batchDTO1.setInitialQuantity(500);
        batchDTO1.setCurrentQuantity(500);
        batchDTO1.setManufacturingDate(LocalDate.of(2021, 06, 10));
        batchDTO1.setManufacturingTime(LocalDateTime.of(2021, 06, 03, 00, 00, 00));
        batchDTO1.setDueDate(LocalDate.of(2021, 8, 15));

        batches.add(batchDTO1);

        inboundOrderWithoutOrderNumberDTO.setOrderDate(LocalDate.of(2021, 07, 01));
        inboundOrderWithoutOrderNumberDTO.setSection(sectionDTO);
        inboundOrderWithoutOrderNumberDTO.setBatchStock(batches);
        createInboundOrderDTO.setInboundOrder(inboundOrderWithoutOrderNumberDTO);

        return createInboundOrderDTO;
    }

    public static InboundOrderDTO createInboundOrderDTO(){
        InboundOrderDTO inboundOrderDTO = new InboundOrderDTO();

        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setSectionCode("OSAF001");
        sectionDTO.setWarehouseCode("OSAF");

        Product product = new Product();
        product.setId(UUID.fromString("51b3b287-0b78-484c-90c3-606c4bae9401"));
        product.setName("Alface");
        product.setCategory("FS");

        List<BatchDTO> batches = new ArrayList<>();
        BatchDTO batchDTO1 = new BatchDTO();
        batchDTO1.setProductId(product.getId());
        batchDTO1.setCurrentTemperature(10.0);
        batchDTO1.setMinimumTemperature(5.0);
        batchDTO1.setInitialQuantity(500);
        batchDTO1.setCurrentQuantity(500);
        batchDTO1.setManufacturingDate(LocalDate.of(2021, 06, 10));
        batchDTO1.setManufacturingTime(LocalDateTime.of(2021, 06, 03, 00, 00, 00));
        batchDTO1.setDueDate(LocalDate.of(2021, 8, 15));

        batches.add(batchDTO1);

        inboundOrderDTO.setOrderNumber(1);
        inboundOrderDTO.setOrderDate(LocalDate.of(2021, 07, 01));
        inboundOrderDTO.setSection(sectionDTO);
        inboundOrderDTO.setBatchStock(batches);

        return inboundOrderDTO;
    }

    public static Section createSection (String code, String category, Integer capacity, Warehouse warehouse){
        Batch batch = new Batch();
        Set<Batch> batches = new HashSet<>();
        batches.add(batch);
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setBatchStock(batches);
        Set<InboundOrder> inboundOrderSet = new HashSet<>();
        inboundOrderSet.add(inboundOrder);

        Section section = new Section();
        section.setCode("OSAF001");
        section.setCategory("FS");
        section.setCapacity(500);
        section.setWarehouse(createWarehouse());
        section.setInboundOrder(inboundOrderSet);

        return section;
    }

    public static Warehouse createWarehouse (){
        Warehouse warehouse = new Warehouse();
        warehouse.setName("Fullfillment Osasco");
        warehouse.setCode("OSAF");

        return warehouse;
    }
    public static Product createProduct() {
        Product product = new Product();
        product.setId(UUID.fromString("51b3b287-0b78-484c-90c3-606c4bae9401"));
        product.setName("Alface");
        product.setCategory("FS");

        return product;
    }

    public static List<Product> createListProducts(){
        List<Product> products = new ArrayList<>();
        products.add(createProduct());

        return products;
    }

    public static Product createProductToPersist(){
        Product product = new Product();
        product.setId(UUID.fromString("51b3b287-0b78-484c-90c3-606c4bae9401"));
        product.setName("Alface");
        product.setCategory("FS");

        return product;
    }

    public static Account createAccountSupervisor() {
        Account account = new Account();
        Role role = new Role("ROLE_SUPERVISOR", List.of(account));
        account.setId(supervisorId);
        account.setUserName("Maria");
        account.setPassword("contra123");
        account.setRole(role);

        return account;
    }

    public static Account createPersistedAccountSupervisor() {
        Account account = new Account();
        Role role = new Role("ROLE_SUPERVISOR", List.of(account));
        account.setId(UUID.fromString("27a40a9e-3838-4717-935d-b9f6f4a4f623"));
        account.setRole(role);
        account.setUserName("monaliza");

        return account;
    }

    public static Supervisor createSupervisor(){
        Supervisor supervisor = new Supervisor();
        Warehouse warehouse = new Warehouse();
        warehouse.setCode("OSAF");
        warehouse.setName("Fullfillment Osasco");
        supervisor.setId(supervisorId);
        supervisor.setName("Maria");
        supervisor.setWarehouse(warehouse);

        return supervisor;
    }

    //TODO MUDAR SUPERVISOR ID
    public static Supervisor createOtherSupervisor(){
        Supervisor supervisor = new Supervisor();
        Warehouse warehouse = new Warehouse();
        warehouse.setCode("CAJF");
        warehouse.setName("Fullfillment Cajamar");
        supervisor.setId(UUID.fromString("b20c0c2d-f378-4d7c-b965-e8a6a128c948"));
        supervisor.setName("Barbara");
        supervisor.setWarehouse(warehouse);
        return supervisor;
    }

    public static InboundOrder createInboundOrder(){
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setOrderNumber(1);
        inboundOrder.setSection(createSection("OSAF001", "FS", 500, new Warehouse("OSAF", "Fullfillment Osasco")));
        inboundOrder.setSupervisor(createSupervisor());
        inboundOrder.setOrderDate(LocalDate.of(2021, 07, 05));
        Set<Batch> batchStock = createBatchStock();
        batchStock.forEach(batch -> batch.setInboundOrder(inboundOrder));
        inboundOrder.setBatchStock(batchStock);

        return inboundOrder;
    }

    public static InboundOrder createInboundOrderWithSetBatchDates() {
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setOrderNumber(1);
        inboundOrder.setSection(createSection("OSAF001", "FS", 500, new Warehouse("OSAF", "Fullfillment Osasco")));
        inboundOrder.setSupervisor(createSupervisor());
        inboundOrder.setOrderDate(LocalDate.of(2021, 07, 05));
        Set<Batch> batchStock = createBatchStockWithSetTime();
        batchStock.forEach(batch -> batch.setInboundOrder(inboundOrder));
        inboundOrder.setBatchStock(batchStock);

        return inboundOrder;
    }

    public static InboundOrder createInboundOrderWithTwoBatches(){
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setOrderNumber(1);
        inboundOrder.setSection(createSection("OSAF001", "FS", 500, new Warehouse("OSAF", "Fullfillment Osasco")));
        inboundOrder.setSupervisor(createSupervisor());
        inboundOrder.setOrderDate(LocalDate.of(2021, 07, 05));
        Set<Batch> batchStock = createBatchStockWithTwoProducts();
        batchStock.forEach(batch -> batch.setInboundOrder(inboundOrder));
        inboundOrder.setBatchStock(batchStock);

        return inboundOrder;
    }

    public static Set<Batch> createBatchStock(){
        Set<Batch> batches = new HashSet<>();
        Batch batch = new Batch();
        Product product = new Product();
        product.setId(UUID.fromString("51b3b287-0b78-484c-90c3-606c4bae9401"));
        batch.setProduct(product);
        batch.setCurrentTemperature(10.0);
        batch.setMinimumTemperature(5.0);
        batch.setInitialQuantity(500);
        batch.setCurrentQuantity(500);
        batch.setManufacturingDate(LocalDate.now());
        batch.setManufacturingTime(LocalDateTime.now());
        batch.setDueDate(LocalDate.now().plusWeeks(1));
        batch.setBatchNumber(1);

        batches.add(batch);

        return batches;
    }

    public static Set<Batch> createBatchStockWithSetTime(){
        Set<Batch> batches = new HashSet<>();
        Batch batch = new Batch();
        Product product = new Product();
        product.setId(UUID.fromString("51b3b287-0b78-484c-90c3-606c4bae9401"));
        batch.setProduct(product);
        batch.setCurrentTemperature(10.0);
        batch.setMinimumTemperature(5.0);
        batch.setInitialQuantity(500);
        batch.setCurrentQuantity(500);
        batch.setManufacturingDate(LocalDate.of(2021, 06, 10));
        batch.setManufacturingTime(LocalDateTime.of(2021, 06, 03, 00, 00, 00));
        batch.setDueDate(LocalDate.of(2021, 8, 15));
        batch.setBatchNumber(1);

        batches.add(batch);

        return batches;
    }

    public static Set<Batch> createBatchStockWithNoBatchNumber(){
        Set<Batch> batches = new HashSet<>();
        Batch batch = new Batch();
        Product product = new Product();
        product.setId(UUID.fromString("51b3b287-0b78-484c-90c3-606c4bae9401"));
        batch.setProduct(product);
        batch.setCurrentTemperature(10.0);
        batch.setMinimumTemperature(5.0);
        batch.setInitialQuantity(500);
        batch.setCurrentQuantity(500);
        batch.setManufacturingDate(LocalDate.of(2021, 06, 10));
        batch.setManufacturingTime(LocalDateTime.of(2021, 06, 03, 00, 00, 00));
        batch.setDueDate(LocalDate.of(2021, 8, 15));

        batches.add(batch);

        return batches;
    }

    public static Set<Batch> createBatchStockWithTwoBatches(){
        Set<Batch> batches = new HashSet<>();
        Batch batch1 = new Batch();
        Product product = new Product();
        product.setId(UUID.fromString("51b3b287-0b78-484c-90c3-606c4bae9401"));
        batch1.setProduct(product);
        batch1.setCurrentTemperature(10.0);
        batch1.setMinimumTemperature(5.0);
        batch1.setInitialQuantity(500);
        batch1.setCurrentQuantity(500);
        batch1.setManufacturingDate(LocalDate.now());
        batch1.setManufacturingTime(LocalDateTime.now());
        batch1.setDueDate(LocalDate.now().plusWeeks(1));

        Batch batch2 = new Batch();
        product.setId(UUID.fromString("51b3b287-0b78-484c-90c3-606c4bae9401"));
        batch2.setProduct(product);
        batch2.setCurrentTemperature(10.0);
        batch2.setMinimumTemperature(5.0);
        batch2.setInitialQuantity(500);
        batch2.setCurrentQuantity(500);
        batch2.setManufacturingDate(LocalDate.now());
        batch2.setManufacturingTime(LocalDateTime.now());
        batch2.setDueDate(LocalDate.now().plusWeeks(4));

        batches.add(batch1);
        batches.add(batch2);

        return batches;
    }

    public static Set<Batch> createBatchStockWithTwoBatchesWithProductOfRFCategory(){
        Set<Batch> batches = new HashSet<>();
        Batch batch1 = new Batch();
        Product product = new Product();
        product.setId(UUID.fromString("1b0d82fa-277f-4f13-a9b7-a6c4c4eec204"));
        batch1.setProduct(product);
        batch1.setCurrentTemperature(10.0);
        batch1.setMinimumTemperature(5.0);
        batch1.setInitialQuantity(500);
        batch1.setCurrentQuantity(500);
        batch1.setManufacturingDate(LocalDate.now());
        batch1.setManufacturingTime(LocalDateTime.now());
        batch1.setDueDate(LocalDate.now().plusWeeks(1));

        Batch batch2 = new Batch();
        batch2.setProduct(product);
        batch2.setCurrentTemperature(10.0);
        batch2.setMinimumTemperature(5.0);
        batch2.setInitialQuantity(500);
        batch2.setCurrentQuantity(500);
        batch2.setManufacturingDate(LocalDate.now());
        batch2.setManufacturingTime(LocalDateTime.now());
        batch2.setDueDate(LocalDate.now().plusWeeks(4));

        batches.add(batch1);
        batches.add(batch2);

        return batches;
    }

    public static Set<Batch> createBatchStockWithThreeBatches(){
        Set<Batch> batches = new HashSet<>();
        Batch batch1 = new Batch();
        Product product = new Product();
        product.setId(UUID.fromString("51b3b287-0b78-484c-90c3-606c4bae9401"));
        batch1.setProduct(product);
        batch1.setCurrentTemperature(10.0);
        batch1.setMinimumTemperature(5.0);
        batch1.setInitialQuantity(500);
        batch1.setCurrentQuantity(500);
        batch1.setManufacturingDate(LocalDate.now());
        batch1.setManufacturingTime(LocalDateTime.now());
        batch1.setDueDate(LocalDate.now().plusWeeks(1));

        Batch batch2 = new Batch();
        product.setId(UUID.fromString("51b3b287-0b78-484c-90c3-606c4bae9401"));
        batch2.setProduct(product);
        batch2.setCurrentTemperature(10.0);
        batch2.setMinimumTemperature(5.0);
        batch2.setInitialQuantity(500);
        batch2.setCurrentQuantity(500);
        batch2.setManufacturingDate(LocalDate.now());
        batch2.setManufacturingTime(LocalDateTime.now());
        batch2.setDueDate(LocalDate.now().plusWeeks(4));

        Batch batch3 = new Batch();
        product.setId(UUID.fromString("51b3b287-0b78-484c-90c3-606c4bae9401"));
        batch3.setProduct(product);
        batch3.setCurrentTemperature(10.0);
        batch3.setMinimumTemperature(5.0);
        batch3.setInitialQuantity(500);
        batch3.setCurrentQuantity(300);
        batch3.setManufacturingDate(LocalDate.now());
        batch3.setManufacturingTime(LocalDateTime.now());
        batch3.setDueDate(LocalDate.now().plusWeeks(5));

        batches.add(batch1);
        batches.add(batch2);
        batches.add(batch3);

        return batches;
    }

    public static Set<Batch> createBatchStockWithTwoProducts(){
        Set<Batch> batches = new HashSet<>();
        Batch batch = new Batch();
        Product product = new Product();
        product.setId(UUID.fromString("51b3b287-0b78-484c-90c3-606c4bae9401"));
        batch.setProduct(product);
        batch.setCurrentTemperature(10.0);
        batch.setMinimumTemperature(5.0);
        batch.setInitialQuantity(500);
        batch.setCurrentQuantity(500);
        batch.setManufacturingDate(LocalDate.of(2021, 06, 10));
        batch.setManufacturingTime(LocalDateTime.of(2021, 06, 03, 00, 00, 00));
        batch.setDueDate(LocalDate.of(2021, 8, 15));
        batch.setBatchNumber(1);

        Batch batch1 = new Batch();
        Product product1 = new Product();
        product1.setId(UUID.fromString("51b3b287-0b78-484c-90c3-606c4bae9401"));
        batch1.setProduct(product);
        batch1.setCurrentTemperature(10.0);
        batch1.setMinimumTemperature(5.0);
        batch1.setInitialQuantity(500);
        batch1.setCurrentQuantity(500);
        batch1.setManufacturingDate(LocalDate.of(2021, 06, 10));
        batch1.setManufacturingTime(LocalDateTime.of(2021, 06, 03, 00, 00, 00));
        batch1.setDueDate(LocalDate.of(2021, 8, 15));
        batch1.setBatchNumber(1);

        batches.add(batch);
        batches.add(batch1);


        return batches;
    }

    public static List<Batch> createBatchStockList(){
        List<Batch> batches = new ArrayList<>();
        Product product = new Product();
        product.setId(UUID.fromString("51b3b287-0b78-484c-90c3-606c4bae9401"));

        batches.add(createBatch(10.0, 5.0, 500, 500, LocalDate.of(2021, 06, 10),
                LocalDateTime.of(2021, 06, 03, 00, 00, 00), (LocalDate.of(2021, 8, 06)), 1));

        return batches;
    }

    public static Batch createBatch(Double currentTemperature, Double minimumTemperature, Integer initialQuantity, Integer currentQuantity, LocalDate manufacturingDate,
                                    LocalDateTime manufacturingTime, LocalDate dueDate, Integer batchNumber){
        Batch batch = new Batch();
        batch.setProduct(createProduct());
        batch.setCurrentTemperature(currentTemperature);
        batch.setMinimumTemperature(minimumTemperature);
        batch.setInitialQuantity(initialQuantity);
        batch.setCurrentQuantity(currentQuantity);
        batch.setManufacturingDate(manufacturingDate);
        batch.setManufacturingTime(manufacturingTime);
        batch.setDueDate(dueDate);
        batch.setBatchNumber(batchNumber);
        batch.setInboundOrder(createInboundOrder());

        return batch;
    }

    public static ModelMapper createModelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        modelMapper.getConfiguration().setSkipNullEnabled(true);

        modelMapper.addMappings(new PropertyMap<SectionDTO, Section>() {
            @Override
            protected void configure() {
                map().setCode(source.getSectionCode());
            }
        });

        modelMapper.addMappings(new PropertyMap<BatchWithoutNumberDTO, Batch>() {
            @Override
            protected void configure() {
                map().getInboundOrder().setOrderDate(null);
            }
        });

        modelMapper.addMappings(new PropertyMap<BatchDTO, Batch>() {
            @Override
            protected void configure() {
                map().getInboundOrder().setOrderDate(null);
            }
        });

        return modelMapper;
    }

    public static UpdateInboundOrderDTO createUpdateInboundOrderDTO(){
        UpdateInboundOrderDTO updateInboundOrderDTO = new UpdateInboundOrderDTO();
        InboundOrderDTO inboundOrderDTO = createInboundOrderDTO();
        inboundOrderDTO.getBatchStock().get(0).setBatchNumber(1);
        updateInboundOrderDTO.setInboundOrder(inboundOrderDTO);
        return updateInboundOrderDTO;
    }

    public static ProductWarehouseDTO createProductWarehouseDTO(){
        ProductWarehouseDTO productWarehouseDTO = new ProductWarehouseDTO();
        productWarehouseDTO.setProductId("51b3b287-0b78-484c-90c3-606c4bae9401");
        productWarehouseDTO.setWarehouses(new ArrayList<>());
        productWarehouseDTO.getWarehouses().add(new WarehouseQuantityDTO("OSAF", "1000"));
        productWarehouseDTO.getWarehouses().add(new WarehouseQuantityDTO("CAJF", "1000"));

        return productWarehouseDTO;
    }

    public static List<Warehouse> createWarehouses() {
        List<Warehouse> warehouses = new ArrayList<>();

        warehouses.add(new Warehouse("OSAF", "Fullfillment Osasco"));
        warehouses.add(new Warehouse("CAJF", "Fullfillment Cajamar"));

        return warehouses;
    }

    public static InboundOrder createOneBatchInboundOrderToPersist() {
        Supervisor supervisorPersisted = new Supervisor();
        supervisorPersisted.setId(UUID.fromString("27a40a9e-3838-4717-935d-b9f6f4a4f623"));
        Section sectionPersisted = new Section();
        sectionPersisted.setCode("OSAF001");
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setOrderNumber(1);
        inboundOrder.setSection(sectionPersisted);
        inboundOrder.setSupervisor(supervisorPersisted);
        inboundOrder.setOrderDate(LocalDate.now());
        Set<Batch> batchStock = createBatchStock();
        batchStock.forEach(batch -> {
            batch.setInboundOrder(inboundOrder);
            batch.setMinimumTemperature(20.0);
            batch.setCurrentTemperature(30.0);
        });
        inboundOrder.setBatchStock(batchStock);

        return inboundOrder;
    }

    public static InboundOrder createOneBatchInboundOrderToPersistWith0QuantityBatch() {
        Supervisor supervisorPersisted = new Supervisor();
        supervisorPersisted.setId(UUID.fromString("27a40a9e-3838-4717-935d-b9f6f4a4f623"));
        Section sectionPersisted = new Section();
        sectionPersisted.setCode("OSAF001");
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setOrderNumber(1);
        inboundOrder.setSection(sectionPersisted);
        inboundOrder.setSupervisor(supervisorPersisted);
        inboundOrder.setOrderDate(LocalDate.of(2021, 07, 05));
        Set<Batch> batchStock = createBatchStock();
        batchStock.forEach(batch -> {
            batch.setInboundOrder(inboundOrder);
            batch.setCurrentQuantity(0);
        });
        inboundOrder.setBatchStock(batchStock);

        return inboundOrder;
    }

    public static InboundOrder createOneBatchInboundOrderToPersistByWarehouseCode(String warehouseCode) {
        Supervisor supervisorPersisted = new Supervisor();
        supervisorPersisted.setId(UUID.fromString("27a40a9e-3838-4717-935d-b9f6f4a4f623"));
        Section sectionPersisted = new Section();
        sectionPersisted.setCode(warehouseCode + "001");
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setSection(sectionPersisted);
        inboundOrder.setSupervisor(supervisorPersisted);
        inboundOrder.setOrderDate(LocalDate.of(2021, 07, 05));
        Set<Batch> batchStock = createBatchStockWithNoBatchNumber();
        batchStock.forEach(batch -> {
            batch.setInboundOrder(inboundOrder);
            batch.setInitialQuantity(500);
            batch.setCurrentQuantity(500);
        });
        inboundOrder.setBatchStock(batchStock);

        return inboundOrder;
    }

    public static InboundOrder createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatch(String warehouseCode) {
        Supervisor supervisorPersisted = new Supervisor();
        supervisorPersisted.setId(UUID.fromString("27a40a9e-3838-4717-935d-b9f6f4a4f623"));
        Section sectionPersisted = new Section();
        sectionPersisted.setCode(warehouseCode + "001");
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setSection(sectionPersisted);
        inboundOrder.setSupervisor(supervisorPersisted);
        inboundOrder.setOrderDate(LocalDate.of(2021, 07, 05));
        Set<Batch> batchStock = createBatchStockWithTwoBatches();
        Iterator<Batch> iterator = batchStock.iterator();
        iterator.next().setDueDate(LocalDate.now());
        iterator.next().setDueDate(LocalDate.now().plusYears(1));
        batchStock.stream().forEach(batch -> {
            batch.setInboundOrder(inboundOrder);
        });
        inboundOrder.setBatchStock(batchStock);

        return inboundOrder;
    }

    public static InboundOrder createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatchWithRFProduct(String warehouseCode) {
        Supervisor supervisorPersisted = new Supervisor();
        supervisorPersisted.setId(UUID.fromString("27a40a9e-3838-4717-935d-b9f6f4a4f623"));
        Section sectionPersisted = new Section();
        sectionPersisted.setCode(warehouseCode + "001");
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setSection(sectionPersisted);
        inboundOrder.setSupervisor(supervisorPersisted);
        inboundOrder.setOrderDate(LocalDate.of(2021, 07, 05));
        Set<Batch> batchStock = createBatchStockWithTwoBatchesWithProductOfRFCategory();
        Iterator<Batch> iterator = batchStock.iterator();
        iterator.next().setDueDate(LocalDate.now());
        iterator.next().setDueDate(LocalDate.now().plusYears(1));
        batchStock.stream().forEach(batch -> {
            batch.setInboundOrder(inboundOrder);
        });
        inboundOrder.setBatchStock(batchStock);

        return inboundOrder;
    }

    public static InboundOrder createTwoBatchesInboundOrderToPersistByWarehouseCodeWithTwoExpiringBatchWithRFProduct(String warehouseCode) {
        Supervisor supervisorPersisted = new Supervisor();
        supervisorPersisted.setId(UUID.fromString("27a40a9e-3838-4717-935d-b9f6f4a4f623"));
        Section sectionPersisted = new Section();
        sectionPersisted.setCode(warehouseCode + "001");
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setSection(sectionPersisted);
        inboundOrder.setSupervisor(supervisorPersisted);
        inboundOrder.setOrderDate(LocalDate.of(2021, 07, 05));
        Set<Batch> batchStock = createBatchStockWithTwoBatchesWithProductOfRFCategory();
        Iterator<Batch> iterator = batchStock.iterator();
        iterator.next().setDueDate(LocalDate.now().plusWeeks(1));
        iterator.next().setDueDate(LocalDate.now());
        batchStock.stream().forEach(batch -> {
            batch.setInboundOrder(inboundOrder);
        });
        inboundOrder.setBatchStock(batchStock);

        return inboundOrder;
    }

    public static InboundOrder createTwoBatchInboundOrderToPersist() {
        Supervisor supervisorPersisted = new Supervisor();
        supervisorPersisted.setId(UUID.fromString("27a40a9e-3838-4717-935d-b9f6f4a4f623"));
        Section sectionPersisted = new Section();
        sectionPersisted.setCode("OSAF001");
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setOrderNumber(1);
        inboundOrder.setSection(sectionPersisted);
        inboundOrder.setSupervisor(supervisorPersisted);
        inboundOrder.setOrderDate(LocalDate.now());
        Set<Batch> batchStock = createBatchStockWithTwoBatches();
        batchStock.forEach(batch -> {
            batch.setInboundOrder(inboundOrder);
            batch.setMinimumTemperature(20.0);
            batch.setCurrentTemperature(30.0);
        });
        inboundOrder.setBatchStock(batchStock);

        return inboundOrder;
    }

    public static InboundOrder createThreeBatchInboundOrderToPersist() {
        Supervisor supervisorPersisted = new Supervisor();
        supervisorPersisted.setId(UUID.fromString("27a40a9e-3838-4717-935d-b9f6f4a4f623"));
        Section sectionPersisted = new Section();
        sectionPersisted.setCode("OSAF001");
        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setOrderNumber(1);
        inboundOrder.setSection(sectionPersisted);
        inboundOrder.setSupervisor(supervisorPersisted);
        inboundOrder.setOrderDate(LocalDate.now());
        Set<Batch> batchStock = createBatchStockWithThreeBatches();
        batchStock.forEach(batch -> {
            batch.setInboundOrder(inboundOrder);
            batch.setMinimumTemperature(20.0);
            batch.setCurrentTemperature(30.0);
        });
        inboundOrder.setBatchStock(batchStock);

        return inboundOrder;
    }

    public static String createRequestOneBatch(){
        String request = "{\"inboundOrder\": {\"orderDate\": \"2021-07-01\", \"section\": {\"sectionCode\": \"OSAF001\" , \"warehouseCode\": \"OSAF\"}, \"batchStock\": [" +
                getBatchStock("51b3b287-0b78-484c-90c3-606c4bae9401", 10.0, 5.0,
                        500, 500, "2021-06-10", "2021-06-10 20:00:00", "2021-08-15") + "]}}";

        return request;
    }

    public static BatchStockWithDueDateDTO createBatchStockWithDueDate (LocalDate dueDate, Integer batchNumber, String producId, Integer quantity, String category){
        BatchStockWithDueDateDTO batchStockWithDueDateDTO = new BatchStockWithDueDateDTO();
        batchStockWithDueDateDTO.setDueDate(dueDate);
        batchStockWithDueDateDTO.setBatchNumber(batchNumber);
        batchStockWithDueDateDTO.setProductId(producId);
        batchStockWithDueDateDTO.setQuantity(quantity);
        batchStockWithDueDateDTO.setProductTypeId(category);

        return batchStockWithDueDateDTO;
    }


    public static String createRequestTwoBatches(){
        String request = "{\"inboundOrder\": {\"orderNumber\": 1,\"orderDate\": \"2021-07-01\", \"section\": {\"sectionCode\": \"OSAF001\" , \"warehouseCode\": \"OSAF\"}, \"batchStock\": [" +
                getBatchStock("51b3b287-0b78-484c-90c3-606c4bae9401", 10.0, 5.0,
                        500, 500, "2021-06-10", "2021-06-03 00:00:00", "2021-08-15") + "," +
                getBatchStock("51b3b287-0b78-484c-90c3-606c4bae9401", 10.0, 5.0,
                        500, 500, "2021-06-10", "2021-06-03 00:00:00", "2021-08-15")  + "]}}";
        return request;
    }

    private static String getBatchStock(String productId, Double currentTemperature, Double minimumTemperature, Integer initialQuantity, Integer currentQuantity, String manufacturingDate, String manufacturingTime, String dueDate) {
        return "{\"productId\": \""+productId+"\", \"currentTemperature\": "+currentTemperature+", " +
                "\"minimumTemperature\": "+minimumTemperature+", \"initialQuantity\":"+initialQuantity+", \"currentQuantity\": "+currentQuantity+", " +
                "\"manufacturingDate\": \""+manufacturingDate+"\", \"manufacturingTime\": \""+manufacturingTime+"\", \"dueDate\": \""+dueDate+"\"}";
    }

    private static String getUpdateBatchStock(String productId, Double currentTemperature, Double minimumTemperature, Integer initialQuantity, Integer currentQuantity, String manufacturingDate, String manufacturingTime, String dueDate, Integer batchNumber) {
        return "{\"productId\": \""+productId+"\", \"currentTemperature\": "+currentTemperature+", " +
                "\"minimumTemperature\": "+minimumTemperature+", \"initialQuantity\":"+initialQuantity+", \"currentQuantity\": "+currentQuantity+", " +
                "\"manufacturingDate\": \""+manufacturingDate+"\", \"manufacturingTime\": \""+manufacturingTime+"\", \"dueDate\": \""+dueDate+"\", \"batchNumber\": \""+batchNumber+"\"}";
    }

    public static String updateRequestOneBatch(){
        String request = "{\"inboundOrder\": {\"orderNumber\": 1, \"orderDate\": \"2021-07-01\", \"section\": {\"sectionCode\": \"OSAF001\" , \"warehouseCode\": \"OSAF\"}, \"batchStock\": [" +
                getUpdateBatchStock("51b3b287-0b78-484c-90c3-606c4bae9401", 10.0, 5.0,
                        500, 500, "2021-06-10", "2021-06-10 20:00:00", "2021-08-15", 1) + "]}}";

        return request;
    }

    public static String updateRequestTwoBatches(){
        String request = "{\"inboundOrder\": {\"orderNumber\": 1, \"orderDate\": \"2021-07-01\", \"section\": {\"sectionCode\": \"OSAF001\" , \"warehouseCode\": \"OSAF\"}, \"batchStock\": [" +
                getUpdateBatchStock("51b3b287-0b78-484c-90c3-606c4bae9401", 10.0, 5.0,
                        500, 500, "2021-06-10", "2021-06-03 00:00:00", "2021-08-15", 1) + "," +
                getUpdateBatchStock("51b3b287-0b78-484c-90c3-606c4bae9401", 10.0, 5.0,
                        500, 500, "2021-06-10", "2021-06-03 00:00:00", "2021-08-15", 2)  + "]}}";
        return request;
    }

    public static String createToken() {
        return "Bearer " + JWTUtil.getJWT(createPersistedAccountSupervisor());
    }

    public static List<BatchInfoDTO> createBatchInfoList() {
        return List.of(
                new BatchInfoDTO(1, 500, LocalDate.of(2021, 8, 15))
        );
    }
}
