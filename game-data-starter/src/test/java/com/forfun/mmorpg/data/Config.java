package com.forfun.mmorpg.data;

import org.forfun.mmorpg.data.DataManager;
import org.forfun.mmorpg.data.ResourceProperties;
import org.forfun.mmorpg.data.convertor.JsonToArrayConvertor;
import org.forfun.mmorpg.data.convertor.JsonToListConvertor;
import org.forfun.mmorpg.data.convertor.JsonToMapConvertor;
import org.forfun.mmorpg.data.reader.DataReader;
import org.forfun.mmorpg.data.reader.ExcelDataReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

@Configuration
public class Config {

    @Bean(name = "conversionService")
    public ConversionService createConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        conversionService.addConverter(new JsonToListConvertor());
        conversionService.addConverter(new JsonToArrayConvertor());
        conversionService.addConverter(new JsonToMapConvertor());
        return conversionService;
    }

    @Bean
    public DataReader createDataReader() {
        DataReader reader = new ExcelDataReader();
        return reader;
    }

    @Bean
    public ResourceProperties createResourceConfiguration() {
        ResourceProperties configuration = new ResourceProperties();
        configuration.setSuffix(".xlsx");
        return configuration;
    }

    @Bean
    public DataManager createDataManager() {
        return new DataManager();
    }

}
