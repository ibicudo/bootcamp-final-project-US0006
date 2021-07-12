package com.mercadolibre.bootcamp_w1_g7_mlb_frescos.unit.beans;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.dtos.SampleDTO;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.beans.RandomSampleBean;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomSampleBeanTest {

	@Test
	public void randomPositiveTestOK() {
		RandomSampleBean randomSample = new RandomSampleBean();

		SampleDTO sample = randomSample.random();
		
		assertTrue(sample.getRandom() >= 0);
	}
}
