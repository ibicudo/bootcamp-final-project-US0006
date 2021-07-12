package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.integration;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.InboundOrder;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Product;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.repository.InboundOrderRepository;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.util.TestUtilsGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WarehouseServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InboundOrderRepository inboundOrderRepository;

    private String token;
    private Product product;

    @BeforeAll
    void createToken() {
        token = TestUtilsGenerator.createToken();
        product = TestUtilsGenerator.createProductToPersist();
    }



    @Test
    void getRightProductInAllWarehouses() throws Exception {
       inboundOrderRepository.save(TestUtilsGenerator.createOneBatchInboundOrderToPersistByWarehouseCode("CAJF"));
       inboundOrderRepository.save(TestUtilsGenerator.createOneBatchInboundOrderToPersistByWarehouseCode("OSAF"));

        this.mockMvc.perform(
                get("/warehouse" )
                        .header("Authorization", token)
                        .queryParam("productId", product.getId().toString()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.warehouses.[0].warehouseCode").value("OSAF"))
                .andExpect(jsonPath("$.warehouses.[0].totalQuantity").value("500"))
                .andExpect(jsonPath("$.warehouses.[1].warehouseCode").value("CAJF"))
                .andExpect(jsonPath("$.warehouses.[1].totalQuantity").value("500"));
    }

    @Test
    void getProductsInAllWarehousesWhenProductDoesNotExist() throws Exception {

        this.mockMvc.perform(
                get("/warehouse" )
                        .header("Authorization", token)
                        .queryParam("productId", "2345eeba-7b7f-4a7d-8576-a78e5700abf6"))

                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    void getProductsInAllWarehousesWhenAllBatchesHave0Quantity() throws Exception {
        InboundOrder orderPersisted = inboundOrderRepository
                .save(TestUtilsGenerator.createOneBatchInboundOrderToPersistWith0QuantityBatch());
        String productId = orderPersisted.getBatchStock().iterator().next().getProduct().getId().toString();

        this.mockMvc.perform(
                get("/warehouse" )
                        .header("Authorization", token)
                        .queryParam("productId", productId))

                .andDo(print()).andExpect(status().isNotFound());

    }

}
