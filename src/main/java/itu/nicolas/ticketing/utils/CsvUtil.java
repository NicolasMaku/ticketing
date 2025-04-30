package itu.nicolas.ticketing.utils;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CsvUtil {
    public static <T> byte[] exportToCsvBytes(List<T> data, Class<T> clazz) {
        try {
            CsvMapper mapper = new CsvMapper();
            mapper.registerModule(new JavaTimeModule());

            CsvSchema schema = mapper.schemaFor(clazz).withHeader();
            return mapper.writer(schema).writeValueAsBytes(data);
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
