package com.example.authdemo.util;

import com.example.authdemo.models.FileInfo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    public static List<FileInfo> csvToFileInfo(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<FileInfo> fileInfos = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                FileInfo fileInfo = new FileInfo(
                        csvRecord.get("transactionId"),
                        Double.parseDouble(csvRecord.get("amount")),
                        csvRecord.get("currency"),
                        csvRecord.get("valueDate")
                );

                fileInfos.add(fileInfo);
            }
            return fileInfos;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}