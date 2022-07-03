package com.Syufei.bybook.repo;

import com.alibaba.fastjson.JSON;
import com.Syufei.bybook.bean.BookBean;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.network.NetworkUtil;

import java.util.HashMap;
import java.util.Map;

public class TryBookRepo {
    private static final String Car_list = "/car/list";//获取车辆列表
    private static final String Create_try_car = "/test_drive/create";
    private static final String Get_try_car_list = "/test_drive/list";
    private static final String Cancel_try_car = "/test_drive/cancel";

//    获得车辆列表
    public static void getBooklist(final DataSuccessListenter listenter) {
        NetworkUtil.getInstance().doGet(Car_list, new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                BookBean.BookList bookList = JSON.parseObject(response, BookBean.BookList.class);
                listenter.onDataSuccess(bookList);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

//    创建试驾行程
    public static void createTryBook(int bookId, String address, String phone, final DataSuccessListenter listenter){
        BookBean.CreateTrybook createTrycar=new BookBean.CreateTrybook(bookId,address,phone);
        NetworkUtil.getInstance().doPost(Create_try_car, JSON.toJSONString(createTrycar), new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                BookBean.CommonResponse commonResponse=JSON.parseObject(response, BookBean.CommonResponse.class);
                listenter.onDataSuccess(commonResponse);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

//  获得试驾列表
    public static void tryBookList(int Limit,int Offset,final DataSuccessListenter listenter){
        Map<String ,String > params=new HashMap<>();
        params.put("Limit",Limit+"");
        params.put("Offset",Offset+"");
        NetworkUtil.getInstance().doGet(Get_try_car_list, params, new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                BookBean.TryBookList tryBookList=JSON.parseObject(response, BookBean.TryBookList.class);
                listenter.onDataSuccess(tryBookList);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onDataSuccess(errorMsg);
            }
        });
    }

//    取消试驾行程
    public static void cancelTryBook(int id,final DataSuccessListenter listenter){
        BookBean.IdCommon idCommon=new BookBean.IdCommon();
        idCommon.setId(id);
        NetworkUtil.getInstance().doPost(Cancel_try_car, JSON.toJSONString(idCommon), new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                BookBean.CommonBean commonBean=JSON.parseObject(response, BookBean.CommonBean.class);
                listenter.onDataSuccess(commonBean);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }
}
