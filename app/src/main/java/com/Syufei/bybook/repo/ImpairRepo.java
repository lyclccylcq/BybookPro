package com.Syufei.bybook.repo;

import com.alibaba.fastjson.JSON;
import com.Syufei.bybook.bean.AfterOrderBean;
import com.Syufei.bybook.bean.BookBean;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.network.NetworkUtil;

import java.util.HashMap;
import java.util.Map;

public class ImpairRepo {
    private static final String GetSelfBook = "/car/self_list";
    private static final String SaleOrderList = "/after_sale_order/list";
    private static final String CreateAfterOrder = "/after_sale_order/create";
    private static final String CancelAfterOrder = "/after_sale_order/cancel";

    public static void getSelfCarList(final DataSuccessListenter listenter) {
        NetworkUtil.getInstance().doGet(GetSelfBook, new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                AfterOrderBean.SelfBook selfBook = JSON.parseObject(response, AfterOrderBean.SelfBook.class);
                listenter.onDataSuccess(selfBook);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

    //  type :0代表维修单，1代表保养单
    public static void getAfterOrders(int limit, int offset, int type, final DataSuccessListenter listenter) {
        Map<String, String> params = new HashMap<>();
        params.put("Limit", limit + "");
        params.put("Offset", offset + "");
        params.put("Type", type + "");
        NetworkUtil.getInstance().doGet(SaleOrderList, params, new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                AfterOrderBean.AfterOrder afterOrder = JSON.parseObject(response, AfterOrderBean.AfterOrder.class);
                listenter.onDataSuccess(afterOrder.getData().getList());
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

    public static void createAfterOrder(int type, int id, String address, final DataSuccessListenter listenter) {
        Map<String, String> params = new HashMap<>();
        params.put("Type", type + "");
        AfterOrderBean.CreateAfterOrder order = new AfterOrderBean.CreateAfterOrder();
        order.setSaleOrderId(id);
        order.setAddress(address);
        NetworkUtil.getInstance().doPost(CreateAfterOrder, JSON.toJSONString(order), new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                AfterOrderBean.CreateAfterOrderRes orderRes = JSON.parseObject(response, AfterOrderBean.CreateAfterOrderRes.class);
                listenter.onDataSuccess(orderRes);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        }, params);
    }

    public static void cancelAfterOrder(int id, final DataSuccessListenter listenter) {
        AfterOrderBean.CancelOrder order = new AfterOrderBean.CancelOrder();
        order.setAfterSaleOrderId(id);
        NetworkUtil.getInstance().doPost(CancelAfterOrder, JSON.toJSONString(order), new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                BookBean.CommonResponse response1 = JSON.parseObject(response, BookBean.CommonResponse.class);
                listenter.onDataSuccess(response1);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }
}
