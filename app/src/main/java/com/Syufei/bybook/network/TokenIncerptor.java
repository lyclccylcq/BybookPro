package com.Syufei.bybook.network;

import android.content.Context;

import androidx.annotation.NonNull;

import com.Syufei.bybook.util.SharedPrefTools;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenIncerptor implements Interceptor {
    private Context context;

    public TokenIncerptor(Context context) {
        this.context = context;
    }
//添加token
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String token = (String) SharedPrefTools.get(context,"bycar_token","");
        Request request = chain.request();
          if(token!=null &&!token.equals("")){
            request = request.newBuilder()
                    .addHeader("Authorization",token)
                    .build();
        }
        return chain.proceed(request);
    }
}
