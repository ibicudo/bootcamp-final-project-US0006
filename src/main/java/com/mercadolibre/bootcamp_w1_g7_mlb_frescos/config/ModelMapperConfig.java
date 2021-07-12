package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.config;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.BatchDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.BatchWithoutNumberDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.SectionDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Batch;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Section;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
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


}
