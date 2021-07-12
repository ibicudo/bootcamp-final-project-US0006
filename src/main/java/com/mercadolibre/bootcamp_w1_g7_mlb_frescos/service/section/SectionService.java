package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.service.section;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.SectionUpdateDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Account;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.model.Section;

public interface SectionService {
    Section updateCapacitySection(SectionUpdateDTO sectionUpdateDTO, Account account);
}
