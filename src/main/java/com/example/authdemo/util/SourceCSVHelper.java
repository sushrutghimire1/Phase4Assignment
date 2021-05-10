package com.example.authdemo.util;

import com.example.authdemo.repository.entities.SourceFileEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SourceCSVHelper {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<SourceFileEntity> csvToTargetFile(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<SourceFileEntity> tutorials = new ArrayList<SourceFileEntity>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                SourceFileEntity tutorial = new SourceFileEntity(
                        csvRecord.get("transactionId"),
                        Double.parseDouble(csvRecord.get("amount")),
                        csvRecord.get("currency"),
                        new SimpleDateFormat("yyyy-MM-dd").parse(csvRecord.get("Published"))
                );

                tutorials.add(tutorial);
            }
            return tutorials;
        } catch (IOException | ParseException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}