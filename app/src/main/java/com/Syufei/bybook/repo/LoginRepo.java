package com.Syufei.bybook.repo;

import com.alibaba.fastjson.JSON;
import com.Syufei.bybook.bean.BookBean;
import com.Syufei.bybook.bean.LoginBean;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.network.NetworkUtil;

public class LoginRepo {
    public static final String Login_url = "/user/login";//登录接口
    public static final String Register_url = "/user/register";//注册接口
    public static final String GetUserInfo_url =  "/user/info";//获取用户信息接口

    public static void requestLogin(String userId, String password, final DataSuccessListenter dataSuccessListenter) {
        LoginBean.LoginBody loginBody=new LoginBean.LoginBody(userId,password);
        NetworkUtil.getInstance().doPost(Login_url, JSON.toJSONString(loginBody), new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                LoginBean.LoginResponse loginResponse = JSON.parseObject(response, LoginBean.LoginResponse.class);
                dataSuccessListenter.onDataSuccess(loginResponse);
            }

            @Override
            public void onFailed(String errorMsg) {
                dataSuccessListenter.onError(errorMsg);
            }
        });
    }

    public static void requestRegister(String userId, String password, String userName, final DataSuccessListenter dataSuccessListenter) {
        LoginBean.RegisterBody registerBody=new LoginBean.RegisterBody(userId,password,userName);
        NetworkUtil.getInstance().doPost(Register_url, JSON.toJSONString(registerBody), new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                BookBean.CommonResponse commonResponse=JSON.parseObject(response, BookBean.CommonResponse.class);
                dataSuccessListenter.onDataSuccess(commonResponse);
            }

            @Override
            public void onFailed(String errorMsg) {
                dataSuccessListenter.onError(errorMsg);
            }
        });
    }

    public static void getUserInfo(final DataSuccessListenter dataSuccessListenter) {
        NetworkUtil.getInstance().doGet(GetUserInfo_url, new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                LoginBean.UserInfo userInfo = JSON.parseObject(response, LoginBean.UserInfo.class);
                dataSuccessListenter.onDataSuccess(userInfo);
            }

            @Override
            public void onFailed(String errorMsg) {
                dataSuccessListenter.onError(errorMsg);
            }
        });
    }
}
