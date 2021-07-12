package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.inboundorder;

import com.google.common.collect.Sets;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.*;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.exceptions.BadRequestException;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.exceptions.NotFoundException;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.*;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InboundOrderServiceImpl implements InboundOrderService {

    private final ProductRepository productRepository;

    private final SupervisorRepository supervisorRepository;

    private final SectionRepository sectionRepository;

    private final InboundOrderRepository inboundOrderRepository;

    private final BatchRepository batchRepository;

    private final ModelMapper modelMapper;

    private final Map<String, String> order = Map.of("C", "currentQuantity",
            "F", "dueDate");


    public InboundOrderServiceImpl(ProductRepository productRepository, SupervisorRepository supervisorRepository,
                                   SectionRepository sectionRepository, InboundOrderRepository inboundOrderRepository,
                                   BatchRepository batchRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.supervisorRepository = supervisorRepository;
        this.sectionRepository = sectionRepository;
        this.inboundOrderRepository = inboundOrderRepository;
        this.batchRepository = batchRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BatchStockDTO createInboundOrder(CreateInboundOrderDTO createInboundOrderDTO, Account account) {
        InboundOrderWithoutOrderNumberDTO inboundOrderDTO = createInboundOrderDTO.getInboundOrder();

        Supervisor supervisor = this.supervisorRepository.findById(account.getId())
                .orElseThrow(() -> new NotFoundException("Supervisor not found"));

        SectionDTO sectionDTO = inboundOrderDTO.getSection();
        Set<UUID> productIdsInBatch = inboundOrderDTO
                .getBatchStock()
                .stream()
                .map(BatchWithoutNumberDTO::getProductId)
                .collect(Collectors.toSet());

        validateBaseConstraints(sectionDTO.getSectionCode(), sectionDTO.getWarehouseCode(), supervisor, productIdsInBatch, inboundOrderDTO.getBatchStock().size());

        InboundOrderDTO myInboundOrderDTO = new InboundOrderDTO();
        myInboundOrderDTO.setOrderDate(inboundOrderDTO.getOrderDate());
        List<BatchDTO> batches = inboundOrderDTO.getBatchStock().stream().map(batch -> {
            BatchDTO batchDTO = new BatchDTO();
            batchDTO.setBatchNumber(null);
            batchDTO.setProductId(batch.getProductId());
            batchDTO.setCurrentTemperature(batch.getCurrentTemperature());
            batchDTO.setMinimumTemperature(batch.getMinimumTemperature());
            batchDTO.setInitialQuantity(batch.getInitialQuantity());
            batchDTO.setCurrentQuantity(batch.getCurrentQuantity());
            batchDTO.setManufacturingDate(batch.getManufacturingDate());
            batchDTO.setManufacturingTime(batch.getManufacturingTime());
            batchDTO.setDueDate(batch.getDueDate());

            return batchDTO;
        }).collect(Collectors.toList());
        myInboundOrderDTO.setBatchStock(batches);
        myInboundOrderDTO.setSection(inboundOrderDTO.getSection());
        myInboundOrderDTO.setOrderNumber(null);

        InboundOrder inboundOrder = modelMapper.map(myInboundOrderDTO, InboundOrder.class);
        inboundOrder.getBatchStock().stream().forEach(batch -> batch.setInboundOrder(inboundOrder));
        inboundOrder.setSupervisor(supervisor);

        InboundOrder savedInboundOrder = inboundOrderRepository.save(inboundOrder);

        Set<BatchDTO> batchDTO = savedInboundOrder
                .getBatchStock()
                .stream()
                .map(batch -> this.modelMapper.map(batch, BatchDTO.class))
                .collect(Collectors.toSet());

        return new BatchStockDTO(batchDTO);
    }

    @Override
    public BatchStockDTO updateInboundOrder(UpdateInboundOrderDTO updateInboundOrderDTO, Account account) {
        InboundOrderDTO inboundOrderDTO = updateInboundOrderDTO.getInboundOrder();

        InboundOrder inboundOrder = this.inboundOrderRepository.findById(inboundOrderDTO.getOrderNumber())
                .orElseThrow(() -> new NotFoundException("Inbound order does not exist"));


        Supervisor supervisor = this.supervisorRepository.findById(account.getId())
                .orElseThrow(() -> new NotFoundException("Supervisor not found"));

        SectionDTO sectionDTO = inboundOrderDTO.getSection();
        Set<UUID> productIdsInBatch = inboundOrderDTO
                .getBatchStock()
                .stream()
                .map(BatchDTO::getProductId)
                .collect(Collectors.toSet());

        validateBaseConstraints(sectionDTO.getSectionCode(), sectionDTO.getWarehouseCode(), supervisor, productIdsInBatch, inboundOrderDTO.getBatchStock().size());
        validateUpdateConstraints(inboundOrder, inboundOrderDTO.getBatchStock(), supervisor.getId());

        InboundOrder newInboundOrder = modelMapper.map(inboundOrderDTO, InboundOrder.class);
        newInboundOrder.getBatchStock().stream().forEach(batch -> batch.setInboundOrder(newInboundOrder));
        newInboundOrder.setSupervisor(supervisor);

        InboundOrder savedInboundOrder = inboundOrderRepository.save(newInboundOrder);

        Set<BatchDTO> batchDTO = savedInboundOrder
                .getBatchStock()
                .stream()
                .map(batch -> this.modelMapper.map(batch, BatchDTO.class))
                .collect(Collectors.toSet());

        return new BatchStockDTO(batchDTO);
    }

    @Override
    public ProductBatchStockDTO listProductBatchStock(UUID productId, Account account, String sortParam) {

        if (sortParam == null) {
            sortParam = "C";
        }

        if (order.get(sortParam) == null) {
            throw new BadRequestException("Order parameter should be F or C");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product " + productId + " not found"));
        Supervisor supervisor = supervisorRepository.findById(account.getId())
                .orElseThrow(() -> new NotFoundException("Supervisor not found"));

        String warehouseCode = supervisor.getWarehouse().getCode();

        Section section = sectionRepository
                .findByWarehouseCodeAndCategory(warehouseCode,product.getCategory())
                .orElseThrow(() -> new NotFoundException("Section not found"));


        List<Batch> batches = batchRepository.findBatchesByProductAndWarehouse(productId, warehouseCode, Sort.by(order.get(sortParam)));

        LocalDate minDueDate = LocalDate.now().plusWeeks(3);

        List<BatchInfoDTO> batchStock = batches.stream().filter(batch -> minDueDate.isBefore(batch.getDueDate()) )
                .map(batch -> new BatchInfoDTO(batch.getBatchNumber(), batch.getCurrentQuantity(), batch.getDueDate()))
                .collect(Collectors.toList());

        if(batchStock.isEmpty()) {
            throw new NotFoundException("No batches contain this product");
        }

        SectionDTO sectionDTO = new SectionDTO(section.getCode(), warehouseCode);

        return new ProductBatchStockDTO(sectionDTO, productId, batchStock);
    }



    private void validateBaseConstraints(String sectionCode, String warehouseCode, Supervisor supervisor, Set<UUID> productIdsInBatch, Integer batchStockSize) {
        Section section = sectionRepository.findById(sectionCode)
                .orElseThrow(() -> new NotFoundException("Section not found"));
        List<Product> products = this.getExistingProducts(productIdsInBatch);

        this.checkWarehouse(section.getWarehouse().getCode(), warehouseCode);
        this.checkSupervisorBelongsToWarehouse(supervisor.getWarehouse().getCode(), warehouseCode);
        this.checkProducts(productIdsInBatch, products);
        this.checkProductsCategory(products, section.getCategory());
        checkSectionCapacity(section, batchStockSize);
    }

    private void checkSupervisorBelongsToWarehouse(String expected, String received) {
        if (!expected.equals(received)) {
            throw new BadRequestException("Supervisor does not belong to this warehouse");
        }
    }

    private void checkWarehouse(String expected, String received) {
        if (!expected.equals(received)) {
            throw new BadRequestException("Warehouse and Section do not match");
        }
    }

    private List<Product> getExistingProducts(Set<UUID> productsIds) {
        return productRepository.findAllById(productsIds);
    }

    private void checkProducts(Set<UUID> productIdsInBatch, List<Product> products) {

        Set<UUID> retrievedProductIds = products.stream()
                .map(Product::getId).collect(Collectors.toSet());

        Set<UUID> missingProducts = Sets.difference(productIdsInBatch, retrievedProductIds);

        if (!missingProducts.isEmpty()) {
            throw new NotFoundException("Products with ids " + missingProducts + " are missing in the database");
        }
    }

    private void checkProductsCategory(List<Product> products, String category) {
        List<UUID> productsWithWrongCategory = products.stream()
                .filter(product -> (!product.getCategory().equals(category)))
                .map(Product::getId)
                .collect(Collectors.toList());

        if (!productsWithWrongCategory.isEmpty()) {
            throw new BadRequestException("Products with ids " + productsWithWrongCategory.toString() + " can not be stored in this section");
        }
    }

    private void checkSectionCapacity(Section section, int neededSpace) {
        Integer usedSpace = section.getInboundOrder().stream()
                .map(InboundOrder::getBatchStock).map(Set::size).reduce(0, Integer::sum);

        if (usedSpace + neededSpace > section.getCapacity()) {
            throw new BadRequestException("The section can not contain this inboundOrder");
        }
    }

    private void validateUpdateConstraints(InboundOrder inboundOrder, List<BatchDTO> batchStock, UUID supervisorId) {
        checkSupervisorOwnsInboundOrder(inboundOrder, supervisorId);
        checkBatchBelongsToCorrectInboundOrder(inboundOrder.getOrderNumber(), batchStock);
    }

    private void checkSupervisorOwnsInboundOrder(InboundOrder inboundOrder, UUID supervisorId) {
        Supervisor supervisor = inboundOrder.getSupervisor();
        if (!supervisor.getId().equals(supervisorId)) {
            throw new BadRequestException("Supervisor does not own this inboundOrder");
        }
    }

    private void checkBatchBelongsToCorrectInboundOrder(Integer orderNumber, List<BatchDTO> batchStock) {
        Set<Integer> batchNumbers = batchStock.stream().map(BatchDTO::getBatchNumber).collect(Collectors.toSet());
        Set<Batch> batches = Set.copyOf(this.batchRepository.findAllById(batchNumbers));

        Set<Integer> batchesFromInboundOrder = batches
                .stream()
                .filter(batch -> batch.getInboundOrder().getOrderNumber().equals(orderNumber))
                .map(Batch::getBatchNumber)
                .collect(Collectors.toSet());

        Set<Integer> extraBatches = Sets.difference(batchNumbers, batchesFromInboundOrder);

        if (!extraBatches.isEmpty()) {
            throw new BadRequestException("The batches with ids " + extraBatches + " do not belong to this inboundOrder");
        }
    }

}