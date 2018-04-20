package com.example.test_mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test_mvp.R;
import com.example.test_mvp.model.bean.RegisterBean;
import com.example.test_mvp.presenter.RegisterPresenter;
import com.example.test_mvp.utils.CommonUtil;
import com.example.test_mvp.view.interfaces.IRegisterView;
import com.google.gson.Gson;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements IRegisterView,View.OnClickListener {
    private String s;
    private EditText password;
    private EditText mobile;
    private EditText password_sure;
    private RegisterBean registerBean=new RegisterBean();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(RegisterActivity.this, registerBean.getMsg(), Toast.LENGTH_SHORT).show();
                    if (registerBean.getCode().equals("0")){
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void initView() {
        password = findViewById(R.id.password);
        mobile = findViewById(R.id.mobile);
        password_sure = findViewById(R.id.password_sure);
        findViewById(R.id.register).setOnClickListener(this);
    }

    @Override
    void initData() {
        getParent_title().getTitle().setText(getResources().getString(R.string.register));
    }

    @Override
    RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.activity_register;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                String phoneNum = mobile.getText().toString();
                String passwrd = password.getText().toString();
                String passwrd_sure = password_sure.getText().toString();
                if(!CommonUtil.isMobileNO(phoneNum)) {
                    Toast.makeText(RegisterActivity.this,getResources().getString(R.string.wrong_mobile_num),Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!CommonUtil.isPassNO(passwrd)) {
                    Toast.makeText(RegisterActivity.this,getResources().getString(R.string.wrong_password),Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!passwrd.equals(passwrd_sure)) {
                    Toast.makeText(RegisterActivity.this,getResources().getString(R.string.wrong_password_diff),Toast.LENGTH_SHORT).show();
                    return;
                }

                if(getPresenter() != null) {
                    String path = "user/reg";
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("mobile",phoneNum);
                    hashMap.put("password",passwrd);
                    getPresenter().getDataFromServer(path,hashMap);
                }
                break;
        }
    }

    @Override
    public void onSuccess(String success) {
        Gson gson = new Gson();
        registerBean = gson.fromJson(success, RegisterBean.class);
        handler.sendEmptyMessage(1);
        Log.e("---MainActivity---",success);
    }

    @Override
    public void onError(String error) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().detachView();
    }
}
