package com.Syufei.bybook.repo;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.Syufei.bybook.bean.CityList;
import com.Syufei.bybook.network.DataSuccessListenter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CityRepo {
    private static final OkHttpClient client=new OkHttpClient();
    private static final String key = "V4BBZ-EZDLX-5IY4T-7LCOK-RJJ35-SBF5U";
    private static final String tag = "city_repo";
    private static final String provinceApi = "https://apis.map.qq.com/ws/district/v1/list";
    private static final String citiesApi = "https://apis.map.qq.com/ws/district/v1/getchildren";
    private static Handler mHandler = new Handler();
    public static void queryProvinces(final DataSuccessListenter listenter) {
//        HashMap<String, String> keyMap = new HashMap<>();
//        keyMap.put("key", key);
//        NetworkUtil.getInstance().doGet(provinceApi, keyMap, new NetworkUtil.NetWorkListener() {
//            @Override
//            public void onSuccess(String response) {
//                CityList.Province province = (CityList.Province) JSON.parseObject(response, CityList.Province.class);
//                listenter.onDataSuccess(province);
//            }
//
//            @Override
//            public void onFailed(String errorMsg) {
//                Log.e(tag, errorMsg);
//            }
//        })
        String url = provinceApi;
        StringBuilder builder=new StringBuilder();
        builder.append(url);
        builder.append("?key=");
        builder.append(key);
        Request request = new Request.Builder()
                .url(builder.toString())
                .build();
         client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listenter.onError(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String body=response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        CityList.Province province = (CityList.Province) JSON.parseObject(body, CityList.Province.class);
                        listenter.onDataSuccess(province);
                    }
                });
            }
        });
    }

    public static void queryChildrennCity(String id, final DataSuccessListenter listenter) {
//        HashMap<String, String> keyMap = new HashMap<>();
//        keyMap.put("key", key);
//        keyMap.put("id", id);
//        NetworkUtil.getInstance().doGet(citiesApi, keyMap, new NetworkUtil.NetWorkListener() {
//            @Override
//            public void onSuccess(String response) {
//                CityList.ChildrenCity city = (CityList.ChildrenCity) JSON.parseObject(response, CityList.ChildrenCity.class);
//                listenter.onDataSuccess(city);
//            }
//
//            @Override
//            public void onFailed(String errorMsg) {
//                Log.e(tag, errorMsg);
//            }
//        });


        final OkHttpClient client=new OkHttpClient();
        String url = citiesApi;
        StringBuilder builder=new StringBuilder();
        builder.append(url);
        builder.append("?key=");
        builder.append(key);
        builder.append("&id=");
        builder.append(id);
        Request request = new Request.Builder()
                .url(builder.toString())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                listenter.onError(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String body=response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        CityList.ChildrenCity city = (CityList.ChildrenCity) JSON.parseObject(body, CityList.ChildrenCity.class);
                        listenter.onDataSuccess(city);

                    }
                });
            }
        });
    }
}
