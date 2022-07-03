package com.Syufei.bybook.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lzhihua.bycar.bean.CarBean;
import com.lzhihua.bycar.bean.ManagerBean;
import com.lzhihua.bycar.network.DataSuccessListenter;
import com.lzhihua.bycar.repo.ManagerRepo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ManageAfterViewmodel extends ViewModel {
    private MutableLiveData<Integer> typeLivedata = new MutableLiveData<>(0);//类型记录
    private MutableLiveData<List<ManagerBean.AfterOrderList.Result>> listLiveData = new MutableLiveData<>();//数据观察\
    private MutableLiveData<Boolean> showProgress=new MutableLiveData<>(false);
    private MutableLiveData<Boolean> showBottom=new MutableLiveData<>(false);

    private int selectId;

    public int getSelectId() {
        return selectId;
    }
    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }

    public MutableLiveData<Boolean> getShowBottom() {
        return showBottom;
    }
    //    private MutableLiveData<Integer> offsetData = new MutableLiveData<>();//翻页
    private AtomicInteger offset=new AtomicInteger(1);
    private MutableLiveData<Boolean> isLastPage = new MutableLiveData<>();//是否到最后一页

    public MutableLiveData<Integer> getTypeLivedata() {
        return typeLivedata;
    }
    public MutableLiveData<Boolean> getIsLastPage() {
        return isLastPage;
    }
    public AtomicInteger getOffset() {
        return offset;
    }

    public MutableLiveData<Boolean> getShowProgress() {
        return showProgress;
    }

    public void setOffset(int offset) {
        this.offset.set(offset);
        queryData(typeLivedata.getValue().intValue());
    }

    public MutableLiveData<List<ManagerBean.AfterOrderList.Result>> getListLiveData() {
        return listLiveData;
    }

//    public MutableLiveData<Integer> getOffsetData() {
//        return offsetData;
//    }

    public ManageAfterViewmodel() {
        super();
    }

    public void queryData(int type) {
        showProgress.setValue(true);
        ManagerRepo.getAfterOrderList(10, offset.get(), type, new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                showProgress.setValue(false);
                ManagerBean.AfterOrderList afterOrderList = (ManagerBean.AfterOrderList) obj;
                if (afterOrderList != null && afterOrderList.getData().getList().size() > 0) {
                    listLiveData.setValue(afterOrderList.getData().getList());
                    isLastPage.setValue(false);
                } else if (afterOrderList != null && afterOrderList.getData().getList().size() == 0) {
                    isLastPage.setValue(true);
                    listLiveData.setValue(null);
                }
            }

            @Override
            public void onError(String error) {
                showProgress.setValue(false);
            }
        });
    }

    public void processOrder(int id) {
        showProgress.setValue(true);
        ManagerRepo.processAfterOrder(id, new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                showProgress.setValue(false);
                CarBean.CommonResponse commonResponse = (CarBean.CommonResponse) obj;
                if (commonResponse.getStatus().equals("success")) {
                    queryData(typeLivedata.getValue());
                }
            }

            @Override
            public void onError(String error) {
                showProgress.setValue(false);
            }
        });
    }

    public void priceOrder(double price,int id) {
        showProgress.setValue(true);
        ManagerRepo.afterOrderPrice(price, id, new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                showProgress.setValue(false);
                CarBean.CommonResponse commonResponse = (CarBean.CommonResponse) obj;
                if (commonResponse.getStatus().equals("successq~")) {

                }
            }

            @Override
            public void onError(String error) {
                showProgress.setValue(false);
            }
        });
    }
}
