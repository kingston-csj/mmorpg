package org.forfun.mmorpg.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static void main(String[] args) throws Exception {
        URL resource = CsvReader.class.getResource("/p_skill.csv");
        InputStream inputStream = new FileInputStream(resource.getPath());
        Reader in = new InputStreamReader(inputStream);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
        boolean hasColMeta = false;
        String[] header = null;

        List<String[]> values = new ArrayList<>();
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
            values.add(readRecord(record));

            if ("end".equalsIgnoreCase(record.get(0))) {
                // 结束符号
                break;
            }
        }

        new SkillStorage<>().read(P_Skill.class, header, values);
        System.out.println(records);
    }

    private static String[] readRecord(CSVRecord record) {
        String[] columns;
        columns = new String[record.size() - 1];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = record.get(i + 1);
        }
        return columns;
    }
}
