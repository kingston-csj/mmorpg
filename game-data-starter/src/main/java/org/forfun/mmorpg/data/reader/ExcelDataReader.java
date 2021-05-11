package org.forfun.mmorpg.data.reader;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelDataReader implements DataReader, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ExcelDataReader.class.getName());
    private static final String BEGIN = "header";
    private static final String END = "end";
    private final TypeDescriptor sourceType = TypeDescriptor.valueOf(String.class);

    private ApplicationContext applicationContext;

    @Override
    public <E> List<E> read(InputStream is, Class<E> clazz) {
        try {
            Workbook workbook = WorkbookFactory.create(is);

            boolean hasColMeta = false;
            ExcelHeader[] header = null;
            // 一行行的数据源
            List<ExcelColumn[]> records = new ArrayList<>();

            Sheet sheet = workbook.getSheetAt(0);
            // 获取行
            Iterator<Row> rows = sheet.rowIterator();

            while (rows.hasNext()) {
                Row row = rows.next();
                String firstCell = getCellValue(row.getCell(0));
                if (BEGIN.equalsIgnoreCase(firstCell)) {
                    header = readHeader(clazz, row.iterator());
                    hasColMeta = true;
                    continue;
                }
                if (!hasColMeta) {
                    continue;
                }

                records.add(readExcelRow(header, row.iterator()));
                if (END.equalsIgnoreCase(firstCell)) {
                    // 结束符号
                    break;
                }
            }

            return readRecords(clazz, records);
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }

    private <E> List<E> readRecords(Class<E> clazz, List<ExcelColumn[]> rows) throws Exception {
        List<E> records = new ArrayList<>(rows.size());
        ConversionService conversionService = applicationContext.getBean(ConversionService.class);
        for (int i = 0; i < rows.size(); i++) {
            ExcelColumn[] record = rows.get(i);
            E obj = clazz.newInstance();

            for (ExcelColumn column : record) {
                String colName = column.header.column;
                if (StringUtils.isEmpty(colName)) {
                    continue;
                }

                Field field = clazz.getDeclaredField(colName);
                field.setAccessible(true);
                Object fieldVal = conversionService.convert(column.value, sourceType, new TypeDescriptor(field));
                field.set(obj, fieldVal);
            }

            records.add(obj);
        }

        return records;
    }

    private ExcelHeader[] readHeader(Class clazz, Iterator<Cell> cells) throws NoSuchFieldException {
        List<ExcelHeader> columns = new ArrayList<>();
        while (cells.hasNext()) {
            Cell cell = cells.next();
            String cellValue = getCellValue(cell);
            if (BEGIN.equalsIgnoreCase(cellValue)) {
                continue;
            }
            ExcelHeader header = new ExcelHeader();
            header.column = cellValue;
            if (StringUtils.isNotEmpty(header.column)) {
                header.field = clazz.getDeclaredField(header.column);
                header.field.setAccessible(true);
            }
            columns.add(header);
        }

        return columns.toArray(new ExcelHeader[columns.size()]);
    }

    private String getCellValue(Cell cell) {
        if (cell.getCellTypeEnum() != CellType.STRING) {
            cell.setCellType(CellType.STRING);
        }
        return cell.getStringCellValue();
    }

    private ExcelColumn[] readExcelRow(ExcelHeader[] headers, Iterator<Cell> cells) {
        List<ExcelColumn> columns = new ArrayList<>();
        int index = 0;
        boolean first = true;
        while (cells.hasNext()) {
            Cell cell = cells.next();
            if (first) {
                first = false;
                continue;
            }

            String cellValue = getCellValue(cell);
            ExcelColumn column = new ExcelColumn();
            column.header = headers[index];
            column.value = cellValue;
            columns.add(column);
            index++;
        }
        return columns.toArray(new ExcelColumn[columns.size()]);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    static class ExcelHeader {

        String column;

        Field field;
    }

    static class ExcelColumn {

        ExcelHeader header;

        String value;
    }
}


