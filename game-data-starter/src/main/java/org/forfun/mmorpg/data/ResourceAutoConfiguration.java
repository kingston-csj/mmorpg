package org.forfun.mmorpg.data;

import org.forfun.mmorpg.data.convertor.JsonToArrayConvertor;
import org.forfun.mmorpg.data.convertor.JsonToListConvertor;
import org.forfun.mmorpg.data.convertor.JsonToMapConvertor;
import org.forfun.mmorpg.data.reader.CsvDataReader;
import org.forfun.mmorpg.data.reader.DataReader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(ResourceProperties.class)
public class ResourceAutoConfiguration {

    @Resource
    private ResourceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public DataReader createDataReader() {
        return new CsvDataReader();
    }

    @Bean(name = "dataConversionService")
//    @ConditionalOnMissingBean
    public ConversionService createConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        conversionService.addConverter(new JsonToListConvertor());
        conversionService.addConverter(new JsonToArrayConvertor());
        conversionService.addConverter(new JsonToMapConvertor());
        return conversionService;
    }

    @Bean
    public DataManager createDataManager() {
        DataManager dataManager = new DataManager();
        return dataManager;
    }

}
