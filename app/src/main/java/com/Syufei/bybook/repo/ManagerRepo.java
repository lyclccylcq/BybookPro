package com.Syufei.bybook.repo;

import com.alibaba.fastjson.JSON;
import com.Syufei.bybook.bean.BookBean;
import com.Syufei.bybook.bean.ManagerBean;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.network.NetworkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;

public class ManagerRepo {
//    订单管理
    private static final String GetAllOrders = "/sale_order/list_all";//所有订单列表
    private static final String DealWithOrder = "/sale_order/process";//处理订单
    private static final String FinishOrder = "/sale_order/finish";//完成订单
//    车辆管理
    private static final String AddCar = "/car/create";//创建车辆
    private static final String DeleteCar = "/car/delete";//删除车辆
    private static final String UploadCarImg = "/car/img_upload";//上传车辆图片
//    试驾管理
    private static final String GetTrycarList = "/test_drive/list_all";//获取试驾列表
//    售后管理
    private static final String GetALlAfterOrders = "/after_sale_order/list_all";//获取售后列表
    private static final String DealAfterOrder = "/after_sale_order/process";//处理售后订单
    private static final String AfterOrderPrice = "/after_sale_order/quote";//售后订单报价


    public static void GetOrderList(int limit, int offset, final DataSuccessListenter listenter) {
        Map<String, String> params = new HashMap<>();
        params.put("Limit", limit + "");
        params.put("Offset", offset + "");
        NetworkUtil.getInstance().doGet(GetAllOrders, params, new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                ManagerBean.SaleList saleList = JSON.parseObject(response, ManagerBean.SaleList.class);
                listenter.onDataSuccess(saleList);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

    public static void processOrder(int orderId, final DataSuccessListenter listenter) {
        ManagerBean.OrderIdBean orderIdBean = new ManagerBean.OrderIdBean();
        orderIdBean.setOrderId(orderId);
        NetworkUtil.getInstance().doPost(DealWithOrder, JSON.toJSONString(orderIdBean), new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                BookBean.CommonResponse commonResponse = JSON.parseObject(response, BookBean.CommonResponse.class);
                listenter.onDataSuccess(commonResponse);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

    public static void finishOrder(int orderId, final DataSuccessListenter listenter) {
        ManagerBean.OrderIdBean orderIdBean = new ManagerBean.OrderIdBean();
        orderIdBean.setOrderId(orderId);
        NetworkUtil.getInstance().doPost(FinishOrder, JSON.toJSONString(orderIdBean), new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                BookBean.CommonResponse commonResponse = JSON.parseObject(response, BookBean.CommonResponse.class);
                listenter.onDataSuccess(commonResponse);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

    public static void addCar(String name, String author, String price, String des, final DataSuccessListenter listenter) {
        ManagerBean.AddBook addBook = new ManagerBean.AddBook();
        addBook.setName(name);
        addBook.setPrice(price);
        addBook.setAuthor(author);
        addBook.setDescription(des);
        NetworkUtil.getInstance().doPost(AddCar, JSON.toJSONString(addBook), new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                BookBean.CommonResponse commonResponse = JSON.parseObject(response, BookBean.CommonResponse.class);
                listenter.onDataSuccess(commonResponse);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

    public static void deleteCar(int bookId, final DataSuccessListenter listenter) {
        ManagerBean.DeleteBook deleteBook = new ManagerBean.DeleteBook();
        deleteBook.setBookId(bookId);
        NetworkUtil.getInstance().doPost(DeleteCar, JSON.toJSONString(deleteBook), new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                BookBean.CommonResponse commonResponse = JSON.parseObject(response, BookBean.CommonResponse.class);
                listenter.onDataSuccess(commonResponse);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

    public static void GetTrycarList(int limit, int offset, final DataSuccessListenter listenter) {
        Map<String, String> params = new HashMap<>();
        params.put("Limit", limit + "");
        params.put("Offset", offset + "");
        NetworkUtil.getInstance().doGet(GetTrycarList, params, new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                ManagerBean.TrybookList saleList = JSON.parseObject(response, ManagerBean.TrybookList.class);
                listenter.onDataSuccess(saleList);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

    public static void UploadCarImg(String path,int id,final DataSuccessListenter listenter){
        Map<String,String> params=new HashMap<>();
        params.put("Id",id+"");
        MediaType mediaType = MediaType.parse("image/jpeg");
        List<String> paths=new ArrayList<>();
        paths.add(path);
//        NetworkUtil.getInstance().doPost(UploadCarImg, params,paths, new NetworkUtil.NetWorkListener() {
//            @Override
//            public void onSuccess(String response) {
//                CarBean.CommonResponse commonResponse=JSON.parseObject(response, CarBean.CommonResponse.class);
//                listenter.onDataSuccess(commonResponse);
//            }
//
//            @Override
//            public void onFailed(String errorMsg) {
//                listenter.onError(errorMsg);
//            }
//        });
    }

    public static void  getAfterOrderList(int limit, int offset, int type,final DataSuccessListenter listenter){
        Map<String, String> params = new HashMap<>();
        params.put("Limit", limit + "");
        params.put("Offset", offset + "");
        params.put("Type", type + "");
        NetworkUtil.getInstance().doGet(GetALlAfterOrders, params, new NetworkUtil.NetWorkListener() {
            @Override
            public void onSuccess(String response) {
                ManagerBean.AfterOrderList afterOrderList=JSON.parseObject(response, ManagerBean.AfterOrderList.class);
                listenter.onDataSuccess(afterOrderList);
            }

            @Override
            public void onFailed(String errorMsg) {
                listenter.onError(errorMsg);
            }
        });
    }

    public static void processAfterOrder(int id,final DataSuccessListenter listenter){
        ManagerBean.AfterOrderId afterOrderId=new ManagerBean.AfterOrderId();
        afterOrderId.setAfterSaleOrderId(id);
        NetworkUtil.getInstance().doPost(DealAfterOrder, JSON.toJSONString(afterOrderId), new NetworkUtil.NetWorkListener() {
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

    public static void afterOrderPrice(double price,int id,final DataSuccessListenter listenter){
        ManagerBean.AfterOrderPrice afterOrderPrice=new ManagerBean.AfterOrderPrice();
        afterOrderPrice.setPrice(price);
        afterOrderPrice.setAfterSaleOrderId(id);
        NetworkUtil.getInstance().doPost(AfterOrderPrice, JSON.toJSONString(afterOrderPrice), new NetworkUtil.NetWorkListener() {
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
}
