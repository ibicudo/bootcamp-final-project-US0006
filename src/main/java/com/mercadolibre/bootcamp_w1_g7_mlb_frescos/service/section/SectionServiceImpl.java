package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.section;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.SectionUpdateDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.exceptions.NotFoundException;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Account;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.InboundOrder;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Section;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Supervisor;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SectionServiceImpl implements SectionService{

    private final ProductRepository productRepository;

    private final SupervisorRepository supervisorRepository;

    private final SectionRepository sectionRepository;

    private final InboundOrderRepository inboundOrderRepository;

    private final BatchRepository batchRepository;

    private final ModelMapper modelMapper;

    public SectionServiceImpl(ProductRepository productRepository, SupervisorRepository supervisorRepository, SectionRepository sectionRepository, InboundOrderRepository inboundOrderRepository, BatchRepository batchRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.supervisorRepository = supervisorRepository;
        this.sectionRepository = sectionRepository;
        this.inboundOrderRepository = inboundOrderRepository;
        this.batchRepository = batchRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Section updateCapacitySection(SectionUpdateDTO sectionUpdateDTO, Account account) {

        Section section = this.sectionRepository.findById(sectionUpdateDTO.getCode())
                .orElseThrow(() -> new NotFoundException("Section does not exist"));

        Supervisor supervisor = this.supervisorRepository.findById(account.getId())
                .orElseThrow(() -> new NotFoundException("Supervisor not found"));

        Section newInboundOrder = modelMapper.map(sectionUpdateDTO, Section.class);
        Section sectionSaved = sectionRepository.save(newInboundOrder);

        return sectionSaved;
    }


}
