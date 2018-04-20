package com.example.test_mvp.model.http;

import com.example.test_mvp.application.Constant;
import com.example.test_mvp.model.callback.HttpUtilsCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author:Created by WangZhiQiang on 2018/4/16.
 */
public class NetUtil implements Callback {
    private static NetUtil INSTANCE;
    private final OkHttpClient okHttpClient;
    private HttpUtilsCallback httpUtilsCallback;

    private NetUtil() {
        okHttpClient = new OkHttpClient.Builder().build();
    }

    public static NetUtil getInstance(){
        if (INSTANCE==null){
            INSTANCE = new NetUtil();
        }
        return INSTANCE;
    }

    public String doGet(String path){
        String url = "https://www.zhaoapi.cn/user/login?mobile=12345678901&password=123456";
        Request request = new Request.Builder()
                .url(Constant.BASE_URL+path)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(this);
        return "ok";
    }

    public String doPost(String path, HashMap<String,String> map,HttpUtilsCallback httpUtilsCallback){
        this.httpUtilsCallback = httpUtilsCallback;

        FormBody.Builder builder = new FormBody.Builder();

        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            String value = map.get(key);
            builder.add(key,value);
        }

        FormBody body = builder.build();

        Request request = new Request.Builder()
                .url(Constant.BASE_URL+path)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(this);
        return "";
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String string = response.body().string();
        httpUtilsCallback.onSuccess(string);
    }
}
