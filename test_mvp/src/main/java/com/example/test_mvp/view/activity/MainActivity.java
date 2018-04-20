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
import com.example.test_mvp.model.bean.LoginBean;
import com.example.test_mvp.presenter.MainPresenter;
import com.example.test_mvp.utils.CommonUtil;
import com.example.test_mvp.view.interfaces.IMainView;
import com.google.gson.Gson;

import java.util.HashMap;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView, View.OnClickListener {

    private EditText et_mobile;
    private EditText et_password;
    private LoginBean loginBean=new LoginBean();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Toast.makeText(MainActivity.this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                    //  code":"0"    登陆成功
                    if (loginBean.getCode().equals("0")){
                        Log.e("--MainActivity--","跳转到内容页");
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
        //初始化控件
        et_mobile = findViewById(R.id.et_mobile);
        et_password = findViewById(R.id.et_password);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
    }

    @Override
    void initData() {
        //设置标题名称
        getParent_title().getTitle().setText(getResources().getString(R.string.login));
    }

    @Override
    MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    int setChildContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onSuccess(String success) {
        //gson解析数据
        Gson gson = new Gson();
        loginBean = gson.fromJson(success, LoginBean.class);
        //handler通知主线程操作数据
        handler.sendEmptyMessage(0);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //判断账号密码    是否符合规格
                String name = et_mobile.getText().toString();
                String pass = et_password.getText().toString();
                if(!CommonUtil.isMobileNO(name)) {
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.wrong_mobile_num),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!CommonUtil.isPassNO(pass)) {
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.wrong_password),Toast.LENGTH_SHORT).show();
                    return;
                }
                //发送账号密码验证登录
                if(getPresenter() != null) {
                    String path = "user/login";
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("mobile",name);
                    hashMap.put("password",pass);
                    getPresenter().getDataFromServer(path,hashMap);
                }
                break;
            case R.id.btn_register:
                //跳转到注册页面
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
