package org.forfun.mmorpg.data.reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CsvReader implements DataReader, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(CsvReader.class.getName());
    private static final String BEGIN = "header";
    private static final String END = "end";
    private final TypeDescriptor sourceType = TypeDescriptor.valueOf(String.class);
    private ApplicationContext applicationContext;

    @Override
    public <E> List<E> read(InputStream is, Class<E> clazz) {
        Reader in = new InputStreamReader(is);
        try {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            boolean hasColMeta = false;
            String[] header = null;
            // 一行行的数据源
            List<String[]> rows = new ArrayList<>();
            for (CSVRecord record : records) {
                // BEGIN前面的数据无效
                if (BEGIN.equalsIgnoreCase(record.get(0))) {
                    header = readRecord(record);
                    hasColMeta = true;
                    continue;
                }
                if (!hasColMeta) {
                    continue;
                }
                rows.add(readRecord(record));

                if (END.equalsIgnoreCase(record.get(0))) {
                    // 结束符号
                    break;
                }
            }

            List<E> data = new ArrayList<>(rows.size());
            ConversionService conversionService = applicationContext.getBean(ConversionService.class);
            for (int i = 0; i < rows.size(); i++) {
                String[] record = rows.get(i);
                E obj = clazz.newInstance();
                for (int j = 0; j < header.length; j++) {
                    String colName = header[j];
                    if (StringUtils.isEmpty(colName)) {
                        continue;
                    }

                    Field field = clazz.getDeclaredField(colName);
                    field.setAccessible(true);
                    Object fieldVal = conversionService.convert(record[j], sourceType, new TypeDescriptor(field));
                    field.set(obj, fieldVal);
                }
                data.add(obj);
            }

            return data;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    private String[] readRecord(CSVRecord record) {
        String[] columns;
        columns = new String[record.size() - 1];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = record.get(i + 1);
        }
        return columns;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
