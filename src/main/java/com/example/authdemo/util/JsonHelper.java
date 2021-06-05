package com.example.authdemo.util;

import com.example.authdemo.models.FileInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    public static List<FileInfo> jsonToSourceFile(InputStream is) throws UnsupportedEncodingException {
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                System.out.println(inputStr);
                responseStrBuilder.append(inputStr);
            }

            JSONArray jsonArray = new JSONArray(responseStrBuilder.toString());

            List<FileInfo> fileInfos = new ArrayList<>();
            for (int i = 0; i < jsonArray.toList().size(); i++) {
                JSONObject rec = (JSONObject) jsonArray.get(i);
                FileInfo fileInfo = new FileInfo();
                fileInfo.setAmount(rec.optDouble("amount"));
                fileInfo.setCurrency(rec.optString("currency"));
                fileInfo.setValueDate(rec.optString("valueDate"));
                fileInfo.setTransactionId(rec.optString("transactionId"));
                fileInfos.add(fileInfo);
            }

            return fileInfos;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}