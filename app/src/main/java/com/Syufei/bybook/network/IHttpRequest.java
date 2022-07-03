package com.Syufei.bybook.network;

import java.util.List;
import java.util.Map;

public interface IHttpRequest {
    void doGet(String url, NetworkUtil.NetWorkListener listener);
    void doGet(String url, Map<String,String> params, NetworkUtil.NetWorkListener listener);
    void doGet(String url, Map<String,String> params, NetworkRepo.OkhttpOption okhttpOption, NetworkUtil.NetWorkListener listener);

    void doPost(String url, Map<String,String> params,String fileName, List<String> paths, NetworkUtil.NetWorkListener listener);
    void doPost(String url, String json, NetworkUtil.NetWorkListener listener);
    void doPost(String url,String json, NetworkUtil.NetWorkListener listener,Map<String,String> params);
    void cancel(String tag);
}
