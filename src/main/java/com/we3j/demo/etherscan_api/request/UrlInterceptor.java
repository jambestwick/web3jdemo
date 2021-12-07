package com.we3j.demo.etherscan_api.request;

import com.we3j.demo.etherscan_api.key.ApiKey;
import com.we3j.demo.utils.Environment;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @Author jambestwick
 * @create 2021/12/7 0007  22:42
 * @email jambestwick@126.com
 */
public class UrlInterceptor implements Interceptor {
    public static final String NAME_BASE_URL = "baseUrl";
    private String baseUrl = null;

    public UrlInterceptor(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        //获取request
        Request request = chain.request();
        //获取request的创建者builder
        Request.Builder builder = request.newBuilder();

        HttpUrl oldHttpUrl = request.url();   //从request中获取原有的HttpUrl实例oldHttpUrl
        if (baseUrl == null) {
            throw new IOException("baseUrl not init");
        }
        HttpUrl newBaseUrl = HttpUrl.parse(baseUrl);

        //重建新的HttpUrl，修改需要修改的url部分
        HttpUrl newFullUrl = oldHttpUrl
                .newBuilder()
                .scheme(newBaseUrl.scheme())
                .host(newBaseUrl.host())
                .port(newBaseUrl.port())
                .addQueryParameter("apikey", ApiKey.KEY)
                .build();

        //然后返回一个response至此结束修改
        return chain.proceed(builder.url(newFullUrl).build());

    }

}
