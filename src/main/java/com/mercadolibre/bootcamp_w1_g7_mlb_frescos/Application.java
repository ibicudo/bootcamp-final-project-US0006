package com.mercadolibre.bootcamp_w1_g7_mlb_frescos;

import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.config.SpringConfig;
import com.mercadolibre.bootcamp_w1_g7_mlb_frescos.util.ScopeUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		ScopeUtils.calculateScopeSuffix();
		new SpringApplicationBuilder(SpringConfig.class).registerShutdownHook(true)
				.run(args);
	}
}
