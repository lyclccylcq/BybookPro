package com.Syufei.bybook.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lzhihua.bycar.bean.ManagerBean;
import com.lzhihua.bycar.network.DataSuccessListenter;
import com.lzhihua.bycar.repo.ManagerRepo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ManageTrycarViewmodel extends ViewModel {
    private MutableLiveData<List<ManagerBean.TrycarList.Result>> listLivedate = new MutableLiveData<>();
    private MutableLiveData<Boolean> networkError=new MutableLiveData<>();
    private AtomicInteger offset=new AtomicInteger(1);

    public MutableLiveData<Boolean> getNetworkError() {
        return networkError;
    }

    public MutableLiveData<List<ManagerBean.TrycarList.Result>> getListLivedate() {
        return listLivedate;
    }

    public void setListLivedate(MutableLiveData<List<ManagerBean.TrycarList.Result>> listLivedate) {
        this.listLivedate = listLivedate;
    }
    public void queryData(){
        ManagerRepo.GetTrycarList(10, offset.get(), new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                ManagerBean.TrycarList saleList=( ManagerBean.TrycarList) obj;
                listLivedate.setValue(saleList.getData().getList());
                offset.incrementAndGet();
            }

            @Override
            public void onError(String error) {
                networkError.setValue(true);
            }
        });
    }
    public ManageTrycarViewmodel(){
        super();
        queryData();
    }
}
