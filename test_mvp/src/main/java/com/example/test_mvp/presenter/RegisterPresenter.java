package com.example.test_mvp.presenter;

import com.example.test_mvp.model.callback.HttpUtilsCallback;
import com.example.test_mvp.model.http.NetUtil;
import com.example.test_mvp.view.interfaces.IRegisterView;

import java.util.HashMap;

/**
 * author:Created by WangZhiQiang on 2018/4/16.
 */
public class RegisterPresenter extends BasePresenter<IRegisterView>{

    private NetUtil netUtil;

    public RegisterPresenter() {
        netUtil = NetUtil.getInstance();
    }

    public void getDataFromServer(String path,HashMap<String, String> map) {
        netUtil.doPost(path, map, new HttpUtilsCallback() {
            @Override
            public void onSuccess(String success) {
                getView().onSuccess(success);
            }
            @Override
            public void onFail(int errCode, String errMsg) {

            }
        });
    }
}
