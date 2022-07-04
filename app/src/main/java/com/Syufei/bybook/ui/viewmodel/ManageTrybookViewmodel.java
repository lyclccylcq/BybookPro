package com.Syufei.bybook.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Syufei.bybook.bean.ManagerBean;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.repo.ManagerRepo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ManageTrybookViewmodel extends ViewModel {
    private MutableLiveData<List<ManagerBean.TrybookList.Result>> listLivedate = new MutableLiveData<>();
    private MutableLiveData<Boolean> networkError=new MutableLiveData<>();
    private AtomicInteger offset=new AtomicInteger(1);

    public MutableLiveData<Boolean> getNetworkError() {
        return networkError;
    }

    public MutableLiveData<List<ManagerBean.TrybookList.Result>> getListLivedate() {
        return listLivedate;
    }

    public void setListLivedate(MutableLiveData<List<ManagerBean.TrybookList.Result>> listLivedate) {
        this.listLivedate = listLivedate;
    }
    public void queryData(){
        ManagerRepo.GetTrycarList(10, offset.get(), new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                ManagerBean.TrybookList saleList=( ManagerBean.TrybookList) obj;
                listLivedate.setValue(saleList.getData().getList());
                offset.incrementAndGet();
            }

            @Override
            public void onError(String error) {
                networkError.setValue(true);
            }
        });
    }
    public ManageTrybookViewmodel(){
        super();
        queryData();
    }
}
