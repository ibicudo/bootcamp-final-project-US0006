package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.beans;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.SampleDTO;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomSampleBean {
	private Random random = new Random();

	public SampleDTO random() {
		return new SampleDTO(random.nextInt(Integer.MAX_VALUE));
	}
}

