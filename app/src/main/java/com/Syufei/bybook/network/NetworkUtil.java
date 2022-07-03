package com.Syufei.bybook.network;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//实际发起网络请求的地方
public class NetworkUtil implements IHttpRequest {
    private OkHttpClient client;
    private static Context mContext;
    private static Handler mHandler = new Handler();
    private static int cacheSize = 10 * 1024 * 1024;//10 MB
    private static final int NETWORK_SUCCESS = 1;//请求成功
    private static final int NETWORK_FALIED = 2;//请求失败
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public static void init(Context context) {
        mContext = context.getApplicationContext();
        mHandler = getmHandler();
    }

    private static Handler getmHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }

    @Override
    public void doGet(String url, NetWorkListener listener) {
        doGet(url, null, null, listener);
    }

    @Override
    public void doGet(String url, Map<String, String> params, NetWorkListener listener) {
        doGet(url, params, null, listener);
    }

    @Override
    public void doGet(String url, Map<String, String> params, NetworkRepo.OkhttpOption okhttpOption, NetWorkListener listener) {
        url = NetworkRepo.Base_url + url;
        url = NetworkRepo.appendUri(url, params);
        Request.Builder builder = new Request.Builder().url(url).tag(okhttpOption == null ? "" : okhttpOption.getTag());
        builder = configHeaders(builder, okhttpOption);
        Request request = builder.build();
        Log.d("network_util", android.os.Process.myTid() + "");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handleError(e, listener);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("network_util", android.os.Process.myTid() + "");
                handlerResult(response, listener);
            }
        });
    }
    //上传json
    @Override
    public void doPost(String url, String json, NetWorkListener listener) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        url = NetworkRepo.Base_url + url;
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handleError(e, listener);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                handlerResult(response, listener);
            }
        });
    }

    //上传json,带header
    @Override
    public void doPost(String url, String json, NetWorkListener listener, Map<String, String> params) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        url = NetworkRepo.Base_url + url;
        url = NetworkRepo.appendUri(url, params);
        Request.Builder builder=new Request.Builder().url(url).post(requestBody);
//        builder=configPostParams(builder,headers);
        client.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handleError(e, listener);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                handlerResult(response, listener);
            }
        });
    }

//    多图片上传
    @Override
    public void doPost(String url, Map<String, String> params,String fileName, List<String> paths, NetWorkListener listener) {
        url=NetworkRepo.Base_url+url;
        //url=NetworkRepo.appendUri(url,params);
        MultipartBody.Builder builder=new MultipartBody.Builder();
        MediaType type=MediaType.parse("image/jpeg");
        builder.setType(MultipartBody.FORM);
        for (String path:paths){
            File file=new File(path);
            if (file!=null){
                builder.addFormDataPart(fileName,file.getName(),RequestBody.create(type,file));
            }
        }
        if (params!=null && params.keySet().size()>0){
            for (String key:params.keySet()){
                builder.addFormDataPart(key,params.get(key));
            }
        }
        MultipartBody body=builder.build();
        Request request=new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                handleError(e, listener);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                handlerResult(response, listener);
            }
        });
    }

    //post添加参数
    private FormBody.Builder configPostParams(FormBody.Builder builder, Map<String, String> params) {
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                String key = entry.getKey();
                String val = entry.getValue();
                builder.add(key, val);
            }
        }
        return builder;
    }
    //添加Header
    private Request.Builder configPostParams(Request.Builder builder, Map<String, String> params) {
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                String key = entry.getKey();
                String val = entry.getValue();
                builder.addHeader(key, val);
            }
        }
        return builder;
    }

    //添加Header
    private Request.Builder configHeaders(Request.Builder builder, NetworkRepo.OkhttpOption option) {
        if (option == null) {
            return builder;
        }
        Map<String, String> headers = option.getParams();
        if (headers == null || headers.size() == 0) {
            return builder;
        }
        Set<Map.Entry<String, String>> entries = headers.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String val = entry.getValue();
            builder.addHeader(key, val);
        }
        return builder;
    }

    @Override
    public void cancel(String tag) {
        if (client != null) {
            for (Call call : client.dispatcher().queuedCalls()) {
                if (call.request().tag().equals(tag)) {
                    call.cancel();
                }
            }
        }
    }

    private void handlerResult(Response response, final NetWorkListener listener) throws IOException {
        final String result = response.body().string();
        if (listener != null) {
            getmHandler().post(new Runnable() {
                @Override
                public void run() {
                    listener.onSuccess(result);
                }
            });
        }
    }

    private void handleError(IOException e, final NetWorkListener listener) {
        if (listener != null) {
            listener.onFailed(e.getMessage());
        }
    }

    private volatile static NetworkUtil instance;

    public static NetworkUtil getInstance() {
        if (instance == null) {
            synchronized (NetworkUtil.class) {
                if (instance == null) {
                    instance = new NetworkUtil();
                }
            }
        }
        return instance;
    }

    private NetworkUtil() {
        client = new OkHttpClient.Builder()
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .addInterceptor(new TokenIncerptor(mContext))
                .build();
    }

    public interface NetWorkListener {
        void onSuccess(String response);

        void onFailed(String errorMsg);
    }
}
