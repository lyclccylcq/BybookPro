package com.Syufei.bybook.network;

import java.util.Map;

public class NetworkRepo {
    public static final String Base_url = "http:louis296.top:8083";
    //public static final String Base_url = "http://42.193.193.160:8082";
    public static class OkhttpOption {
        private String url;
        private String tag;//log时使用
        private Map<String, String> params;//设置header

        public OkhttpOption(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public static final class Builder {
            public String tag;
            public Map<String, String> params;
            public String url;

            public void setTag(String tag) {
                this.tag = tag;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setParams(Map<String, String> params) {
                this.params = params;
            }

            public OkhttpOption build() {
                OkhttpOption okhttpOption = new OkhttpOption(tag);
                okhttpOption.params = params;
                okhttpOption.url = url;
                return okhttpOption;
            }
        }
    }

//    添加query参数
    public static String appendUri(String url, Map<String, String> params) {
        if (params == null) {
            return url;
        }
        StringBuffer buffer = new StringBuffer();
        if (params.isEmpty()) {
            return url;
        } else if(params.keySet().size()>1){
            buffer.append(url);
            buffer.append("?");
            int size=params.size();
            int index=0;
            for (String key : params.keySet()) {
                buffer.append(key);
                buffer.append("=");
                buffer.append(params.get(key));
                if(index!=size-1){
                    buffer.append("&");
                }
                index++;
            }
        }else if(params.keySet().size()==1){{
            buffer.append(url);
            buffer.append("?");
            for (String key : params.keySet()) {
                buffer.append(key);
                buffer.append("=");
                buffer.append(params.get(key));
            }
        }}
        return buffer.toString();
    }
}
