package com.my.bbs.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @Author liuweicheng
 * @Date 2022/12/9
 **/
public class TranslatorUtil {
    private static String key = "1fc0c615c10d4f48b5da8fcc46d72d50";
    // location, also known as region.
    // required if you're using a multi-service or regional (not global) resource. It can be found in the Azure portal on the Keys and Endpoint page.
    private static String location = "koreacentral";


    /**
     *  translate to english
     * @param content
     * @return
     */
    public static String translate(String content) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        Map<String, String> map = new HashMap<>();
        map.put("Text", content);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        RequestBody body = RequestBody.create(mediaType,
                gson.toJson(Collections.singletonList(map)));
        Request request = new Request.Builder()
                .url("https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&to=en")
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", key)
                // location required if you're using a multi-service or regional (not global) resource.
                .addHeader("Ocp-Apim-Subscription-Region", location)
                .addHeader("Content-type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        JSONObject jsonObject = JSONArray.parseArray(Objects.requireNonNull(response.body()).string()).getJSONObject(0).getJSONArray("translations").getJSONObject(0);
        return jsonObject.getString("text");
    }

    public static void main(String[] args) throws IOException {
        System.out.println(translate("안녕, 너 정말 예쁘다!며칠 있다가 놀러 올 수 있을까?"));
    }
}
