package com.cathayholdings.homework.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class HttpConnectManager {

    private final OkHttpClient client;

    private final ObjectMapper objectMapper;

    public HttpConnectManager(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.client = createHttpClient();
    }

    private OkHttpClient createHttpClient() {
        Dispatcher dispatcher = new Dispatcher();
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES))
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .dispatcher(dispatcher)
                .build();
    }

    public String get(String url) {
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public <ResClz> ResClz get(String url, Class<ResClz> resType) {
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            if (HttpStatus.OK.value() != response.code()) {
                throw new RuntimeException("API Error, Response Code = " + response.code());
            }

            String resBody = Objects.requireNonNull(response.body()).string();
            return objectMapper.readValue(resBody, resType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
