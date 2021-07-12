package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.integration;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.UpdateInboundOrderDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Account;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Role;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Section;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.repository.AccountRepository;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.repository.InboundOrderRepository;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.repository.SectionRepository;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.security.JWTUtil;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.util.TestUtilsGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InboundOrderServiceImplIntegrationTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InboundOrderRepository inboundOrderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SectionRepository sectionRepository;

    String token;
    
    @BeforeAll
    void setup() {
        token = TestUtilsGenerator.createToken();
    }


    @Test
    void createInboundOrderWithOneBatch() throws Exception {
        String request = TestUtilsGenerator.createRequestOneBatch();

        this.mockMvc.perform(
                post("/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(request))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.batchStock.[0].currentTemperature").value(10.0))
                .andExpect(jsonPath("$.batchStock.[0].minimumTemperature").value(5.0))
                .andExpect(jsonPath("$.batchStock.[0].initialQuantity").value(500));

    }

    @Test
    void createInboundOrderWithTwoBatch() throws Exception {
        String request = TestUtilsGenerator.createRequestTwoBatches();
        
        this.mockMvc.perform(
                post("/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(request))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.batchStock.[0].currentTemperature").value(10.0))
                .andExpect(jsonPath("$.batchStock.[0].minimumTemperature").value(5.0))
                .andExpect(jsonPath("$.batchStock.[1].currentTemperature").value(10.0))
                .andExpect(jsonPath("$.batchStock.[1].minimumTemperature").value(5.0));

    }

    @Test
    void updateInboundOrderWithOneBatch() throws Exception {
        inboundOrderRepository.save(TestUtilsGenerator.createOneBatchInboundOrderToPersist());
        UpdateInboundOrderDTO updateInboundOrderDTO = TestUtilsGenerator.createUpdateInboundOrderDTO();

        String request = TestUtilsGenerator.updateRequestOneBatch();
        this.mockMvc.perform(
                put("/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(request))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.batchStock.[0].currentTemperature").value(10.0))
                .andExpect(jsonPath("$.batchStock.[0].minimumTemperature").value(5.0));

    }

    @Test
    void updateInboundOrderWithTwoBatches() throws Exception {
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchInboundOrderToPersist());

        String request = TestUtilsGenerator.updateRequestTwoBatches();
        this.mockMvc.perform(
                put("/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(request))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.batchStock.[0].currentTemperature").value(10.0))
                .andExpect(jsonPath("$.batchStock.[0].minimumTemperature").value(5.0))
                .andExpect(jsonPath("$.batchStock.[1].minimumTemperature").value(5.0))
                .andExpect(jsonPath("$.batchStock.[1].minimumTemperature").value(5.0));

    }

    @Test
    void testListProductBatchStockWithCorrectDueDateFilter() throws Exception {
        inboundOrderRepository.save(TestUtilsGenerator.createTwoBatchInboundOrderToPersist());

        this.mockMvc.perform(
                get("/list?productId=51b3b287-0b78-484c-90c3-606c4bae9401")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.batchStock.length()").value(1));
    }

    @Test
    void testListProductBatchStockWithCorrectCurrentQuantitySorting() throws Exception {
        inboundOrderRepository.save(TestUtilsGenerator.createThreeBatchInboundOrderToPersist());

        this.mockMvc.perform(
                get("/list?productId=51b3b287-0b78-484c-90c3-606c4bae9401&sortParam=C")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.batchStock.length()").value(2))
                .andExpect(jsonPath("$.batchStock[0].currentQuantity").value(300))
                .andExpect(jsonPath("$.batchStock[1].currentQuantity").value(500));
    }

    @Test
    void testListProductBatchStockWithCorrectDueDateSorting() throws Exception {
        inboundOrderRepository.save(TestUtilsGenerator.createThreeBatchInboundOrderToPersist());

        this.mockMvc.perform(
                get("/list?productId=51b3b287-0b78-484c-90c3-606c4bae9401&sortParam=F")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.batchStock.length()").value(2))
                .andExpect(jsonPath("$.batchStock[0].dueDate").value(LocalDate.now().plusWeeks(4).toString()))
                .andExpect(jsonPath("$.batchStock[1].dueDate").value(LocalDate.now().plusWeeks(5).toString()));
    }

    @Test
    void testListProductBatchStockThrowExceptionWhenSortParamIsWrong() throws Exception {
        inboundOrderRepository.save(TestUtilsGenerator.createThreeBatchInboundOrderToPersist());

        this.mockMvc.perform(
                get("/list?productId=51b3b287-0b78-484c-90c3-606c4bae9401&sortParam=X")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Bad Request Exception. Order parameter should be F or C"));
    }

    @Test
    void testListProductBatchStockThrowExceptionWhenProductDoesNotExist() throws Exception {
        inboundOrderRepository.save(TestUtilsGenerator.createThreeBatchInboundOrderToPersist());

        this.mockMvc.perform(
                get("/list?productId=4a60193e-806e-4872-8b08-bda1dcfcb64e&sortParam=F")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not Found Exception. Product 4a60193e-806e-4872-8b08-bda1dcfcb64e not found"));
    }

    @Test
    void testListProductBatchStockThrowExceptionSupervisorDoesNotExist() throws Exception {
        inboundOrderRepository.save(TestUtilsGenerator.createThreeBatchInboundOrderToPersist());
        Role role = new Role();
        role.setId("ROLE_SUPERVISOR");
        Account account = new Account(UUID.fromString("4a60193e-806e-4872-8b08-bda1dcfcb64e"), "test", "1234456", role);
        accountRepository.save(account);

        String token = "Bearer " + JWTUtil.getJWT(account);

        this.mockMvc.perform(
                get("/list?productId=51b3b287-0b78-484c-90c3-606c4bae9401&sortParam=F")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not Found Exception. Supervisor not found"));
    }

    @Test
    void testListProductBatchStockThrowExceptionSectionDoesNotExist() throws Exception {
        inboundOrderRepository.save(TestUtilsGenerator.createThreeBatchInboundOrderToPersist());
        Section section = sectionRepository.findById("OSAF001").orElseThrow();
        section.setCategory("wrong-category");
        sectionRepository.save(section);

        this.mockMvc.perform(
                get("/list?productId=51b3b287-0b78-484c-90c3-606c4bae9401&sortParam=F")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not Found Exception. Section not found"));
    }

    @Test
    void testListProductBatchStockThrowExceptionWhenBatchListIsEmpty() throws Exception {
        inboundOrderRepository.save(TestUtilsGenerator.createOneBatchInboundOrderToPersist());

        this.mockMvc.perform(
                get("/list?productId=51b3b287-0b78-484c-90c3-606c4bae9401&sortParam=F")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not Found Exception. No batches contain this product"));
    }
}
