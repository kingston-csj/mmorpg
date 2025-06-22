package org.forfun.mmorpg.game.base;

import jforgame.commons.eventbus.EventBus;
import jforgame.data.reader.DataReader;
import jforgame.data.reader.ExcelDataReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupportConfiguration {

	@Bean
	public DataReader createDataReader() {
		return new ExcelDataReader();
	}

	@Bean
	public EventBus createEventBus() {
		return new EventBus();
	}

}
