package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.integration;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.repository.AccountRepository;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BatchServiceImplIntegrationTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InboundOrderRepository inboundOrderRepository;

    private String token;

    @BeforeAll
    void setup() {
        token = TestUtilsGenerator.createToken();
    }

    @Test
    void testGetBatchesByDueDate() throws Exception {
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatch("CAJF"));
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatch("OSAF"));

        this.mockMvc.perform(
                get("/due-date")
                        .header("Authorization", token)
                        .queryParam("quantityDays", "30"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.batchStock.length()").value(1))
                .andExpect(jsonPath("$.batchStock.[0].dueDate").value(LocalDate.now().toString()));
    }

    @Test
    void testGetBatchesByDueDateFilterByCategory() throws Exception {
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatchWithRFProduct("CAJF"));
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatchWithRFProduct("OSAF"));
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatch("CAJF"));
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatch("OSAF"));


        this.mockMvc.perform(
                get("/due-date/list")
                        .header("Authorization", token)
                        .queryParam("quantityDays", "30")
                        .queryParam("category", "RF"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.batchStock.length()").value(1))
                .andExpect(jsonPath("$.batchStock.[0].dueDate").value(LocalDate.now().toString()));
    }

    @Test
    void testGetBatchesByDueDateFilterByCategoryAndOrderByDescDueDate() throws Exception {
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatchWithRFProduct("CAJF"));
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatchWithRFProduct("OSAF"));
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithTwoExpiringBatchWithRFProduct("OSAF"));
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatch("CAJF"));
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatch("OSAF"));


        this.mockMvc.perform(
                get("/due-date/list")
                        .header("Authorization", token)
                        .queryParam("quantityDays", "30")
                        .queryParam("category", "RF")
                        .queryParam("typeOrder", "desc"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.batchStock.length()").value(3))
                .andExpect(jsonPath("$.batchStock.[0].dueDate").value(LocalDate.now().plusWeeks(1).toString()));
    }

    @Test
    void testGetBatchesByDueDateFilterByCategoryAndOrderByAscDueDate() throws Exception {
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatchWithRFProduct("CAJF"));
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatchWithRFProduct("OSAF"));
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithTwoExpiringBatchWithRFProduct("OSAF"));
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatch("CAJF"));
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchesInboundOrderToPersistByWarehouseCodeWithOneExpiringBatch("OSAF"));


        this.mockMvc.perform(
                get("/due-date/list")
                        .header("Authorization", token)
                        .queryParam("quantityDays", "30")
                        .queryParam("category", "RF")
                        .queryParam("typeOrder", "asc"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.batchStock.length()").value(3))
                .andExpect(jsonPath("$.batchStock.[2].dueDate").value(LocalDate.now().plusWeeks(1).toString()));
    }
}


