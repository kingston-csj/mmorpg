package org.forfun.mmorpg.game.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
public class SupportConfiguration {

	@Bean("gameConversion")
	public ConversionService createConversionService() {
		return new DefaultConversionService();
	}

}
