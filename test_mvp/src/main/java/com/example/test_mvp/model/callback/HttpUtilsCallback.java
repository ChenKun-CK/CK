package com.example.test_mvp.model.callback;

/**
 * author:Created by WangZhiQiang on 2018/4/19.
 */
public interface HttpUtilsCallback {
    void onSuccess(String success);
    void onFail(int errCode,String errMsg);
}
