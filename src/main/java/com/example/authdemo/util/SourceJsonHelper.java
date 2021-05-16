package com.example.authdemo.util;

import com.example.authdemo.repository.entities.SourceFileEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SourceJsonHelper {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<SourceFileEntity> jsonToSourceFile(String username, InputStream is) throws UnsupportedEncodingException {
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONArray jsonArray = new JSONArray(responseStrBuilder.toString());

            List<SourceFileEntity> sourceFileEntities = new ArrayList<>();
            for (int i = 0; i < jsonArray.toList().size(); i++) {
                JSONObject rec = (JSONObject) jsonArray.get(i);
                SourceFileEntity sourceFileEntity = new SourceFileEntity();
                sourceFileEntity.setAmount(rec.optDouble("amount"));
                sourceFileEntity.setCurrency(rec.optString("currency"));
                sourceFileEntity.setValueDate(rec.optString("valueDate"));
                sourceFileEntity.setTransactionId(rec.optString("transactionId"));
                sourceFileEntity.setUsername(username);
                sourceFileEntities.add(sourceFileEntity);
            }

            return sourceFileEntities;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}