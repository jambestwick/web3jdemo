package mona;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author jambestwick
 * @create 2021/11/29 0029  23:45
 * @email jambestwick@126.com
 */
public class RequestUtil {
    private static final Logger log = LoggerFactory.getLogger(RequestUtil.class);


    public static String requestGet(String url) {
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(url)
                .get().build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();

            String result = response.body().string();
            log.debug("response:" + result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String requestPost(String url, Map<String, String> requestMap, Map<String, String> headMap) {
        // &weaid=1&date=2018-08-13&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json";

        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : requestMap.keySet()) {
            String value = requestMap.get(key);
            builder.add(key, value);
        }

        Headers headers = Headers.of(headMap);
        RequestBody body = builder.build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .headers(headers)
                .post(body)
                .build();

        try {
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            if (response.code() == 200) {
                return response.body().toString();
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
