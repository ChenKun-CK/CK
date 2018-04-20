package com.example.test_mvp.presenter;

import com.example.test_mvp.view.interfaces.IBaseView;

/**
 * author:Created by WangZhiQiang on 2018/4/16.
 */
public class BasePresenter<T extends IBaseView> {

    private T t;

    public void attachView(T t){
        this.t=t;
    }

    public T getView(){
        return t;
    }

    public void detachView(){
        t=null;
    }
}
