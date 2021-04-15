package org.forfun.mmorpg.csv.reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.forfun.mmorpg.csv.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CsvReader implements DataReader {

    private static Logger logger = LoggerFactory.getLogger(CsvReader.class.getName());
    private TypeDescriptor sourceType = TypeDescriptor.valueOf(String.class);

    @Override
    public <E> List<E> read(InputStream is, Class<E> clazz) {
        Reader in = new InputStreamReader(is);
        try {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            boolean hasColMeta = false;
            String[] header = null;
            List<String[]> rows = new ArrayList<>();
            for (CSVRecord record : records) {
                // server前面的数据无效
                if ("server".equalsIgnoreCase(record.get(0))) {
                    header = readRecord(record);
                    hasColMeta = true;
                    continue;
                }
                if (!hasColMeta) {
                    continue;
                }
                rows.add(readRecord(record));

                if ("end".equalsIgnoreCase(record.get(0))) {
                    // 结束符号
                    break;
                }
            }

            List<E> data = new ArrayList<>(rows.size());
            ConversionService conversionService = AppContext.getConversionService();
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
}
