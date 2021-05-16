package com.example.authdemo.util;

import com.example.authdemo.repository.entities.TargetFileEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TargetJsonHelper {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<TargetFileEntity> jsonToTargetFile(String username, InputStream is) throws UnsupportedEncodingException {
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONArray jsonArray = new JSONArray(responseStrBuilder.toString());

            List<TargetFileEntity> targetFileEntities = new ArrayList<>();
            for (int i = 0; i < jsonArray.toList().size(); i++) {
                JSONObject rec = (JSONObject) jsonArray.get(i);
                TargetFileEntity targetFileEntity = new TargetFileEntity();
                targetFileEntity.setAmount(rec.optDouble("amount"));
                targetFileEntity.setCurrency(rec.optString("currency"));
                targetFileEntity.setValueDate(rec.optString("valueDate"));
                targetFileEntity.setTransactionId(rec.optString("transactionId"));
                targetFileEntity.setUsername(username);
                targetFileEntities.add(targetFileEntity);
            }

            return targetFileEntities;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

}