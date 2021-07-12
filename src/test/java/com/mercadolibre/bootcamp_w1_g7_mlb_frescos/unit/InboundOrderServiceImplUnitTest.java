package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.unit;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.*;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.exceptions.BadRequestException;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.exceptions.NotFoundException;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.*;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.repository.*;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.inboundorder.InboundOrderServiceImpl;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.util.MockitoExtension;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.util.TestUtilsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InboundOrderServiceImplUnitTest {

    @Mock
    private InboundOrderRepository inboundOrderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private SupervisorRepository supervisorRepository;

    @Mock
    private BatchRepository batchRepository;

    @InjectMocks
    private InboundOrderServiceImpl inboundOrderServiceImpl;

    private ModelMapper modelMapper;

    Account accountSupervisor;
    InboundOrder inboundOrder;
    Section section;
    List<Product> products;
    Supervisor supervisor;
    InboundOrderDTO inboundOrderDTO;

    @BeforeEach
    public void setUp (){
        accountSupervisor = TestUtilsGenerator.createAccountSupervisor();
        section = TestUtilsGenerator.createSection("OSAF001", "FS", 500, new Warehouse("OSAF", "Fullfillment Osasco"));
        products = TestUtilsGenerator.createListProducts();
        supervisor = TestUtilsGenerator.createSupervisor();
        inboundOrder = TestUtilsGenerator.createInboundOrder();
        inboundOrderDTO = TestUtilsGenerator.createInboundOrderDTO();
        modelMapper = TestUtilsGenerator.createModelMapper();
        inboundOrderServiceImpl = new InboundOrderServiceImpl(productRepository, supervisorRepository,
                 sectionRepository,  inboundOrderRepository, batchRepository, modelMapper);

    }

    @Test
    void testCreateRightWithRightSection(){
        //arrange
        CreateInboundOrderDTO createInboundOrderDTO = TestUtilsGenerator.getInboundOrderDto();
        Optional<Section> sectionOptional = Optional.of(section);
        Optional<Supervisor> supervisorOptional = Optional.of(supervisor);
        Set<UUID> listProducts = new HashSet<>();
        listProducts.add(createInboundOrderDTO.getInboundOrder().getBatchStock().get(0).getProductId());
        when(sectionRepository.findById(createInboundOrderDTO.getInboundOrder().getSection().getSectionCode())).thenReturn(sectionOptional);
        when(productRepository.findAllById(listProducts)).thenReturn(products);
        when(supervisorRepository.findById(accountSupervisor.getId())).thenReturn(supervisorOptional);
        when(inboundOrderRepository.save(any(InboundOrder.class))).thenReturn(TestUtilsGenerator.createInboundOrderWithSetBatchDates());
        inboundOrderDTO.getBatchStock().get(0).setBatchNumber(1);

        //act
        BatchStockDTO response = inboundOrderServiceImpl.createInboundOrder(createInboundOrderDTO, accountSupervisor);

        //assert
        assertThat(inboundOrderDTO.getBatchStock()).usingRecursiveComparison().isEqualTo(response.getBatchStock());
    }

    @Test
    void testCreateWrongIfDoesNotExistSection(){
        //arrange
        CreateInboundOrderDTO createInboundOrderDTO = TestUtilsGenerator.getInboundOrderDto();
        Optional<Section> sectionOptional = Optional.empty();
        Optional<Supervisor> supervisorOptional = Optional.of(supervisor);
        Set<UUID> listProducts = new HashSet<>();
        listProducts.add(createInboundOrderDTO.getInboundOrder().getBatchStock().get(0).getProductId());
        when(sectionRepository.findById(createInboundOrderDTO.getInboundOrder().getSection().getSectionCode())).thenReturn(sectionOptional);
        when(productRepository.findAllById(listProducts)).thenReturn(products);
        when(supervisorRepository.findById(accountSupervisor.getId())).thenReturn(supervisorOptional);

        //assert
        assertThrows(NotFoundException.class, () -> {
            BatchStockDTO response = inboundOrderServiceImpl.createInboundOrder(createInboundOrderDTO, accountSupervisor);
        });
    }

    @Test
    void testCreateRightWithRightWarehouse(){
        //arrange
        CreateInboundOrderDTO createInboundOrderDTO = TestUtilsGenerator.getInboundOrderDto();
        Optional<Section> sectionOptional = Optional.of(section);
        Optional<Supervisor> supervisorOptional = Optional.of(supervisor);
        Set<UUID> listProducts = new HashSet<>();
        listProducts.add(createInboundOrderDTO.getInboundOrder().getBatchStock().get(0).getProductId());
        when(sectionRepository.findById(createInboundOrderDTO.getInboundOrder().getSection().getSectionCode())).thenReturn(sectionOptional);
        when(productRepository.findAllById(listProducts)).thenReturn(products);
        when(supervisorRepository.findById(accountSupervisor.getId())).thenReturn(supervisorOptional);
        when(inboundOrderRepository.save(any(InboundOrder.class))).thenReturn(inboundOrder);
        inboundOrderDTO.getBatchStock().get(0).setBatchNumber(1);

        //act
        BatchStockDTO response = inboundOrderServiceImpl.createInboundOrder(createInboundOrderDTO, accountSupervisor);

        //assert
        assertEquals("OSAF" , createInboundOrderDTO.getInboundOrder().getSection().getWarehouseCode());
    }

    @Test
    void testCreateWrongWithWrongWarehouse(){
        //arrange
        CreateInboundOrderDTO createInboundOrderDTO = TestUtilsGenerator.getInboundOrderDto();
        Optional<Section> sectionOptional = Optional.of(section);
        Optional<Supervisor> supervisorOptional = Optional.of(supervisor);
        Set<UUID> listProducts = new HashSet<>();
        listProducts.add(createInboundOrderDTO.getInboundOrder().getBatchStock().get(0).getProductId());
        when(sectionRepository.findById(createInboundOrderDTO.getInboundOrder().getSection().getSectionCode())).thenReturn(sectionOptional);
        when(productRepository.findAllById(listProducts)).thenReturn(products);
        when(supervisorRepository.findById(accountSupervisor.getId())).thenReturn(supervisorOptional);
        when(inboundOrderRepository.save(any(InboundOrder.class))).thenReturn(inboundOrder);
        inboundOrderDTO.getBatchStock().get(0).setBatchNumber(1);

        //act
        createInboundOrderDTO.getInboundOrder().getSection().setWarehouseCode("DAHJ");

        //assert
        assertThrows(BadRequestException.class, () -> {
            BatchStockDTO response = inboundOrderServiceImpl.createInboundOrder(createInboundOrderDTO, accountSupervisor);
        });

    }

    @Test
    void testCreateWrongBatchWithProduct (){
        //arrange
        CreateInboundOrderDTO createInboundOrderDTO = TestUtilsGenerator.getInboundOrderDto();
        section.setCategory("RF");
        Optional<Section> sectionOptional = Optional.of(section);
        List<UUID> listProducts = new ArrayList<>();
        listProducts.add(createInboundOrderDTO.getInboundOrder().getBatchStock().get(0).getProductId());
        when(sectionRepository.findById(createInboundOrderDTO.getInboundOrder().getSection().getSectionCode())).thenReturn(sectionOptional);
        when(productRepository.findAllById(listProducts)).thenReturn(products);

        //assert
        assertThrows(NotFoundException.class, () -> {
            BatchStockDTO response = inboundOrderServiceImpl.createInboundOrder(createInboundOrderDTO, accountSupervisor);
        });
    }

    @Test
    void testCreateWrongBatchWithWrongCapacity (){
        //arrange
        CreateInboundOrderDTO createInboundOrderDTO = TestUtilsGenerator.getInboundOrderDto();
        section.setCapacity(1);
        Optional<Section> sectionOptional = Optional.of(section);
        List<UUID> listProducts = new ArrayList<>();
        listProducts.add(createInboundOrderDTO.getInboundOrder().getBatchStock().get(0).getProductId());
        when(sectionRepository.findById(createInboundOrderDTO.getInboundOrder().getSection().getSectionCode())).thenReturn(sectionOptional);
        when(productRepository.findAllById(listProducts)).thenReturn(products);

        //assert
        assertThrows(NotFoundException.class, () -> {
            BatchStockDTO response = inboundOrderServiceImpl.createInboundOrder(createInboundOrderDTO, accountSupervisor);
        });
    }

    @Test
    void testRightUpdateInboundOrder() {
        //arrange
        UpdateInboundOrderDTO updateInboundOrderDTO = TestUtilsGenerator.createUpdateInboundOrderDTO();
        Optional<InboundOrder> inboundOrderOptional = Optional.of(inboundOrder);
        Optional<Supervisor> supervisorOptional = Optional.of(supervisor);
        Optional<Section> sectionOptional = Optional.of(section);
        List<Batch> batches = TestUtilsGenerator.createBatchStockList();
        Set<Integer> batchNumbers = updateInboundOrderDTO.getInboundOrder().getBatchStock().stream().map(BatchDTO::getBatchNumber).collect(Collectors.toSet());
        Set<UUID> listProducts = new HashSet<>();
        listProducts.add(updateInboundOrderDTO.getInboundOrder().getBatchStock().get(0).getProductId());

        when(inboundOrderRepository.findById(updateInboundOrderDTO.getInboundOrder().getOrderNumber())).thenReturn(inboundOrderOptional);
        when(supervisorRepository.findById(accountSupervisor.getId())).thenReturn(supervisorOptional);
        when(sectionRepository.findById(updateInboundOrderDTO.getInboundOrder().getSection().getSectionCode())).thenReturn(sectionOptional);
        when(batchRepository.findAllById(batchNumbers)).thenReturn(batches);
        when(productRepository.findAllById(listProducts)).thenReturn(products);
        inboundOrderDTO.getBatchStock().get(0).setBatchNumber(1);
        when(inboundOrderRepository.save(any(InboundOrder.class))).thenReturn(TestUtilsGenerator.createInboundOrderWithSetBatchDates());

        //act
        BatchStockDTO response = inboundOrderServiceImpl.updateInboundOrder(updateInboundOrderDTO, accountSupervisor);

        //assert
        verify(inboundOrderRepository, Mockito.times(1)).save(any(InboundOrder.class));
        assertThat(inboundOrderDTO.getBatchStock()).usingRecursiveComparison().isEqualTo(response.getBatchStock());
    }

    @Test
    void testWrongUpdateInboundOrderWhenInboundOrderDoesNotExists() {
        //arrange
        UpdateInboundOrderDTO updateInboundOrderDTO = TestUtilsGenerator.createUpdateInboundOrderDTO();
        Optional<InboundOrder> inboundOrderOptional = Optional.empty();

        when(inboundOrderRepository.findById(updateInboundOrderDTO.getInboundOrder().getOrderNumber())).thenReturn(inboundOrderOptional);

        //assert
        assertThrows(NotFoundException.class, () -> {
            inboundOrderServiceImpl.updateInboundOrder(updateInboundOrderDTO, accountSupervisor);
        });
    }

    @Test
    void testWrongUpdateInboundOrderWhenSupervisorDoesNotExists() {
        //arrange
        UpdateInboundOrderDTO updateInboundOrderDTO = TestUtilsGenerator.createUpdateInboundOrderDTO();
        Optional<InboundOrder> inboundOrderOptional = Optional.of(inboundOrder);
        Optional<Supervisor> supervisorOptional = Optional.empty();

        when(inboundOrderRepository.findById(updateInboundOrderDTO.getInboundOrder().getOrderNumber())).thenReturn(inboundOrderOptional);
        when(supervisorRepository.findById(accountSupervisor.getId())).thenReturn(supervisorOptional);

        //assert
        assertThrows(NotFoundException.class, () -> {
            inboundOrderServiceImpl.updateInboundOrder(updateInboundOrderDTO, accountSupervisor);
        });
    }

    @Test
    void testWrongUpdateInboundOrderWhenSupervisorDoesNotMatchWarehouse() {
        //arrange
        UpdateInboundOrderDTO updateInboundOrderDTO = TestUtilsGenerator.createUpdateInboundOrderDTO();
        inboundOrder.setSupervisor(TestUtilsGenerator.createOtherSupervisor());
        Optional<InboundOrder> inboundOrderOptional = Optional.of(inboundOrder);
        Optional<Supervisor> supervisorOptional = Optional.of(supervisor);
        Optional<Section> sectionOptional = Optional.of(section);
        Set<UUID> listProducts = new HashSet<>();
        listProducts.add(updateInboundOrderDTO.getInboundOrder().getBatchStock().get(0).getProductId());

        when(inboundOrderRepository.findById(updateInboundOrderDTO.getInboundOrder().getOrderNumber())).thenReturn(inboundOrderOptional);

        when(supervisorRepository.findById(accountSupervisor.getId())).thenReturn(supervisorOptional);
        when(sectionRepository.findById(updateInboundOrderDTO.getInboundOrder().getSection().getSectionCode())).thenReturn(sectionOptional);
        when(productRepository.findAllById(listProducts)).thenReturn(products);

        //assert
        assertThrows(BadRequestException.class, () -> {
            inboundOrderServiceImpl.updateInboundOrder(updateInboundOrderDTO, accountSupervisor);
        });
    }

    @Test
    void testWrongUpdateInboundOrderWhenBatchDoesNotBelongToInboundOrder() {
        //arrange
        UpdateInboundOrderDTO updateInboundOrderDTO = TestUtilsGenerator.createUpdateInboundOrderDTO();
        updateInboundOrderDTO.getInboundOrder().setOrderNumber(2);
        Optional<InboundOrder> inboundOrderOptional = Optional.of(inboundOrder);
        Optional<Supervisor> supervisorOptional = Optional.of(supervisor);
        Optional<Section> sectionOptional = Optional.of(section);
        Set<UUID> listProducts = new HashSet<>();
        listProducts.add(updateInboundOrderDTO.getInboundOrder().getBatchStock().get(0).getProductId());

        when(inboundOrderRepository.findById(updateInboundOrderDTO.getInboundOrder().getOrderNumber())).thenReturn(inboundOrderOptional);
        when(supervisorRepository.findById(accountSupervisor.getId())).thenReturn(supervisorOptional);
        when(sectionRepository.findById(updateInboundOrderDTO.getInboundOrder().getSection().getSectionCode())).thenReturn(sectionOptional);
        when(productRepository.findAllById(listProducts)).thenReturn(products);

        //assert
        assertThrows(BadRequestException.class, () -> {
            inboundOrderServiceImpl.updateInboundOrder(updateInboundOrderDTO, accountSupervisor);
        });
    }

    @Test
    void testListProductBatchStockWithCorrectDueDateFilter() {
        // arrange
        Product product = TestUtilsGenerator.createProduct();
        UUID productId = product.getId();
        UUID supervisorId = supervisor.getId();
        String warehouseCode = supervisor.getWarehouse().getCode();
        List<Batch> batches = new ArrayList<>(TestUtilsGenerator.createBatchStockWithTwoProducts());
        batches.get(0).setDueDate(LocalDate.now().plusWeeks(2));
        List<BatchInfoDTO> expectedBatches = TestUtilsGenerator.createBatchInfoList();

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(supervisorRepository.findById(supervisorId)).thenReturn(Optional.of(supervisor));
        when(sectionRepository.findByWarehouseCodeAndCategory(warehouseCode, product.getCategory())).thenReturn(Optional.of(section));
        when(batchRepository.findBatchesByProductAndWarehouse(productId, warehouseCode, Sort.by("currentQuantity"))).thenReturn(batches);

        // act
        ProductBatchStockDTO productBatchStockDTO = inboundOrderServiceImpl.listProductBatchStock(productId, accountSupervisor, "C");

        // assert
        assertThat(productBatchStockDTO.getBatchStock()).usingRecursiveComparison().isEqualTo(expectedBatches);
    }

    @Test
    void testListProductBatchStockCallsRepositoryWithCorrectParameter() {
        // arrange
        Product product = TestUtilsGenerator.createProduct();
        UUID productId = product.getId();
        UUID supervisorId = supervisor.getId();
        String warehouseCode = supervisor.getWarehouse().getCode();
        List<Batch> batches = new ArrayList<>(TestUtilsGenerator.createBatchStockWithTwoProducts());
        batches.get(0).setDueDate(LocalDate.now().plusWeeks(2));
        List<BatchInfoDTO> expectedBatches = TestUtilsGenerator.createBatchInfoList();

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(supervisorRepository.findById(supervisorId)).thenReturn(Optional.of(supervisor));
        when(sectionRepository.findByWarehouseCodeAndCategory(warehouseCode, product.getCategory())).thenReturn(Optional.of(section));
        when(batchRepository.findBatchesByProductAndWarehouse(productId, warehouseCode, Sort.by("dueDate"))).thenReturn(batches);

        // act
        ProductBatchStockDTO productBatchStockDTO = inboundOrderServiceImpl.listProductBatchStock(productId, accountSupervisor, "F");

        // assert
        verify(batchRepository, Mockito.times(1)).findBatchesByProductAndWarehouse(productId, warehouseCode, Sort.by("dueDate"));
    }

    @Test
    void testListProductBatchStockCallsRepositoryWithCorrectDefaultParameter() {
        // arrange
        Product product = TestUtilsGenerator.createProduct();
        UUID productId = product.getId();
        UUID supervisorId = supervisor.getId();
        String warehouseCode = supervisor.getWarehouse().getCode();
        List<Batch> batches = new ArrayList<>(TestUtilsGenerator.createBatchStockWithTwoProducts());
        batches.get(0).setDueDate(LocalDate.now().plusWeeks(2));
        List<BatchInfoDTO> expectedBatches = TestUtilsGenerator.createBatchInfoList();

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(supervisorRepository.findById(supervisorId)).thenReturn(Optional.of(supervisor));
        when(sectionRepository.findByWarehouseCodeAndCategory(warehouseCode, product.getCategory())).thenReturn(Optional.of(section));
        when(batchRepository.findBatchesByProductAndWarehouse(productId, warehouseCode, Sort.by("currentQuantity"))).thenReturn(batches);

        // act
        ProductBatchStockDTO productBatchStockDTO = inboundOrderServiceImpl.listProductBatchStock(productId, accountSupervisor, null);

        // assert
        verify(batchRepository, Mockito.times(1)).findBatchesByProductAndWarehouse(productId, warehouseCode, Sort.by("currentQuantity"));
    }

    @Test
    void testThrowExceptionWhenSortParamIsWrong() {
        // arrange
        Product product = TestUtilsGenerator.createProduct();
        UUID productId = product.getId();
        UUID supervisorId = supervisor.getId();
        String warehouseCode = supervisor.getWarehouse().getCode();
        List<Batch> batches = new ArrayList<>(TestUtilsGenerator.createBatchStockWithTwoProducts());
        batches.get(0).setDueDate(LocalDate.now().plusWeeks(2));

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(supervisorRepository.findById(supervisorId)).thenReturn(Optional.of(supervisor));
        when(sectionRepository.findByWarehouseCodeAndCategory(warehouseCode, product.getCategory())).thenReturn(Optional.of(section));
        when(batchRepository.findBatchesByProductAndWarehouse(productId, warehouseCode, Sort.by("currentQuantity"))).thenReturn(batches);

        // assert
        assertThrows(BadRequestException.class, () -> {
            inboundOrderServiceImpl.listProductBatchStock(productId, accountSupervisor, "X");
        });
    }

    @Test
    void testThrowExceptionWhenProductDoesNotExist() {
        // arrange
        Product product = TestUtilsGenerator.createProduct();
        UUID productId = product.getId();
        UUID supervisorId = supervisor.getId();
        String warehouseCode = supervisor.getWarehouse().getCode();
        List<Batch> batches = new ArrayList<>(TestUtilsGenerator.createBatchStockWithTwoProducts());
        batches.get(0).setDueDate(LocalDate.now().plusWeeks(2));

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(supervisorRepository.findById(supervisorId)).thenReturn(Optional.of(supervisor));
        when(sectionRepository.findByWarehouseCodeAndCategory(warehouseCode, product.getCategory())).thenReturn(Optional.of(section));
        when(batchRepository.findBatchesByProductAndWarehouse(productId, warehouseCode, Sort.by("currentQuantity"))).thenReturn(batches);

        // assert
        assertThrows(NotFoundException.class, () -> {
            inboundOrderServiceImpl.listProductBatchStock(UUID.fromString("da372202-a300-4996-aee2-8c5cb52a2e59"), accountSupervisor, "C");
        });
    }

    @Test
    void testThrowExceptionWhenSupervisorDoesNotExist() {
        // arrange
        Product product = TestUtilsGenerator.createProduct();
        UUID productId = product.getId();
        UUID supervisorId = supervisor.getId();
        String warehouseCode = supervisor.getWarehouse().getCode();
        List<Batch> batches = new ArrayList<>(TestUtilsGenerator.createBatchStockWithTwoProducts());
        batches.get(0).setDueDate(LocalDate.now().plusWeeks(2));

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(supervisorRepository.findById(supervisorId)).thenReturn(Optional.empty());
        when(sectionRepository.findByWarehouseCodeAndCategory(warehouseCode, product.getCategory())).thenReturn(Optional.of(section));
        when(batchRepository.findBatchesByProductAndWarehouse(productId, warehouseCode, Sort.by("currentQuantity"))).thenReturn(batches);

        // assert
        assertThrows(NotFoundException.class, () -> {
            inboundOrderServiceImpl.listProductBatchStock(productId, accountSupervisor, "C");
        });
    }

    @Test
    void testThrowExceptionWhenSectionDoesNotExist() {
        // arrange
        Product product = TestUtilsGenerator.createProduct();
        UUID productId = product.getId();
        UUID supervisorId = supervisor.getId();
        String warehouseCode = supervisor.getWarehouse().getCode();
        List<Batch> batches = new ArrayList<>(TestUtilsGenerator.createBatchStockWithTwoProducts());
        batches.get(0).setDueDate(LocalDate.now().plusWeeks(2));

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(supervisorRepository.findById(supervisorId)).thenReturn(Optional.of(supervisor));
        when(sectionRepository.findByWarehouseCodeAndCategory(warehouseCode, product.getCategory())).thenReturn(Optional.empty());
        when(batchRepository.findBatchesByProductAndWarehouse(productId, warehouseCode, Sort.by("currentQuantity"))).thenReturn(batches);

        // assert
        assertThrows(NotFoundException.class, () -> {
            inboundOrderServiceImpl.listProductBatchStock(productId, accountSupervisor, "C");
        });
    }

    @Test
    void testThrowExceptionWhenBatchListIsEmpty() {
        // arrange
        Product product = TestUtilsGenerator.createProduct();
        UUID productId = product.getId();
        UUID supervisorId = supervisor.getId();
        String warehouseCode = supervisor.getWarehouse().getCode();
        List<Batch> batches = new ArrayList<>(TestUtilsGenerator.createBatchStockWithTwoProducts());
        batches.get(0).setDueDate(LocalDate.now().plusWeeks(2));
        batches.get(1).setDueDate(LocalDate.now().plusWeeks(2));

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(supervisorRepository.findById(supervisorId)).thenReturn(Optional.of(supervisor));
        when(sectionRepository.findByWarehouseCodeAndCategory(warehouseCode, product.getCategory())).thenReturn(Optional.of(section));
        when(batchRepository.findBatchesByProductAndWarehouse(productId, warehouseCode, Sort.by("currentQuantity"))).thenReturn(batches);

        // assert
        assertThrows(NotFoundException.class, () -> {
            inboundOrderServiceImpl.listProductBatchStock(productId, accountSupervisor, "C");
        });
    }

}
